package com.gxuwz.ccsa_server.repository;

import com.gxuwz.ccsa_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByPhoneAndPassword(String phone, String password);
    User findByPhone(String phone); // 添加此方法用于注册查重
}