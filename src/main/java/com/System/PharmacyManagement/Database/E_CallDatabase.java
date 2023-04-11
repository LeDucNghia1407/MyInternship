package com.System.PharmacyManagement.Database;

import com.System.PharmacyManagement.Model.Call;
import com.System.PharmacyManagement.Model.HealthcareService;
import com.System.PharmacyManagement.Model.HealthFacility;
import com.System.PharmacyManagement.Repository.CallRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.sql.Date;

@Data
@Configuration
public class E_CallDatabase {

    long[] callIDs = new long[]{1, 2, 3, 4};
    Date[] dates = new Date[]{Date.valueOf("2023-04-06"), Date.valueOf("2023-04-06"), Date.valueOf("2023-04-07"), Date.valueOf("2023-04-07")};
    HealthcareService[] healthcareServices = new HealthcareService[]{new B_HealthcareServiceDatabase().getService(1), new B_HealthcareServiceDatabase().getService(2), new B_HealthcareServiceDatabase().getService(3), new B_HealthcareServiceDatabase().getService(4)};
    HealthFacility[] healthFacilities = new HealthFacility[]{new D_HealthFacilityDatabase().getHealthFacility(1), new D_HealthFacilityDatabase().getHealthFacility(2), new D_HealthFacilityDatabase().getHealthFacility(2), new D_HealthFacilityDatabase().getHealthFacility(3)};
    Call[] callList = new Call[4];

    @Bean
    @Primary
    CommandLineRunner initCallDatabase(CallRepository callRepository) {
        return new CommandLineRunner() {

            //Logger
            private final Logger logger = LoggerFactory.getLogger(E_CallDatabase.class);

            @Override
            public void run(String... args) throws Exception {
                for (int i = 0; i < 4; i++) {
                    callList[i] = new Call(callIDs[i],  healthcareServices[i], healthFacilities[i], dates[i]);
                }
                for (int i = 0; i < 4; i++) {
                    logger.info("insert data: " + callRepository.save(callList[i]));
                }
            }
        };
    }

    public Call getCall(int i) {
        i = i-1;
        Call call = new Call(callIDs[i],  healthcareServices[i], healthFacilities[i], dates[i]);
        return call;
    }
}
