package com.gxuwz.ccsa_server.controller;

import com.gxuwz.ccsa_server.entity.*;
import com.gxuwz.ccsa_server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired private UserRepository userRepository;
    @Autowired private AdminRepository adminRepository;
    @Autowired private MerchantRepository merchantRepository;

    // ================= 登录接口 =================

    @PostMapping("/login/resident")
    public Map<String, Object> loginResident(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String password = params.get("password");
        User user = userRepository.findByPhoneAndPassword(phone, password);
        return buildResult(user, "账号或密码错误");
    }

    @PostMapping("/login/admin")
    public Map<String, Object> loginAdmin(@RequestBody Map<String, String> params) {
        String account = params.get("account");
        String password = params.get("password");
        Admin admin = adminRepository.findByAccountAndPassword(account, password);
        return buildResult(admin, "账号或密码错误");
    }

    /**
     * 商家登录
     * 修改说明：拦截 rejected 状态，允许 pending 状态登录以便上传资质
     */
    @PostMapping("/login/merchant")
    public Map<String, Object> loginMerchant(@RequestBody Map<String, String> params) {
        String phone = params.get("phone");
        String password = params.get("password");
        Merchant merchant = merchantRepository.findByPhoneAndPassword(phone, password);

        // --- 修改开始 ---
        // 逻辑：只拦截 rejected (已拒绝) 的账号
        // pending (待审核) 和 approved (已通过) 的用户均可继续向下执行，登录成功
        if (merchant != null && "rejected".equals(merchant.getStatus())) {
            Map<String, Object> error = new HashMap<>();
            error.put("code", 403);
            error.put("msg", "账号审核未通过"); // 明确被拒绝时提示
            return error;
        }
        // --- 修改结束 ---

        return buildResult(merchant, "账号或密码错误");
    }

    // ================= 注册接口 =================

    // 1. 居民注册
    @PostMapping("/register/resident")
    public Map<String, Object> registerResident(@RequestBody User user) {
        // 查重
        if (userRepository.findByPhone(user.getPhone()) != null) {
            Map<String, Object> res = new HashMap<>();
            res.put("code", 400);
            res.put("msg", "该手机号已注册");
            return res;
        }
        try {
            User savedUser = userRepository.save(user);
            return buildResult(savedUser, "注册失败");
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> res = new HashMap<>();
            res.put("code", 500);
            res.put("msg", "服务器内部错误: " + e.getMessage());
            return res;
        }
    }

    // 2. 商家注册
    @PostMapping("/register/merchant")
    public Map<String, Object> registerMerchant(@RequestBody Merchant merchant) {
        // 查重
        if (merchantRepository.findByPhone(merchant.getPhone()) != null) {
            Map<String, Object> res = new HashMap<>();
            res.put("code", 400);
            res.put("msg", "该手机号已注册");
            return res;
        }
        // 设置默认状态为 pending (待审核)
        if (merchant.getStatus() == null) {
            merchant.setStatus("pending");
        }
        try {
            Merchant savedMerchant = merchantRepository.save(merchant);
            return buildResult(savedMerchant, "注册失败");
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> res = new HashMap<>();
            res.put("code", 500);
            res.put("msg", "服务器内部错误: " + e.getMessage());
            return res;
        }
    }

    // ================= 辅助方法 =================
    private Map<String, Object> buildResult(Object data, String errorMsg) {
        Map<String, Object> result = new HashMap<>();
        if (data != null) {
            result.put("code", 200);
            result.put("msg", "操作成功");
            result.put("data", data);
        } else {
            result.put("code", 400);
            result.put("msg", errorMsg);
        }
        return result;
    }
}