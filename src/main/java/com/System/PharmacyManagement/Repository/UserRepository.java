package com.System.PharmacyManagement.Repository;

import com.System.PharmacyManagement.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
