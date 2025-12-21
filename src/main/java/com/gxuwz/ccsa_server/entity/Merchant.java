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
    private String gender;
    private String phone;
    private String password;

    // --- 新增字段以匹配安卓端逻辑 ---
    // 状态字符串 (pending/approved/rejected)
    private String status;

    // 资质状态码: 0=未认证, 1=审核中, 2=已通过, 3=未通过
    // 安卓端主要依赖这个int字段判断UI
    private Integer qualificationStatus;

    // 图片存储路径 (服务器本地路径或URL)
    private String idCardFrontUri;
    private String idCardBackUri;
    private String licenseUri;

    public Merchant() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCommunity() { return community; }
    public void setCommunity(String community) { this.community = community; }

    public String getMerchantName() { return merchantName; }
    public void setMerchantName(String merchantName) { this.merchantName = merchantName; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getQualificationStatus() { return qualificationStatus; }
    public void setQualificationStatus(Integer qualificationStatus) { this.qualificationStatus = qualificationStatus; }

    public String getIdCardFrontUri() { return idCardFrontUri; }
    public void setIdCardFrontUri(String idCardFrontUri) { this.idCardFrontUri = idCardFrontUri; }

    public String getIdCardBackUri() { return idCardBackUri; }
    public void setIdCardBackUri(String idCardBackUri) { this.idCardBackUri = idCardBackUri; }

    public String getLicenseUri() { return licenseUri; }
    public void setLicenseUri(String licenseUri) { this.licenseUri = licenseUri; }
}