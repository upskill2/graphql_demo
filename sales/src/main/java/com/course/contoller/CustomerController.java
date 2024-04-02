package com.course.contoller;

import com.course.entity.CustomerEntity;
import com.course.mapper.SalesMapper;
import com.course.sales.generated.types.*;
import com.course.service.CustomerService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.relay.Connection;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DgsComponent
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SalesMapper salesMapper;

    @DgsQuery
    public CustomerPagination customerPagination (@InputArgument (name = "customer") Optional<UniqueCustomerInput> uniqueCustomerInput,
                                                  DataFetchingEnvironment dataFetchingEnvironment,
                                                  int page,
                                                  int size) {
        final Page<CustomerEntity> customers = customerService.findCustomers (uniqueCustomerInput, page, size);

        Connection<CustomerEntity> pageConnection = new SimpleListConnection<> (
                customers.getContent ()).get (dataFetchingEnvironment);


        CustomerPagination result = new CustomerPagination ();
        result.setCustomerConnection (pageConnection);
        result.setTotalPage (customers.getTotalPages ());
        result.setPage (customers.getNumber ());
        result.setSize (customers.getSize ());
        result.setTotalElement (customers.getTotalElements ());

        return result;
    }

    @DgsMutation
    public CustomerMutationResponse addNewCustomer (@InputArgument (name = "customer") AddCustomerInput addCustomerInput) {
        final CustomerEntity customerEntity = customerService.addCustomer (addCustomerInput);

        return CustomerMutationResponse.newBuilder ()
                .success (customerEntity != null)
                .customerUuid (customerEntity.getUuid ().toString ())
                .message ("Customer added successfully")
                .build ();
    }

    @DgsMutation
    public CustomerMutationResponse addAddressesToExistingCustomer (
            UniqueCustomerInput customer,
            List<AddAddressInput> addresses) {

        UUID customerId = UUID.fromString (customer.getUuid ());

        final CustomerEntity customerEntity = customerService.findUniqueCustomerEntity (customer).stream ()
                .findFirst ()
                .orElseThrow (() -> new DgsEntityNotFoundException ("Customer not found"));


        final CustomerEntity resultEntity = customerService.addAddressToTheExistingCustomer (
                customerEntity, addresses);

        return CustomerMutationResponse.newBuilder ()
                .success (resultEntity != null)
                .customerUuid (resultEntity.getUuid ().toString ())
                .message ("Address added successfully")
                .build ();
    }


}
