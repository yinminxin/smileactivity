package com.wondersgroup.smileactivity.smileactivity.controller;

import com.wondersgroup.smileactivity.smileactivity.entity.ActivityInfo;
import com.wondersgroup.smileactivity.smileactivity.service.ActivityInfoService;
import com.wondersgroup.smileactivity.smileactivity.utils.RandomCodeUtils;
import com.wondersgroup.smileactivity.smileactivity.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Map;

@Controller
@RequestMapping("")
public class ActivityController extends BaseController {

    private final SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

    @Autowired
    public ActivityInfoService activityInfoService;

    @RequestMapping(value = "/activityIndex",method = RequestMethod.GET)
    public String activityInfoPage(Map<String,Object> map,String activityId){
//        ActivityInfo activityInfo=activityInfoService.getActivityInfoById("shengxuenadianshi");
        this.response.addHeader("x-frame-options","SAMEORIGIN");
        ActivityInfo activityInfo=activityInfoService.getActivityInfoById(activityId);
        if(activityInfo==null|| "".equals(activityInfo)){
            return "error";
        }
        String startTime = df.format(activityInfo.getStartTime());
        String endTime = df.format(activityInfo.getEndTime());
        String token = RandomCodeUtils.getUUid();
        this.setSessionAttr("tokenKey",token);
        map.put("activityInfo",activityInfo);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("token",token);
        return "activity";
    }
}
