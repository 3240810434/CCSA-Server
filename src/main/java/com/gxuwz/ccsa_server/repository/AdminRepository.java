package com.gxuwz.ccsa_server.repository;
import com.gxuwz.ccsa_server.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByAccountAndPassword(String account, String password);
}