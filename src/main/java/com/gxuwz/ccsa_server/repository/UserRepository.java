package com.gxuwz.ccsa_server.repository;
import com.gxuwz.ccsa_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByPhoneAndPassword(String phone, String password);
}