package com.example.springboottaskcore.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * User 控制器
 *
 * @author zhu_zishuang
 * @date 2020-09-16
 */
@RestController
@RequestMapping(value = "/user", method = {RequestMethod.POST})
@Api(tags = "用户管理")
@Slf4j
public class UserController extends BaseController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }
}
