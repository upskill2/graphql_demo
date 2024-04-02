package com.course.mapper;

import com.course.entity.AddressEntity;
import com.course.entity.CustomerEntity;
import com.course.sales.generated.types.AddAddressInput;
import com.course.sales.generated.types.AddCustomerInput;
import com.course.sales.generated.types.Customer;
import com.course.sales.generated.types.SalesAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper (componentModel = "spring")
public interface SalesMapper {

    SalesAddress toSalesAddress (AddressEntity addressEntity);

    @Mapping (target = "addresses", source = "addressEntity", qualifiedByName = "toAddressEntityTwo")
    Customer toCustomer (CustomerEntity customerEntity);

    @Mapping (target = "addressEntity", source = "addresses", qualifiedByName = "toAddressEntity")
    CustomerEntity toCustomerEntity (AddCustomerInput addCustomerInput);


    @Mapping (target = "addressEntity", source = "addresses", qualifiedByName = "toAddressEntityFromCustomer")
    CustomerEntity toCustomerEntityFromCustomer (Customer customer);

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
