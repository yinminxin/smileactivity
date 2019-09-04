package com.wondersgroup.smileactivity.smileactivity.controller;

import com.wondersgroup.smileactivity.smileactivity.utils.RSAUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("rsa")
public class RSAController extends BaseController {

    @RequestMapping(value = "/getPublicKey", method = RequestMethod.POST)
    public ResponseEntity getKey(String token){
        String tokenKey = (String) this.getSessionAttr("tokenKey");
        this.response.addHeader("x-frame-options","SAMEORIGIN");
        if(token==null||tokenKey==null||!token.equals(tokenKey)){
//                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            return ResponseEntity.ok("error");
        }
        String publicKey = RSAUtils.generateBase64PublicKey();
        return ResponseEntity.ok(publicKey);
    }
}
