package com.gxuwz.ccsa_server.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String gender;
    private String phone;
    private String password;
    private String community;
    private String building;
    private String room;
    private String avatar;
}