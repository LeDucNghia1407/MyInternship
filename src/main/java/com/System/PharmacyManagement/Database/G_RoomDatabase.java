package com.System.PharmacyManagement.Database;

import com.System.PharmacyManagement.Model.Department;
import com.System.PharmacyManagement.Model.Room;
import com.System.PharmacyManagement.Repository.RoomRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Data
@Configuration
public class G_RoomDatabase {

    long[] roomIDs = new long[]{1, 2, 3};
    String[] roomNumbers = new String[]{"101", "203", "302"};
    String[] statuses = new String[]{"Available", "Occupied", "Available"};
    Department[] departments = new Department[]{new F_DepartmentDatabase().getDepartment(1), new F_DepartmentDatabase().getDepartment(2), new F_DepartmentDatabase().getDepartment(3)};
    Room[] roomList = new Room[3];

    @Bean
    @Primary
    CommandLineRunner initRoomDatabase(RoomRepository roomRepository) {
        return new CommandLineRunner() {

            //Logger
            private final Logger logger = LoggerFactory.getLogger(G_RoomDatabase.class);

            @Override
            public void run(String... args) throws Exception {
                for (int i = 0; i < 3; i++) {
                    roomList[i] = new Room(roomIDs[i],departments[i], roomNumbers[i], statuses[i] );
                }
                for (int i = 0; i < 3; i++) {
                    logger.info("insert data: " + roomRepository.save(roomList[i]));
                }
            }
        };
    }

    public Room getRoom(int i) {
        i = i-1;
        Room room = new Room(roomIDs[i],departments[i], roomNumbers[i], statuses[i] );
        return room;
    }
}
