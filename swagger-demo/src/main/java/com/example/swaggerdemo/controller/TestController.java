package com.example.swaggerdemo.controller;

import com.example.swaggerdemo.common.dto.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: wangzg
 * @Time: 2021/7/19
 */
@Api("测试controller")
@RestController
public class TestController {

    @ApiOperation("test方法")
    @PostMapping("/test")
    public String test1(@RequestBody Param param) {

        return param.toString();
    }

    @ApiOperation("test2方法")

    @GetMapping("/param/{id}")
    public String test2(@ApiParam(name = "id", value = "参数id", required = true) @RequestParam("id") String id) {
        return "id ==> " +id;
    }
}
