package com.gxuwz.ccsa_server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gxuwz.ccsa_server.entity.AdminNotice;
import com.gxuwz.ccsa_server.mapper.AdminNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AdminNoticeService {

    @Autowired
    private AdminNoticeMapper noticeMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String NOTICE_CACHE_KEY_RESIDENT = "notice:list:resident";
    private static final String NOTICE_CACHE_KEY_MERCHANT = "notice:list:merchant";

    // 保存或更新通知（草稿或发布）
    public void saveOrUpdateNotice(AdminNotice notice) {
        if (notice.getId() == null) {
            notice.setCreateTime(new Date());
        }
        if (notice.getStatus() == 1 && notice.getPublishTime() == null) {
            notice.setPublishTime(new Date());
        }

        if (notice.getId() == null) {
            noticeMapper.insert(notice);
        } else {
            noticeMapper.updateById(notice);
        }

        // 如果是发布状态，清除缓存，保证用户读到最新的
        if (notice.getStatus() == 1) {
            clearCache();
        }
    }

    // 删除通知
    public void deleteNotice(Long id) {
        noticeMapper.deleteById(id);
        clearCache();
    }

    // 管理员获取列表（包含草稿和已发布，不走缓存，因为实时性要求高且并发低）
    public List<AdminNotice> getAdminList(Integer status) {
        QueryWrapper<AdminNotice> query = new QueryWrapper<>();
        if (status != null) {
            query.eq("status", status);
        }
        query.orderByDesc("create_time");
        return noticeMapper.selectList(query);
    }

    // 居民/商家获取列表（走 Redis 缓存，应对高并发）
    public List<AdminNotice> getUserList(String userType) {
        String cacheKey = "RESIDENT".equals(userType) ? NOTICE_CACHE_KEY_RESIDENT : NOTICE_CACHE_KEY_MERCHANT;

        // 1. 查缓存
        List<AdminNotice> cachedList = (List<AdminNotice>) redisTemplate.opsForValue().get(cacheKey);
        if (cachedList != null) {
            return cachedList;
        }

        // 2. 查数据库
        QueryWrapper<AdminNotice> query = new QueryWrapper<>();
        query.eq("status", 1); // 只看已发布
        // 逻辑：目标是 BOTH 或者 匹配当前用户类型
        query.and(wrapper -> wrapper.eq("target_type", "BOTH").or().eq("target_type", userType));
        query.orderByDesc("publish_time");

        List<AdminNotice> dbList = noticeMapper.selectList(query);

        // 3. 写缓存 (设置过期时间，例如 10 分钟，防止数据不一致太久)
        if (dbList != null && !dbList.isEmpty()) {
            redisTemplate.opsForValue().set(cacheKey, dbList, 10, TimeUnit.MINUTES);
        }

        return dbList;
    }

    private void clearCache() {
        redisTemplate.delete(NOTICE_CACHE_KEY_RESIDENT);
        redisTemplate.delete(NOTICE_CACHE_KEY_MERCHANT);
    }
}