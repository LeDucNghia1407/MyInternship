package com.System.PharmacyManagement.Database;

import com.System.PharmacyManagement.Model.Department;
import com.System.PharmacyManagement.Model.HealthFacility;
import com.System.PharmacyManagement.Repository.DepartmentRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Data
@Configuration
public class F_DepartmentDatabase {

    long[] departmentIDs = new long[]{1, 2, 3};
    String[] doctorNames = new String[]{"Dr. Smith", "Dr. Johnson", "Dr. Jones"};
    String[] names = new String[]{"Pediatrics", "Cardiology", "Neurology"};
    String[] statuses = new String[]{"Open", "Closed", "Open"};
    HealthFacility[] healthFacilities = new HealthFacility[]{new D_HealthFacilityDatabase().getHealthFacility(1), new D_HealthFacilityDatabase().getHealthFacility(3), new D_HealthFacilityDatabase().getHealthFacility(2)};
    Department[] departmentList = new Department[3];

    @Bean
    @Primary
    CommandLineRunner initDepartmentDatabase(DepartmentRepository departmentRepository) {
        return new CommandLineRunner() {

            //Logger
            private final Logger logger = LoggerFactory.getLogger(F_DepartmentDatabase.class);

            @Override
            public void run(String... args) throws Exception {
                for (int i = 0; i < 3; i++) {
                    departmentList[i] = new Department(departmentIDs[i], healthFacilities[i], doctorNames[i], names[i], statuses[i] );
                }
                for (int i = 0; i < 3; i++) {
                    logger.info("insert data: " + departmentRepository.save(departmentList[i]));
                }
            }
        };
    }

    public Department getDepartment(int i) {
        i = i-1;
        Department department = new Department(departmentIDs[i], healthFacilities[i], doctorNames[i], names[i], statuses[i] );
        return department;
    }
}
