package com.bingo.ssh.shop.bean.buyer;

import javax.persistence.*;

/**
 * Created by 26685 on 2017/7/5.
 */
@Entity
public class ContactInfo {
    private Long contactId;
    private String address;
    private String postcode;
    private String phone;
    private String mobile;
    private Buyer buyer;

    @Id
    @GeneratedValue
    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    @Column(length = 60,nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(length = 6)
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Column(length = 18)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(length = 12)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @OneToOne(mappedBy = "contactInfo",cascade = CascadeType.REFRESH)
    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactInfo that = (ContactInfo) o;

        return contactId != null ? contactId.equals(that.contactId) : that.contactId == null;

    }

    @Override
    public int hashCode() {
        return contactId != null ? contactId.hashCode() : 0;
    }
}
