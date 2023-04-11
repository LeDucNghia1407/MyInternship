package com.System.PharmacyManagement.Repository;

import com.System.PharmacyManagement.Model.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CallRepository extends JpaRepository<Call, Long> {
    Optional<Call> findById(Long id);

    @Query(value = "SELECT c.id, c.date, c.health_facility_id, c.healthcare_service_id FROM calls c ", nativeQuery = true)
    List<Map<String, Object>> shallowFind();
    List<Call> findByid(Long id);
}
