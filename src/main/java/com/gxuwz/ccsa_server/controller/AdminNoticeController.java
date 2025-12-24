package com.gxuwz.ccsa_server.controller;

import com.gxuwz.ccsa_server.common.Result;
import com.gxuwz.ccsa_server.entity.AdminNotice;
import com.gxuwz.ccsa_server.service.AdminNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class AdminNoticeController {

    @Autowired
    private AdminNoticeService noticeService;

    // 管理员：保存/发布/更新通知
    @PostMapping("/save")
    public Result<String> saveNotice(@RequestBody AdminNotice notice) {
        noticeService.saveOrUpdateNotice(notice);
        return Result.success("操作成功");
    }

    // 管理员：删除通知
    @DeleteMapping("/delete/{id}")
    public Result<String> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return Result.success("删除成功");
    }

    // 管理员：获取列表 (status: 0草稿, 1已发布, null全部)
    @GetMapping("/admin/list")
    public Result<List<AdminNotice>> getAdminList(@RequestParam(required = false) Integer status) {
        return Result.success(noticeService.getAdminList(status));
    }

    // 居民/商家：获取通知列表 (高并发接口)
    @GetMapping("/user/list")
    public Result<List<AdminNotice>> getUserList(@RequestParam String userType) {
        // userType: "RESIDENT" or "MERCHANT"
        return Result.success(noticeService.getUserList(userType));
    }
}