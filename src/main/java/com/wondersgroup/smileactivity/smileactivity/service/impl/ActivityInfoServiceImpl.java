package com.wondersgroup.smileactivity.smileactivity.service.impl;

import com.wondersgroup.smileactivity.smileactivity.entity.ActivityInfo;
import com.wondersgroup.smileactivity.smileactivity.repository.ActivityInfoRepository;
import com.wondersgroup.smileactivity.smileactivity.service.ActivityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityInfoServiceImpl implements ActivityInfoService {

    @Autowired
    public ActivityInfoRepository activityInfoRepository;

    public List<ActivityInfo> getAllActivityInfo(){
        return activityInfoRepository.findAll();
    }

    @Override
    public ActivityInfo getActivityInfoById(String id) {
        return activityInfoRepository.getOne(id);
    }

}
