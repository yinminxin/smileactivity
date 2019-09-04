package com.wondersgroup.smileactivity.smileactivity.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "activity_user_info", schema = "ueae", catalog = "ueae")
public class ActivityUserInfo {
    private String id;
    private String name;
    private String mobile;
    private String mail;
    private String activityInfoId;
    private Date createTime;
    private String createUserId;
    private String status;

    @Basic
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "generator", strategy = "uuid")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "mail")
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "activity_info_id")
    public String getActivityInfoId() {
        return activityInfoId;
    }

    public void setActivityInfoId(String activityInfoId) {
        this.activityInfoId = activityInfoId;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "create_user_id")
    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityUserInfo that = (ActivityUserInfo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(mobile, that.mobile) &&
                Objects.equals(mail, that.mail) &&
                Objects.equals(activityInfoId, that.activityInfoId) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(createUserId, that.createUserId) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, mobile, mail, activityInfoId, createTime, createUserId, status);
    }
}
