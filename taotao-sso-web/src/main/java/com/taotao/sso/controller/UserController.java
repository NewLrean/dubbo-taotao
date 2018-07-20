package com.taotao.sso.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 蒋志鹏 on 2018/7/14.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @Value("${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;



    @RequestMapping(value = "/check/{param}/{type}",method = RequestMethod.GET)
    @ResponseBody
    private TaotaoResult checkData(@PathVariable String param,@PathVariable Integer type){
        TaotaoResult taotaoResult = userService.checkData(param, type);
        return taotaoResult;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    private TaotaoResult userRegsiter(TbUser tbUser){
        System.out.println(tbUser);
        TaotaoResult taotaoResult = userService.insertUser(tbUser);
        return taotaoResult;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    private TaotaoResult userToLogin(String username, String password, Model model,
                               HttpServletRequest request, HttpServletResponse response){
        TaotaoResult taotaoResult = userService.toLogin(username, password);
        if(taotaoResult.getStatus()==200)
        CookieUtils.setCookie(request,response,COOKIE_TOKEN_KEY,taotaoResult.getData().toString());
        return taotaoResult;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    private String ToLogOut(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies){
            if(COOKIE_TOKEN_KEY.equals(cookie.getName())){
                cookie.setValue(null);
                cookie.setMaxAge(0);// 立即销毁cookie
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }

        TaotaoResult logout = userService.logout();
        return "login";
    }

    @RequestMapping(value = "/token/{token}",method = RequestMethod.GET)
    @ResponseBody
    private String getUserByToken(@PathVariable String token,String callback){
        TaotaoResult result = userService.getUserByToken(token);
        if(StringUtils.isNotEmpty(callback)){
            return callback+"("+JsonUtils.objectToJson(result)+");";
        }
        return JsonUtils.objectToJson(result);
    }



}
