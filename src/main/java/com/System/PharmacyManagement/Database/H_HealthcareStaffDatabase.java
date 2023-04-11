package com.System.PharmacyManagement.Database;

import com.System.PharmacyManagement.Model.HealthcareStaff;
import com.System.PharmacyManagement.Model.Room;
import com.System.PharmacyManagement.Repository.HealthcareStaffRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Data
@Configuration
public class H_HealthcareStaffDatabase {

    long[] healthcareStaffIDs = new long[]{1, 2, 3};
    String[] doctorIds = new String[]{"D001", "D002", "D003"};
    String[] types = new String[]{"Nurse", "Surgeon", "Technician"};
    Room[] rooms = new Room[]{new G_RoomDatabase().getRoom(1), new G_RoomDatabase().getRoom(2), new G_RoomDatabase().getRoom(3)};
    HealthcareStaff[] healthcareStaffList = new HealthcareStaff[3];

    @Bean
    @Primary
    CommandLineRunner initHealthcareStaffDatabase(HealthcareStaffRepository healthcareStaffRepository) {
        return new CommandLineRunner() {

            //Logger
            private final Logger logger = LoggerFactory.getLogger(H_HealthcareStaffDatabase.class);

            @Override
            public void run(String... args) throws Exception {
                for (int i = 0; i < 3; i++) {
                    healthcareStaffList[i] = new HealthcareStaff(healthcareStaffIDs[i],rooms[i], doctorIds[i], types[i] );
                }
                for (int i = 0; i < 3; i++) {
                    logger.info("insert data: " + healthcareStaffRepository.save(healthcareStaffList[i]));
                }
            }
        };
    }

    public HealthcareStaff getHealthcareStaff(int i) {
        i = i-1;
        HealthcareStaff healthcareStaff = new HealthcareStaff(healthcareStaffIDs[i],rooms[i], doctorIds[i], types[i] );
        return healthcareStaff;
    }
}
