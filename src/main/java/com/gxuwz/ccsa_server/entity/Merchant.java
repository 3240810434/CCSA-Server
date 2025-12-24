package com.gxuwz.ccsa_server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

@TableName("merchant")
public class Merchant implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String community;
    private String merchantName;
    private String contactName;
    private String gender;
    private String phone;
    private String password;
    private String avatar;
    private Boolean isOpen;
    private Integer qualificationStatus;
    private String idCardFrontUri;
    private String idCardBackUri;
    private String licenseUri;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
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
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public Boolean getOpen() { return isOpen; }
    public void setOpen(Boolean open) { isOpen = open; }
    public Integer getQualificationStatus() { return qualificationStatus; }
    public void setQualificationStatus(Integer qualificationStatus) { this.qualificationStatus = qualificationStatus; }
    public String getIdCardFrontUri() { return idCardFrontUri; }
    public void setIdCardFrontUri(String idCardFrontUri) { this.idCardFrontUri = idCardFrontUri; }
    public String getIdCardBackUri() { return idCardBackUri; }
    public void setIdCardBackUri(String idCardBackUri) { this.idCardBackUri = idCardBackUri; }
    public String getLicenseUri() { return licenseUri; }
    public void setLicenseUri(String licenseUri) { this.licenseUri = licenseUri; }
}