package com.course.service;

import com.course.entity.AddressEntity;
import com.course.entity.CustomerEntity;
import com.course.entity.DocumentEntity;
import com.course.mapper.SalesMapper;
import com.course.repositories.CustomerRepository;
import com.course.sales.generated.types.*;
import com.course.specification.CustomerSpecification;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SalesMapper salesMapper;

    public CustomerMutationResponse updateExistingCustomer (final UniqueCustomerInput uniqueCustomerInput,
                                                            final UpdateCustomerInput updateCustomerInput) {
        Optional<Customer> customer = findUniqueCustomer (uniqueCustomerInput);
        CustomerEntity customerEntity = customer.map (salesMapper::toCustomerEntityFromCustomer)
                .orElseThrow (() -> new DgsEntityNotFoundException ("Customer not found"));
        if (StringUtils.isAllBlank (updateCustomerInput.getEmail (), updateCustomerInput.getPhone ())) {
            throw new IllegalArgumentException ("At least one field should be provided");
        }

        Optional<String> email = Optional.ofNullable (updateCustomerInput.getEmail ());
        Optional<String> phone = Optional.ofNullable (updateCustomerInput.getPhone ());

        if (customer.isPresent ()) {
            customerEntity.setEmail (email.orElse (customer.get ().getEmail ()));
            customerEntity.setPhone (phone.orElse (customer.get ().getUuid ()));
            final CustomerEntity save = customerRepository.save (customerEntity);
            return new CustomerMutationResponse (true,
                    "Customer updated successfully",
                    save.getUuid ().toString ());
        } else {
            return new CustomerMutationResponse (false,
                    "Customer not found",
                    uniqueCustomerInput.getUuid ());
        }
    }

    public CustomerMutationResponse addDocumentToExistingCustomer (final UniqueCustomerInput customer,
                                                                   final String documentType,
                                                                   File file) {

        final Optional<CustomerEntity> customerEntity = findUniqueCustomerEntity (customer);
        if (customerEntity.isEmpty ()) {
            throw new DgsEntityNotFoundException ("Customer not found");
        }

        final DocumentEntity documentEntity = new DocumentEntity ();
        String documentPath = String.format ("%s/%s/%s-%s-%s", "https://dummy-storage.com",
                customerEntity.get ().getUuid (), documentType, UUID.randomUUID (), file.getName ());
        documentEntity.setDocumentType (documentType);
        documentEntity.setDocumentPath (documentPath);

        customerEntity.get ().getDocumentEntity ().add (documentEntity);
        final CustomerEntity save = customerRepository.save (customerEntity.get ());

        return new CustomerMutationResponse (true,
                "Document saved successfully",
                customerEntity.get ().getUuid ().toString ());
    }

    public Page<CustomerEntity> findCustomers (Optional<UniqueCustomerInput> uniqueCustomerInput,
                                               int page,
                                               int size) {
        PageRequest pageRequest = PageRequest.of (
                Optional.of (page).orElse (0),
                Optional.of (size).orElse (5),
                Sort.by (CustomerSpecification.FIELD_EMAIL));

        if (uniqueCustomerInput.isPresent ()) {
            final Optional<Customer> customer = findUniqueCustomer (uniqueCustomerInput.get ());
            final Optional<CustomerEntity> uniqueCustomer = customer
                    .map (salesMapper::toCustomerEntityFromCustomer);
            return uniqueCustomer.isPresent () ?
                    new PageImpl<> (
                            List.of (uniqueCustomer.get ()), pageRequest, 10) :
                    new PageImpl<> (Collections.emptyList (), pageRequest, 0);
        }
        return customerRepository.findAll (pageRequest);

    }

    public CustomerEntity addCustomer (AddCustomerInput addCustomerInput) {
        final CustomerEntity customerEntity = salesMapper.toCustomerEntity (addCustomerInput);
        return customerRepository.save (customerEntity);
    }

    public CustomerEntity addAddressToTheExistingCustomer (
            CustomerEntity customerEntity, List<AddAddressInput> addAddressInput) {

        List<AddressEntity> addressEntity = addAddressInput
                .stream ()
                .map (salesMapper::toAddressEntity)
                .toList ();

        customerEntity.getAddresses ().addAll (addressEntity);
        return customerRepository.save (customerEntity);
    }

    public Optional<Customer> findUniqueCustomer (UniqueCustomerInput uniqueCustomerInput) {

        final String email = uniqueCustomerInput.getEmail ();
        final String uuid = uniqueCustomerInput.getUuid ();
        if (StringUtils.isNoneBlank (uuid, email)) {
            throw new IllegalArgumentException ("Only one of the fields should be provided");
        } else if (StringUtils.isAllBlank (email, uuid)) {
            throw new IllegalArgumentException ("One of the fields should be provided");
        }

        PageRequest pageRequest = PageRequest.of (0, 10);

        Specification<CustomerEntity> specification = Specification.where (
                StringUtils.isNoneBlank (uuid)
                        ? CustomerSpecification.uuidEqualsIgnoreCase (uuid) : CustomerSpecification.emailContainsIgnoreCase (email)
        );
        return customerRepository.findAll (specification, pageRequest)
                .stream ().
                map (salesMapper::toCustomer).
                findFirst ();
    }

    public Optional<CustomerEntity> findUniqueCustomerEntity (UniqueCustomerInput uniqueCustomerInput) {

        final String email = uniqueCustomerInput.getEmail ();
        final String uuid = uniqueCustomerInput.getUuid ();
        if (StringUtils.isNoneBlank (uuid, email)) {
            throw new IllegalArgumentException ("Only one of the fields should be provided");
        } else if (StringUtils.isAllBlank (email, uuid)) {
            throw new IllegalArgumentException ("One of the fields should be provided");
        }

        PageRequest pageRequest = PageRequest.of (0, 10);

        Specification<CustomerEntity> specification = Specification.where (
                StringUtils.isNoneBlank (uuid)
                        ? CustomerSpecification.uuidEqualsIgnoreCase (uuid) : CustomerSpecification.emailContainsIgnoreCase (email)
        );
        return customerRepository.findAll (specification, pageRequest)
                .stream ().
                findFirst ();
    }
}
