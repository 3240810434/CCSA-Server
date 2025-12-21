package com.gxuwz.ccsa_server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String account; // 管理员账号
    private String password;
    private String community;

    public Admin() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getCommunity() { return community; }
    public void setCommunity(String community) { this.community = community; }
}