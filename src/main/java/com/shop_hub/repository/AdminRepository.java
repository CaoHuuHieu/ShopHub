package com.shop_hub.repository;

import com.shop_hub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
