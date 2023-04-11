package com.System.PharmacyManagement.Repository;

import com.System.PharmacyManagement.Model.HealthFacility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HealthFacilityRepository extends JpaRepository<HealthFacility, Long> {
    Optional<HealthFacility> findById(Long id);

    List<HealthFacility> findByid(Long id);


}
