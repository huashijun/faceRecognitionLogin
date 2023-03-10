# 描述
人脸识别登录服务

# 应用
1. 人脸识别登录

# 技术范畴
Vue2/SpringBoot2/Redis/Django/face_recognition

# 思路流程
![思路流程](https://github.com/huashijun/huashijun.github.io/raw/master/faceRecognitionLogin.jpg)

# 原型设计
![原型设计1](https://github.com/huashijun/huashijun.github.io/raw/master/faceRecognitionLogin1.jpg)
![原型设计2](https://github.com/huashijun/huashijun.github.io/raw/master/faceRecognitionLogin2.jpg)

# 前端页面设计
![前端页面1](https://github.com/huashijun/huashijun.github.io/raw/master/faceRecognitionLogin3.jpg)
![前端页面2](https://github.com/huashijun/huashijun.github.io/raw/master/faceRecognitionLogin4.jpg)


# 后端接口设计
~~~
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

def face_image_check():
    known_image = face_recognition.load_image_file("user_image.jpg")
    unknown_image = face_recognition.load_image_file("face_image.jpg")

    biden_encoding = face_recognition.face_encodings(known_image)[0]
    unknown_encoding = face_recognition.face_encodings(unknown_image)[0]

    results = face_recognition.compare_faces([biden_encoding], unknown_encoding)
    return HttpResponse(results)
~~~

# 运行

# 点赞
一键三连是分享创作的动力
