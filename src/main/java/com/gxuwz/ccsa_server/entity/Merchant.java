package com.gxuwz.ccsa_server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "merchant")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String community;
    private String merchantName;
    private String contactName;
    private String phone;
    private String password;
    private String status; // 审核状态: pending, approved, rejected

    public Merchant() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getCommunity() { return community; }
    public void setCommunity(String community) { this.community = community; }
    public String getMerchantName() { return merchantName; }
    public void setMerchantName(String merchantName) { this.merchantName = merchantName; }
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}