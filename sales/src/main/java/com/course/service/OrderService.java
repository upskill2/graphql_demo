package com.course.service;

import com.course.entity.CustomerEntity;
import com.course.entity.FinanceEntity;
import com.course.entity.SalesOrderEntity;
import com.course.entity.SalesOrderItemsEntity;
import com.course.mapper.SalesMapper;
import com.course.repositories.CustomerRepository;
import com.course.repositories.SalesOrderRepository;
import com.course.sales.generated.types.AddSalesOrderInput;
import com.course.sales.generated.types.SalesOrderItemInput;
import com.course.sales.generated.types.SalesOrderMutationResponse;
import com.course.sales.generated.types.SimpleModel;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    private SalesOrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SalesMapper salesMapper;

    @Autowired
    private ProductService productService;

    public SalesOrderMutationResponse addNewSalesOrder (final AddSalesOrderInput salesOrder) {

        Optional<CustomerEntity> customerEntity = customerRepository.findById (UUID.fromString (salesOrder.getCustomerUuid ()));
        double d = 20.65;
        if (customerEntity.isEmpty ()) {
            throw new DgsEntityNotFoundException (String.format ("Customer UUID: %s not found", salesOrder.getCustomerUuid ()));
        }

        String modelUuid = salesOrder.getSalesOrderItems ().stream ().map (SalesOrderItemInput::getModelUuid).findFirst ().orElse ("");
        final Map<String, SimpleModel> simpleModelMap = productService.loadSimpleModel (Set.of (modelUuid));

        // Add the new sales order
        final SalesOrderEntity salesOrderEntity = salesMapper.toSalesOrderEntity (salesOrder);
        final SalesOrderItemsEntity salesOrderItems = salesOrder.getSalesOrderItems ().stream ()
                .map (salesMapper::toSalesOrderItemsEntity).findFirst ().orElse (new SalesOrderItemsEntity ());
        final FinanceEntity financeEntity = salesMapper.toFinanceEntity (salesOrder.getFinance ());


        salesOrderEntity.setCustomerEntity (customerEntity.get ());
        financeEntity.setSalesOrderEntity (salesOrderEntity);
        salesOrderEntity.setSalesOrderItems (List.of (salesOrderItems));
        salesOrderItems.setSimpleModel (simpleModelMap.get (modelUuid));
        salesOrderEntity.setFinance (financeEntity);

        final SalesOrderEntity savedOrder = orderRepository.save (salesOrderEntity);

        return SalesOrderMutationResponse.newBuilder ()
                .orderNumber (savedOrder.getOrderNumber ())
                .customerUuid (salesOrder.getCustomerUuid ())
                .salesOrderUuid (savedOrder.getUuid ().toString ())
                .message ("Sales Order added successfully")
                .success (true)
                .simpleModel (simpleModelMap.get (modelUuid))
                .build ();
    }
}
