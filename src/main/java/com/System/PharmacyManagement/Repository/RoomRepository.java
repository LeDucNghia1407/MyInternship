package com.System.PharmacyManagement.Repository;

import com.System.PharmacyManagement.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findById(Long id);

    List<Room> findByid(Long id);

    @Query(value = "SELECT r.id, r.room, r.status, r.departmentid FROM Room r ", nativeQuery = true)
    List<Map<String, Object>> shallowFind();

}
