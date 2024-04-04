package com.course.service;

import com.course.entity.CustomerEntity;
import com.course.entity.SalesOrderEntity;
import com.course.mapper.SalesMapper;
import com.course.repositories.CustomerRepository;
import com.course.repositories.SalesOrderRepository;
import com.course.sales.generated.types.AddSalesOrderInput;
import com.course.sales.generated.types.SalesOrderMutationResponse;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private SalesOrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SalesMapper salesMapper;

    public SalesOrderMutationResponse addNewSalesOrder (final AddSalesOrderInput salesOrder) {

        Optional<CustomerEntity> customerEntity = customerRepository.findById (UUID.fromString (salesOrder.getCustomerUuid ()));

        double d =  20.65;
        if (customerEntity.isEmpty ()) {
            throw new DgsEntityNotFoundException (String.format ("Customer UUID: %s not found", salesOrder.getCustomerUuid ()));
        }

        // Add the new sales order
        final SalesOrderEntity savedOrder = orderRepository.save (salesMapper.toSalesOrderEntity (salesOrder));


        return SalesOrderMutationResponse.newBuilder ()
                .orderNumber (savedOrder.getOrderNumber ())
                .customerUuid (salesOrder.getCustomerUuid ())
                .salesOrderUuid (savedOrder.getUuid ().toString ())
                .message ("Sales Order added successfully")
                .success (true)
                .build ();
    }
}
