package com.System.PharmacyManagement.Database;

import com.System.PharmacyManagement.Model.Customer;
import com.System.PharmacyManagement.Model.HealthcareService;
import com.System.PharmacyManagement.Repository.CustomerRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Data
@Configuration
public class C_CustomerDatabase {

    long[] customerIDs = new long[]{1, 2, 3, 4};
    String[] names = new String[]{"Samantha Jones", "Mark Johnson", "Amy Brown", "David Lee"};
    int[] ages = new int[]{28, 45, 36, 22};
    String[] genders = new String[]{"Female", "Male", "Female", "Male"};
    int[] phoneNumbers = new int[]{5551234, 5555678, 5552468, 5559876};
    String[] symptoms = new String[]{"Fever", "Sore throat", "Cough", "Headache"};
    HealthcareService[] serviceIDs = new HealthcareService[]{new B_HealthcareServiceDatabase().getService(1),new B_HealthcareServiceDatabase().getService(2),new B_HealthcareServiceDatabase().getService(3),new B_HealthcareServiceDatabase().getService(4)};
    String[] contactTypes = new String[]{"Telephone", "In person", "Telephone", "In person"};
    Customer[] customerList = new Customer[4];

    @Bean
    @Primary
    CommandLineRunner initCustomerDatabase(CustomerRepository customerRepository) {
        return new CommandLineRunner() {

            //Logger
            private final Logger logger = LoggerFactory.getLogger(C_CustomerDatabase.class);

            @Override
            public void run(String... args) throws Exception {
                for (int i = 0; i < 4; i++) {
                    customerList[i] = new Customer(customerIDs[i],serviceIDs[i], ages[i], phoneNumbers[i], genders[i], names[i], symptoms[i], contactTypes[i]);
                }
                for (int i = 0; i < 4; i++) {
                    logger.info("insert data: " + customerRepository.save(customerList[i]));
                }
            }
        };
    }
//    long id, HealthcareService healthcareServiceId, int age, int tel, String gender, String name, String symptom, String contactType
    public Customer getCustomer(int i) {
        i = i-1;
        Customer customer = new Customer(customerIDs[i],serviceIDs[i], ages[i], phoneNumbers[i], genders[i], names[i], symptoms[i], contactTypes[i]);
        return customer;
    }
}
