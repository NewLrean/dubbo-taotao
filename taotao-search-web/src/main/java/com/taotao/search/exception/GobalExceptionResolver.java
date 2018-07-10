package com.taotao.search.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 蒋志鹏 on 2018/7/6.
 */
public class GobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {
        e.printStackTrace();

        GobalException gobalException = null;

            //如果抛出的是系统自定义异常则直接转换

        if(e instanceof GobalException){

            gobalException = (GobalException)e;

        }else{

//如果抛出的不是系统自定义异常则重新构造一个系统错误异常。

            gobalException = new GobalException ("系统错误，请与系统管理 员联系！");

        }

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("message", gobalException.getMessage());

        modelAndView.setViewName("error/exception");

        return modelAndView;
    }
}
