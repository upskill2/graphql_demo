package com.course.contoller;

import com.course.entity.CustomerEntity;
import com.course.mapper.SalesMapper;
import com.course.sales.generated.DgsConstants;
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
import org.springframework.core.io.InputStreamSource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DgsComponent
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SalesMapper salesMapper;

    @DgsMutation
    CustomerMutationResponse updateExistingCustomer (Optional<UniqueCustomerInput> customer,
                                                     @InputArgument (name = "customerUpdate") Optional<UpdateCustomerInput> updateCustomerInput) {
        if (customer.isEmpty () || updateCustomerInput.isEmpty ()) {
            return CustomerMutationResponse.newBuilder ()
                    .success (false)
                    .message ("Customer not found")
                    .build ();
        } else {
            return customerService.updateExistingCustomer (customer.get (), updateCustomerInput.get ());
        }
    }

    @DgsMutation
    public CustomerMutationResponse addDocumentToExistingCustomer (UniqueCustomerInput customer, String documentType
            , DataFetchingEnvironment environment
    ) throws IOException {
        MultipartFile documentFile = environment.getArgument (DgsConstants.MUTATION.ADDDOCUMENTTOEXISTINGCUSTOMER_INPUT_ARGUMENT.DocumentFile);

        File directory = new File ("sales/src/main/resources/schema");
        File[] files = directory.listFiles ();


        String filename = "";
        File transferFile = null;
        for (File file : files) {
            filename = file.getName ();
            final long length = file.length ();
            transferFile = file;
        }


        return customerService.addDocumentToExistingCustomer (customer, documentType, transferFile);
    }

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
