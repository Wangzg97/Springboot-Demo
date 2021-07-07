package com.example.securitydemo.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.map.MapUtil;
import com.example.securitydemo.common.lang.ResponseResult;
import com.example.securitydemo.utils.RedisUtil;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@RestController
public class AuthController {
    @Autowired
    Producer producer;
    @Autowired
    RedisUtil redisUtil;

    @GetMapping("/captcha")
    public ResponseResult captcha() throws IOException {
        String code = producer.createText();
        String key = UUID.randomUUID().toString();

        code = "code";
        key = "key";

        BufferedImage img = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", outputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";
        String base64Img = str + encoder.encode(outputStream.toByteArray());

        //存入redis
        redisUtil.hset("captcha", key, code, 120);
        return ResponseResult.succ(
                MapUtil.builder()
                .put("token", key)
                .put("captchaImg", base64Img)
                .build()
        );
    }
}
