package com.gxuwz.ccsa_server.repository;

import com.gxuwz.ccsa_server.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
    Merchant findByPhoneAndPassword(String phone, String password);
    Merchant findByPhone(String phone); // 添加此方法用于注册查重
}