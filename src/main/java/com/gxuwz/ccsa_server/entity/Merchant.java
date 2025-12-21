package com.gxuwz.ccsa_server.entity;

// 【关键修改】将 jakarta 改为 javax
import javax.persistence.*;

@Entity
@Table(name = "merchant")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String community;
    private String name;
    private String contact;
    private String gender;
    private String phone;
    private String password;
    private String status;

    @Column(name = "qualification_status")
    private Integer qualificationStatus;

    public Merchant() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQualificationStatus() {
        return qualificationStatus;
    }

    public void setQualificationStatus(Integer qualificationStatus) {
        this.qualificationStatus = qualificationStatus;
    }
}