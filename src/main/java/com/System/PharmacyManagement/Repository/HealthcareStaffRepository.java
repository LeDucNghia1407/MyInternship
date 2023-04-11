package com.System.PharmacyManagement.Repository;

import com.System.PharmacyManagement.Model.HealthcareStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HealthcareStaffRepository extends JpaRepository<HealthcareStaff, Long> {
    Optional<HealthcareStaff> findById(Long id);

    List<HealthcareStaff> findByid(Long id);

    @Query(value = "SELECT h.id, h.doctorId, h.type, h.roomid FROM HealthcareStaff h ", nativeQuery = true)
    List<Map<String, Object>> shallowFind();

}

