package com.taotao.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 蒋志鹏 on 2018/7/14.
 */

@Controller
public class LoginController {

    @RequestMapping("/page/{page}")
    private String toPage(@PathVariable String page){
        return page;
    }


    @RequestMapping("/checkLogin")
    private String isLogin(String redirectUrl){
        System.out.println(redirectUrl);
        return "login";
    }
}
