package com.System.PharmacyManagement.Repository;

import com.System.PharmacyManagement.Model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findById(Long id);

    List<Login> findByid(Long id);


}
