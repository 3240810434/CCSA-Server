package com.gxuwz.ccsa_server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gxuwz.ccsa_server.common.Result;
import com.gxuwz.ccsa_server.entity.Admin;
import com.gxuwz.ccsa_server.entity.User;
import com.gxuwz.ccsa_server.mapper.AdminMapper;
import com.gxuwz.ccsa_server.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

    // 居民注册
    @PostMapping("/resident/register")
    public Result<User> register(@RequestBody User user) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("phone", user.getPhone());
        if (userMapper.selectCount(query) > 0) {
            return Result.error("该手机号已注册");
        }
        userMapper.insert(user);
        return Result.success(user);
    }

    // 居民登录
    @PostMapping("/resident/login")
    public Result<User> login(@RequestBody User loginUser) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("phone", loginUser.getPhone());
        query.eq("password", loginUser.getPassword());
        User user = userMapper.selectOne(query);
        if (user == null) {
            return Result.error("手机号或密码错误");
        }
        return Result.success(user);
    }

    // 管理员登录
    @PostMapping("/admin/login")
    public Result<Admin> adminLogin(@RequestBody Admin loginAdmin) {
        QueryWrapper<Admin> query = new QueryWrapper<>();
        query.eq("account", loginAdmin.getAccount());
        Admin admin = adminMapper.selectOne(query);
        if (admin == null) return Result.error("账号不存在");
        if (!admin.getPassword().equals(loginAdmin.getPassword())) return Result.error("密码错误");
        // 校验小区权限
        if (loginAdmin.getCommunity() != null && !loginAdmin.getCommunity().equals(admin.getCommunity())) {
            return Result.error("无该小区权限");
        }
        return Result.success(admin);
    }
}