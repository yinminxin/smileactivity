package com.wondersgroup.smileactivity.smileactivity.service;

import com.wondersgroup.smileactivity.smileactivity.entity.ActivityInfo;

import java.util.List;

public interface ActivityInfoService {

    List<ActivityInfo> getAllActivityInfo();

    ActivityInfo getActivityInfoById(String id);
}
