package com.bingo.ssh.shop.bean.buyer;

import com.bingo.ssh.shop.bean.enums.GenderEnum;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 26685 on 2017/7/1.
 */
@Entity
public class Buyer {
    private String username;
    private String password;
    private String realname;
    private String email;
    private GenderEnum gender = GenderEnum.Man;
    private ContactInfo contactInfo;
    private boolean visible=true;
    private Date registerTime=new Date();

    @Id
    @Column(length = 20)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(length = 32,nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Column(length = 64,nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(length = 30)
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @Enumerated(EnumType.STRING)
    @Column(length = 5,nullable = false)
    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id")
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Column(nullable = false)
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Buyer(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Buyer() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Buyer buyer = (Buyer) o;

        return username != null ? username.equals(buyer.username) : buyer.username == null;

    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
