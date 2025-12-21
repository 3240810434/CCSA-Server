package com.gxuwz.ccsa_server.controller;

import com.gxuwz.ccsa_server.entity.*;
import com.gxuwz.ccsa_server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired private UserRepository userRepository;
    @Autowired private AdminRepository adminRepository;
    @Autowired private MerchantRepository merchantRepository;

    // 1. 居民登录
    @PostMapping("/resident")
    public Map<String, Object> loginResident(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String password = params.get("password");
        User user = userRepository.findByPhoneAndPassword(phone, password);
        return buildResult(user, "账号或密码错误");
    }

    // 2. 管理员登录
    @PostMapping("/admin")
    public Map<String, Object> loginAdmin(@RequestBody Map<String, String> params) {
        String account = params.get("account");
        String password = params.get("password");
        Admin admin = adminRepository.findByAccountAndPassword(account, password);
        return buildResult(admin, "账号或密码错误");
    }

    // 3. 商家登录
    @PostMapping("/merchant")
    public Map<String, Object> loginMerchant(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String password = params.get("password");
        Merchant merchant = merchantRepository.findByPhoneAndPassword(phone, password);

        // 简单判断一下审核状态，如果没审核通过也可以拦住（可选）
        if (merchant != null && !"approved".equals(merchant.getStatus())) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", 403);
            error.put("msg", "账号未审核通过");
            return error;
        }
        return buildResult(merchant, "账号或密码错误");
    }

    // 统一返回格式辅助方法
    private Map<String, Object> buildResult(Object data, String errorMsg) {
        Map<String, Object> result = new HashMap<>();
        if (data != null) {
            result.put("code", 200);
            result.put("msg", "登录成功");
            result.put("data", data);
        } else {
            result.put("code", 400);
            result.put("msg", errorMsg);
        }
        return result;
    }
}