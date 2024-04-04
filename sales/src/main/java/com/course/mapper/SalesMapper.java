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

        SimpleModel simpleModel = new SimpleModel ();

        return SalesOrderEntity.builder ()
                .orderNumber ("SALES-" + RandomStringUtils.randomAlphabetic (8).toUpperCase ())
                .financeEntity (toFinanceEntity (addSalesOrderInput.getFinance ()))
                .salesOrderItemsEntity (addSalesOrderInput.getSalesOrderItems ().stream ()
                        .map (this::toSalesOrderItemsEntity)
                        .toList ())
                .financeEntity (toFinanceEntity (addSalesOrderInput.getFinance ()))
                .build ();
    }

    SalesOrderItemsEntity toSalesOrderItemsEntity (SalesOrderItemInput salesOrderItemInput);

    @Mapping (target = "loanEntity", source = "loan")
    FinanceEntity toFinanceEntity (FinanceInput financeInput);

    Loan toLoan (LoanEntity loanEntity);

    LoanEntity toLoanEntity (LoanInput loanInput);

    SalesAddress toSalesAddress (AddressEntity addressEntity);

    //  @Mapping (target = "addresses", source = "addressEntity", qualifiedByName = "toAddressEntityTwo")
    @Mapping (target = "documentEntity", source = "documentEntity", qualifiedByName = "toDocument")
    Customer toCustomer (CustomerEntity customerEntity);

    // @Mapping (target = "addressEntity", source = "addresses", qualifiedByName = "toAddressEntity")
    CustomerEntity toCustomerEntity (AddCustomerInput addCustomerInput);


    //   @Mapping (target = "addresses", source = "addresses", qualifiedByName = "toAddressEntityFromCustomer")
    @Mapping (target = "documentEntity", source = "documentEntity", qualifiedByName = "toDocumentEntity")
    CustomerEntity toCustomerEntityFromCustomer (Customer customer);

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
    public static List<AddressEntity> toAddressEntity (List<SalesAddress> addresses) {
        return addresses.stream ()
                .map (address -> new AddressEntity (
                        UUID.fromString (address.getUuid ()),
                        address.getStreet (),
                        address.getCity (),
                        address.getZipcode ()))
                .toList ();

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

/*     @Named ("toEmptySolutions")
     public static List<Solutions> toEmptySolutions (ProblemCreateInput problemCreateInput) {
          return new ArrayList<> ();
     }*/

}
