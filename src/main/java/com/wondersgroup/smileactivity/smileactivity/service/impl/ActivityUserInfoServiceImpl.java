package com.wondersgroup.smileactivity.smileactivity.service.impl;

import com.wondersgroup.smileactivity.smileactivity.entity.ActivityInfo;
import com.wondersgroup.smileactivity.smileactivity.entity.ActivityUserInfo;
import com.wondersgroup.smileactivity.smileactivity.repository.ActivityUserInfoRepository;
import com.wondersgroup.smileactivity.smileactivity.service.ActivityInfoService;
import com.wondersgroup.smileactivity.smileactivity.service.ActivityUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ActivityUserInfoServiceImpl implements ActivityUserInfoService {

    @Autowired
    public ActivityUserInfoRepository activityUserInfoRepository;

    @Override
    @Transactional
    public void saveInfo(ActivityUserInfo activityUserInfo) {
        activityUserInfo.setCreateTime(new Date());
        activityUserInfo.setStatus("1");
        activityUserInfoRepository.save(activityUserInfo);
    }
}
