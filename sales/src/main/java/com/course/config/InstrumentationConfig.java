package com.course.config;

import com.course.sales.generated.DgsConstants;
import graphql.GraphQLError;
import graphql.execution.ResultPath;
import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.fieldvalidation.FieldAndArguments;
import graphql.execution.instrumentation.fieldvalidation.FieldValidationEnvironment;
import graphql.execution.instrumentation.fieldvalidation.FieldValidationInstrumentation;
import graphql.execution.instrumentation.fieldvalidation.SimpleFieldValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

@Configuration
public class InstrumentationConfig {

    private BiFunction<FieldAndArguments, FieldValidationEnvironment, Optional<GraphQLError>> ageMustBetween18To70 () {

        return (fieldAndArguments, fieldValidationEnvironment) -> {
            Map<String, Object> argCustomer = fieldAndArguments.getArgumentValue (
                    DgsConstants.MUTATION.ADDNEWCUSTOMER_INPUT_ARGUMENT.Customer
            );
            LocalDate birthDate = (LocalDate) argCustomer.getOrDefault (DgsConstants.CUSTOMER.BirthDate, LocalDate.now ());
            Long age = ChronoUnit.YEARS.between (birthDate, LocalDate.now ());

            return (age < 18 || age > 70) ?
                    Optional.of (fieldValidationEnvironment.mkError (
                            "Age must be between 18 and 70" +
                                    age)) :
                    Optional.empty ();
        };

    }

    private BiFunction<FieldAndArguments, FieldValidationEnvironment, Optional<GraphQLError>> emailMustNotGmail () {

        return (fieldAndArguments, fieldValidationEnvironment) -> {
            Map<String, Object> argCustomer = fieldAndArguments.getArgumentValue (
                    DgsConstants.MUTATION.ADDNEWCUSTOMER_INPUT_ARGUMENT.Customer
            );
            String email = (String) argCustomer.getOrDefault (DgsConstants.CUSTOMER.Email, "");

            return (email.toLowerCase ().contains ("@gmail.com")) ?
                    Optional.of (fieldValidationEnvironment.mkError (
                            "Email must not be gmail")) :
                    Optional.empty ();
        };
    }

    @Bean
    Instrumentation ageValidationInstrumentation () {
        ResultPath pathAddNewCustomer = ResultPath.parse ("/" +
                DgsConstants.MUTATION.AddNewCustomer);

        SimpleFieldValidation ageValidation = new SimpleFieldValidation ();
        ageValidation.addRule (pathAddNewCustomer, ageMustBetween18To70 ());

        return new FieldValidationInstrumentation (ageValidation);
    }

    @Bean
    Instrumentation emailValidationInstrumentation () {
        ResultPath pathAddNewCustomer = ResultPath.parse ("/" +
                DgsConstants.MUTATION.AddNewCustomer);

        final SimpleFieldValidation emailValidation = new SimpleFieldValidation ();
        emailValidation.addRule (pathAddNewCustomer, emailMustNotGmail ());

        return new FieldValidationInstrumentation (emailValidation);
    }


}
