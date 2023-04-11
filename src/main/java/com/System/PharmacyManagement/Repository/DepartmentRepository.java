package com.System.PharmacyManagement.Repository;

import com.System.PharmacyManagement.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findById(Long id);

    List<Department> findByid(Long id);

    @Query(value = "SELECT d.id, d.doctorName, d.name,d.status, d.healthfacilityID FROM Department d ", nativeQuery = true)
    List<Map<String, Object>> shallowFind();

}

