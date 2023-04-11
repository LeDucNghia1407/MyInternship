package com.System.PharmacyManagement.Repository;

import com.System.PharmacyManagement.Model.HealthcareService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface HealthcareServiceRepository extends JpaRepository<HealthcareService, Long> {
    Optional<HealthcareService> findById(Long id);

    List<HealthcareService> findByid(Long id);

    @Query(value = "SELECT h.id, h.age,h.gender,h.name,h.tell,h.loginID FROM HealthcareService h ", nativeQuery = true)
    List<Map<String, Object>> shallowFind();



}
