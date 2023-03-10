package com.huashijun.login.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.huashijun.redis.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author huashijun
 */
@Controller
public class IndexController {

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/")
    public String index(){
        return "register";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/success")
    public String success(){
        return "success";
    }

    @GetMapping("/face/login")
    @ResponseBody
    public String login(@RequestParam("image") String image){
        //用户存储的图片
        String userImage = (String) redisUtils.hashGet("face", "花十君");
        //人脸识别的图片
        String faceImage = image.substring(22);
        //http调用第三方api，获取比较结果
        JSONObject param = JSONUtil.createObj();
        param.set("user_image", userImage);
        param.set("face_image", faceImage);
        String result = HttpUtil.createPost("localhost:8000/faceRecognition/")
                .body(String.valueOf(param)).execute().body();
        if("True".equals(result)) {
            return "success";
        }else{
            return "fail";
        }
    }

    @GetMapping("/face/register")
    @ResponseBody
    public String register(@RequestParam("image") String image,
                           @RequestParam("username") String username){
        // 存储用户图片和姓名
        String value = image.substring(22);
        redisUtils.hashPut("face",username,value);
        return "login";
    }
}