package com.wondersgroup.smileactivity.smileactivity.controller;

import com.wondersgroup.smileactivity.smileactivity.entity.ActivityUserInfo;
import com.wondersgroup.smileactivity.smileactivity.service.ActivityUserInfoService;
import com.wondersgroup.smileactivity.smileactivity.utils.RSAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/activityUserInfo")
public class ActivityUserInfoController extends BaseController {
    
    @Autowired
    public ActivityUserInfoService activityUserInfoService;
    
    @RequestMapping(value = "saveInfo",method = RequestMethod.POST)
    public ResponseEntity saveInfo(ActivityUserInfo activityUserInfo,String token){
        try {
            String tokenKey = (String) this.getSessionAttr("tokenKey");
            this.setSessionAttr("tokenKey",null);
            this.response.addHeader("x-frame-options","SAMEORIGIN");
            if(token==null||tokenKey==null||!token.equals(tokenKey)){
//                return new ResponseEntity(HttpStatus.BAD_REQUEST);
                return ResponseEntity.ok("系统繁忙，请刷新页面重试!");
            }
            activityUserInfo.setName(RSAUtils.decryptBase64(activityUserInfo.getName()));
            activityUserInfo.setMail(RSAUtils.decryptBase64(activityUserInfo.getMail()));
            activityUserInfo.setMobile(RSAUtils.decryptBase64(activityUserInfo.getMobile()));
//            String regexName="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            String regexName = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
            String regexMail="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";;
            String regexMobile="^\\d{11}$";
            String name=activityUserInfo.getName();
            String mail = activityUserInfo.getMail();
            String mobile = activityUserInfo.getMobile();
            Pattern pName = Pattern.compile(regexName);
            Pattern pMail = Pattern.compile(regexMail);
            Pattern pMobile = Pattern.compile(regexMobile);
            Matcher mName = pName.matcher(name);
            Matcher mMail = pMail.matcher(mail);
            Matcher mMobile = pMobile.matcher(mobile);
            boolean bName = mName.find();
            boolean bMail = mMail.matches();
            boolean bMobile = mMobile.matches();
            if(name==null||"".equals(name)||bName){
                this.setSessionAttr("tokenKey",tokenKey);
                return ResponseEntity.ok("姓名不能包含特殊字符!");
            }
            if(mail==null||"".equals(mail)||!bMail){
                this.setSessionAttr("tokenKey",tokenKey);
                return ResponseEntity.ok("邮箱不合法!");
            }
            if(mobile==null||"".equals(mobile)||!bMobile){
                this.setSessionAttr("tokenKey",tokenKey);
                return ResponseEntity.ok("手机号不合法!");
            }
            activityUserInfoService.saveInfo(activityUserInfo);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
