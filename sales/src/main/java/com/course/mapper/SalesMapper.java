package com.course.mapper;

import com.course.entity.*;
import com.course.sales.generated.types.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper (componentModel = "spring")
public interface SalesMapper {


    default SalesOrderEntity toSalesOrderEntity (AddSalesOrderInput addSalesOrderInput) {
        return SalesOrderEntity.builder ()
                .orderNumber ("SALES-" + RandomStringUtils.randomAlphabetic (8).toUpperCase ())
                .finance (toFinanceEntity (addSalesOrderInput.getFinance ()))
                .salesOrderItems (addSalesOrderInput.getSalesOrderItems ().stream ()
                        .map (this::toSalesOrderItemsEntity)
                        .toList ())
                .finance (toFinanceEntity (addSalesOrderInput.getFinance ()))
                .build ();
    }

    SalesOrderItemsEntity toSalesOrderItemsEntity (SalesOrderItemInput salesOrderItemInput);

    @Mapping (target = "loanEntity", source = "loan")
    FinanceEntity toFinanceEntity (FinanceInput financeInput);

    @Mapping (target = "loanEntity", source = "loan")
    FinanceEntity toFinanceEntityFromFinance (Finance finance);

    Loan toLoan (LoanEntity loanEntity);

    LoanEntity toLoanEntity (LoanInput loanInput);

    SalesAddress toSalesAddress (AddressEntity addressEntity);

    //  @Mapping (target = "addresses", source = "addressEntity", qualifiedByName = "toAddressEntityTwo")
    @Mapping (target = "documentEntity", source = "documentEntity", qualifiedByName = "toDocument")
    @Mapping (target = "salesOrders", source = "salesOrders", qualifiedByName = "toSalesOrderItem")
    Customer toCustomer (CustomerEntity customerEntity);

    // @Mapping (target = "addressEntity", source = "addresses", qualifiedByName = "toAddressEntity")
    CustomerEntity toCustomerEntity (AddCustomerInput addCustomerInput);

    @Named ("toSalesOrderItem")
    public static List<SalesOrder> toSalesOrderItem (List<SalesOrderEntity> salesOrderEntities) {

        SalesOrderItemsEntity salesOrderItemsEntity = salesOrderEntities.get (0).getSalesOrderItems ().get (0);
        SalesOrderItem salesOrderItem = SalesOrderItem.newBuilder ()
                .simpleModel (salesOrderItemsEntity.getSimpleModel ())
                .quantity (salesOrderItemsEntity.getQuantity ())
                .uuid (salesOrderItemsEntity.getUuid ().toString ())
                .modelUuid (salesOrderItemsEntity.getModelUuid ().toString ())
                .notes (RandomStringUtils.randomAlphabetic (10))
                .build ();

        return salesOrderEntities.stream ()
                .map (salesOrderEntity -> SalesOrder.newBuilder ()
                        .salesOrderItems (List.of (salesOrderItem))
                        .uuid (salesOrderEntity.getUuid ().toString ())
                        .orderDateTime (salesOrderEntity.getOrderDateTime ())
                        .orderNumber (salesOrderEntity.getOrderNumber ())
                        .salesOrderItems (List.of (salesOrderItem))
                        .build ())
                .toList ();
    }

    default CustomerEntity toCustomerEntityFromCustomer (Customer customer) {
        SalesOrderItemsEntity salesOrderItemsEntity = new SalesOrderItemsEntity ();

        final SalesOrderItem salesOrderItem1 = customer.getSalesOrders ().get (0).getSalesOrderItems ().get (0);
        salesOrderItemsEntity.setSimpleModel (salesOrderItem1.getSimpleModel ());
        salesOrderItemsEntity.setQuantity (salesOrderItem1.getQuantity ());
        salesOrderItemsEntity.setUuid (UUID.fromString (salesOrderItem1.getUuid ()));
        salesOrderItemsEntity.setModelUuid (UUID.fromString (salesOrderItem1.getModelUuid ()));

        return CustomerEntity.builder ()
                .uuid (UUID.fromString (customer.getUuid ()))
                .fullName (customer.getFullName ())
                .email (customer.getEmail ())
                .phone (customer.getPhone ())
                .birthDate (customer.getBirthDate ())
                .addresses (customer.getAddresses ().stream ()
                        .map (SalesMapper::toAddressEntityFromCustomer)
                        .toList ())
                .documentEntity (customer.getDocumentEntity ().stream ().map (document -> new DocumentEntity (
                                UUID.fromString (document.getUuid ()),
                                document.getDocumentType (),
                                document.getDocumentPath ()))
                        .toList ())
                .salesOrders (customer.getSalesOrders ().stream ().
                        map (salesOrder -> SalesOrderEntity.builder ()
                                .uuid (UUID.fromString (salesOrder.getUuid ()))
                                .orderDateTime (salesOrder.getOrderDateTime ())
                                .orderNumber (salesOrder.getOrderNumber ())
                                .salesOrderItems (List.of (salesOrderItemsEntity))
                                .build ())
                        .toList ())
                .build ();

    }

    @Named ("toDocument")
    Document toDocument (DocumentEntity documentEntity);

    @Named ("toDocumentEntity")
    public static List<DocumentEntity> toDocumentEntity (List<Document> documentEntity) {
        return documentEntity.stream ()
                .map (document -> new DocumentEntity (
                        UUID.fromString (document.getUuid ()),
                        document.getDocumentType (),
                        document.getDocumentPath ()))
                .toList ();
    }


    @Named ("toAddressEntity")
    AddressEntity toAddressEntity (AddAddressInput addAddressInput);

    @Named ("toAddressEntityFromCustomer")
    public static AddressEntity toAddressEntityFromCustomer (SalesAddress addresses) {
        AddressEntity addressEntity = new AddressEntity ();
        addressEntity.setCity (addresses.getCity ());
        addressEntity.setStreet (addresses.getStreet ());
        addressEntity.setZipcode (addresses.getZipcode ());
        addressEntity.setUuid (UUID.fromString (addresses.getUuid ()));
        return addressEntity;

    }

    @Named ("toAddressEntityTwo")
    public static List<SalesAddress> toAddressEntityTwo (List<AddressEntity> addresses) {
        return addresses.stream ()
                .map (address -> new SalesAddress (
                        address.getUuid ().toString (),
                        address.getStreet (),
                        address.getCity (),
                        address.getZipcode ()))
                .toList ();

    }

}
