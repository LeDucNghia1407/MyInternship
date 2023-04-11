package com.System.PharmacyManagement.Database;

import com.System.PharmacyManagement.Model.HealthFacility;
import com.System.PharmacyManagement.Repository.HealthFacilityRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Data
@Configuration
public class D_HealthFacilityDatabase {

    long[] facilityIDs = new long[]{1, 2, 3};
    String[] names = new String[]{"St. Mary's Hospital", "Greenwood Clinic", "Riverview Medical Center"};
    String[] addresses = new String[]{"123 Main St", "456 Elm St", "789 Oak St"};
    String[] sizes = new String[]{"Large", "Small", "Medium"};
    HealthFacility[] facilityList = new HealthFacility[3];

    @Bean
    @Primary
    CommandLineRunner initHealthFacilityDatabase(HealthFacilityRepository healthFacilityRepository) {
        return new CommandLineRunner() {

            //Logger
            private final Logger logger = LoggerFactory.getLogger(D_HealthFacilityDatabase.class);

            @Override
            public void run(String... args) throws Exception {
                for (int i = 0; i < 3; i++) {
                    facilityList[i] = new HealthFacility(facilityIDs[i], names[i], addresses[i], sizes[i]);
                }
                for (int i = 0; i < 3; i++) {
                    logger.info("insert data: " + healthFacilityRepository.save(facilityList[i]));
                }
            }
        };
    }

    public HealthFacility getHealthFacility(int i) {
        HealthFacility facility = new HealthFacility(facilityIDs[i - 1], names[i - 1], addresses[i - 1], sizes[i - 1]);
        return facility;
    }
}

