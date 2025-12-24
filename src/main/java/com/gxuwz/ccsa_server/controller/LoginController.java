package com.gxuwz.ccsa_server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gxuwz.ccsa_server.common.Result;
import com.gxuwz.ccsa_server.entity.Admin;
import com.gxuwz.ccsa_server.entity.Merchant;
import com.gxuwz.ccsa_server.entity.User;
import com.gxuwz.ccsa_server.mapper.AdminMapper;
import com.gxuwz.ccsa_server.mapper.MerchantMapper;
import com.gxuwz.ccsa_server.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api") // 所有接口都以 /api 开头
public class LoginController {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MerchantMapper merchantMapper; // 新增：注入 MerchantMapper

    // ---------------------- 管理员接口 ----------------------

    // 管理员登录 -> /api/admin/login
    @PostMapping("/admin/login")
    public Result<Admin> adminLogin(@RequestBody Admin admin) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", admin.getAccount());
        queryWrapper.eq("password", admin.getPassword());

        Admin result = adminMapper.selectOne(queryWrapper);
        if (result != null) {
            return Result.success(result);
        } else {
            return Result.error("账号或密码错误");
        }
    }

    // ---------------------- 居民（用户）接口 ----------------------

    // 居民登录 -> /api/user/login
    @PostMapping("/user/login")
    public Result<User> userLogin(@RequestBody User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", user.getPhone());
        queryWrapper.eq("password", user.getPassword());

        User result = userMapper.selectOne(queryWrapper);
        if (result != null) {
            return Result.success(result);
        } else {
            return Result.error("手机号或密码错误");
        }
    }

    // 居民注册 -> /api/user/register
    @PostMapping("/user/register")
    public Result<User> userRegister(@RequestBody User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", user.getPhone());
        if (userMapper.selectCount(queryWrapper) > 0) {
            return Result.error("该手机号已注册");
        }

        int rows = userMapper.insert(user);
        if (rows > 0) {
            return Result.success(user);
        } else {
            return Result.error("注册失败");
        }
    }

    // ---------------------- 商家接口 (新增) ----------------------

    // 商家登录 -> /api/merchant/login
    @PostMapping("/merchant/login")
    public Result<Merchant> merchantLogin(@RequestBody Merchant merchant) {
        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", merchant.getPhone());
        queryWrapper.eq("password", merchant.getPassword());

        Merchant result = merchantMapper.selectOne(queryWrapper);
        if (result != null) {
            return Result.success(result);
        } else {
            return Result.error("手机号或密码错误");
        }
    }

    // 商家注册 -> /api/merchant/register
    @PostMapping("/merchant/register")
    public Result<Merchant> merchantRegister(@RequestBody Merchant merchant) {
        // 1. 检查手机号是否已存在
        QueryWrapper<Merchant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", merchant.getPhone());
        if (merchantMapper.selectCount(queryWrapper) > 0) {
            return Result.error("该手机号已注册");
        }

        // 2. 设置默认值 (如果前端没传，则设置初始状态)
        // 营业状态默认开启
        if (merchant.getOpen() == null) {
            merchant.setOpen(true);
        }
        // 资质审核状态默认 0 (未审核/审核中)
        if (merchant.getQualificationStatus() == null) {
            merchant.setQualificationStatus(0);
        }

        // 3. 插入数据库
        int rows = merchantMapper.insert(merchant);
        if (rows > 0) {
            return Result.success(merchant);
        } else {
            return Result.error("注册失败");
        }
    }
}