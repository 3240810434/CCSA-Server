package com.gxuwz.ccsa_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@Data
@TableName("admin_notices")
public class AdminNotice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private String targetType; // RESIDENT, MERCHANT, BOTH
    private String targetBuildings;
    private String attachmentPath;
    private Integer status; // 0-草稿, 1-已发布
    private Date createTime;
    private Date publishTime;
}