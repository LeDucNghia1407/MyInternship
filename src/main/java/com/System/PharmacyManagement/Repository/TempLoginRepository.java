package com.System.PharmacyManagement.Repository;

import com.System.PharmacyManagement.Model.TempLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TempLoginRepository extends JpaRepository<TempLogin, Long> {
    Optional<TempLogin> findById(Long id);

    List<TempLogin> findByid(Long id);


}
