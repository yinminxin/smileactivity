package com.wondersgroup.smileactivity.smileactivity.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "activity_info", catalog = "ueae")
public class ActivityInfo {
    private String id;
    private String name;
    private String address;
    private String mobile;
    private String themeImg;
    private String activeContentImg;
    private String activeLabel;
    private Date startTime;
    private Date endTime;
    private String status;
    private Date applyStartTime;
    private Date applyEndTime;
    private Double cost;

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
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    @Column(name = "theme_img")
    public String getThemeImg() {
        return themeImg;
    }

    public void setThemeImg(String themeImg) {
        this.themeImg = themeImg;
    }

    @Basic
    @Column(name = "active_content_img")
    public String getActiveContentImg() {
        return activeContentImg;
    }

    public void setActiveContentImg(String activeContentImg) {
        this.activeContentImg = activeContentImg;
    }

    @Basic
    @Column(name = "active_label")
    public String getActiveLabel() {
        return activeLabel;
    }

    public void setActiveLabel(String activeLabel) {
        this.activeLabel = activeLabel;
    }

    @Basic
    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "apply_start_time")
    public Date getApplyStartTime() {
        return applyStartTime;
    }

    public void setApplyStartTime(Date applyStartTime) {
        this.applyStartTime = applyStartTime;
    }

    @Basic
    @Column(name = "apply_end_time")
    public Date getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(Date applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    @Basic
    @Column(name = "cost")
    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityInfo that = (ActivityInfo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(address, that.address) &&
                Objects.equals(mobile, that.mobile) &&
                Objects.equals(themeImg, that.themeImg) &&
                Objects.equals(activeContentImg, that.activeContentImg) &&
                Objects.equals(activeLabel, that.activeLabel) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(status, that.status) &&
                Objects.equals(applyStartTime, that.applyStartTime) &&
                Objects.equals(applyEndTime, that.applyEndTime) &&
                Objects.equals(cost, that.cost);
    }


}
