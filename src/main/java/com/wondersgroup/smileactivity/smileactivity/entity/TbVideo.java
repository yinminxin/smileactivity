package com.wondersgroup.smileactivity.smileactivity.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_video",catalog = "ueae")
public class TbVideo {

  private String videoId;
  private String videoType;
  private String videoSourceIntroduce;
  private String videoName;
  private String videoDescribe;
  private String videoImage;
  private String videoPlayLink;
  private Timestamp createTime;
  private String createId;
  private Timestamp updateTime;
  private String updateId;


    @Basic
    @Id
    @Column(name = "video_id")
  public String getVideoId() {
    return videoId;
  }

  public void setVideoId(String videoId) {
    this.videoId = videoId;
  }

    @Basic
    @Column(name = "video_type")
  public String getVideoType() {
    return videoType;
  }

  public void setVideoType(String videoType) {
    this.videoType = videoType;
  }

    @Basic
    @Column(name = "video_source_introduce")
  public String getVideoSourceIntroduce() {
    return videoSourceIntroduce;
  }

  public void setVideoSourceIntroduce(String videoSourceIntroduce) {
    this.videoSourceIntroduce = videoSourceIntroduce;
  }

    @Basic
    @Column(name = "video_name")
  public String getVideoName() {
    return videoName;
  }

  public void setVideoName(String videoName) {
    this.videoName = videoName;
  }

    @Basic
    @Column(name = "video_describe")
  public String getVideoDescribe() {
    return videoDescribe;
  }

  public void setVideoDescribe(String videoDescribe) {
    this.videoDescribe = videoDescribe;
  }

    @Basic
    @Column(name = "video_image")
  public String getVideoImage() {
    return videoImage;
  }

  public void setVideoImage(String videoImage) {
    this.videoImage = videoImage;
  }

    @Basic
    @Column(name = "video_play_link")
  public String getVideoPlayLink() {
    return videoPlayLink;
  }

  public void setVideoPlayLink(String videoPlayLink) {
    this.videoPlayLink = videoPlayLink;
  }

    @Basic
    @Column(name = "create_time")
  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }

    @Basic
    @Column(name = "create_id")
  public String getCreateId() {
    return createId;
  }

  public void setCreateId(String createId) {
    this.createId = createId;
  }

    @Basic
    @Column(name = "update_time")
  public java.sql.Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.sql.Timestamp updateTime) {
    this.updateTime = updateTime;
  }

    @Basic
    @Column(name = "update_id")
  public String getUpdateId() {
    return updateId;
  }

  public void setUpdateId(String updateId) {
    this.updateId = updateId;
  }

    public TbVideo(String videoId, String videoType, String videoSourceIntroduce, String videoName, String videoDescribe, String videoImage, String videoPlayLink, Timestamp createTime, String createId, Timestamp updateTime, String updateId) {
        this.videoId = videoId;
        this.videoType = videoType;
        this.videoSourceIntroduce = videoSourceIntroduce;
        this.videoName = videoName;
        this.videoDescribe = videoDescribe;
        this.videoImage = videoImage;
        this.videoPlayLink = videoPlayLink;
        this.createTime = createTime;
        this.createId = createId;
        this.updateTime = updateTime;
        this.updateId = updateId;
    }

    public TbVideo() {

    }
}
