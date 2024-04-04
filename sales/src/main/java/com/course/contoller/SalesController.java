package com.course.contoller;

import com.course.sales.generated.types.AddSalesOrderInput;
import com.course.sales.generated.types.SalesOrderMutationResponse;
import com.course.service.OrderService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class SalesController {

    @Autowired
    private OrderService orderService;

    @DgsMutation
    public SalesOrderMutationResponse addNewSalesOrder (AddSalesOrderInput salesOrder) {
        return orderService.addNewSalesOrder (salesOrder);
    }

}
