package com.System.PharmacyManagement.Database;

import com.System.PharmacyManagement.Model.HealthcareService;
import com.System.PharmacyManagement.Model.Login;
import com.System.PharmacyManagement.Repository.HealthcareServiceRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Data
@Configuration
public class B_HealthcareServiceDatabase {

    long[] serviceIDs = new long[]{1, 2, 3, 4};
    String[] names = new String[]{"John Doe", "Jane Smith", "Bob Johnson", "Emily Davis"};
    int[] ages = new int[]{25, 31, 42, 29};
    String[] genders = new String[]{"Male", "Female", "Male", "Female"};
    int[] tells = new int[]{12345, 67890, 24680, 13579};
    Login[] loginIDs = new Login[]{new A_LoginDatabase().getLogin(1), new A_LoginDatabase().getLogin(2), new A_LoginDatabase().getLogin(3), new A_LoginDatabase().getLogin(4)};
    HealthcareService[] serviceList = new HealthcareService[4];

    @Bean
    @Primary
    CommandLineRunner initHealthcareServiceDatabase(HealthcareServiceRepository healthcareServiceRepository) {
        return new CommandLineRunner() {

            //Logger
            private final Logger logger = LoggerFactory.getLogger(B_HealthcareServiceDatabase.class);

            @Override
            public void run(String... args) throws Exception {
                for (int i = 0; i < 4; i++) {
                    serviceList[i] = new HealthcareService(serviceIDs[i],loginIDs[i], names[i], ages[i], tells[i], genders[i]);
                }
                for (int i = 0; i < 4; i++) {
                    logger.info("insert data: " + healthcareServiceRepository.save(serviceList[i]));
                }
            }
        };
    }

    public HealthcareService getService(int i) {
        HealthcareService service = new HealthcareService(serviceIDs[i - 1], loginIDs[i - 1], names[i - 1], ages[i - 1], tells[i - 1],genders[i - 1] );
        return service;
    }
}
