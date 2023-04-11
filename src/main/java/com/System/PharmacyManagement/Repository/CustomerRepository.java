package com.System.PharmacyManagement.Repository;

import com.System.PharmacyManagement.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findById(Long id);
    List<Customer> findByid(Long id);

    @Query(value = "SELECT c.id, c.age, c.contact_type, c.gender, c.name, c.symptom, c.tel, c.healthcareServiceId FROM Customer c ", nativeQuery = true)
    List<Map<String, Object>> shallowFind();

}
