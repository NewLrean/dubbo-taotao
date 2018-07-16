package com.taotao.filter;

import com.taotao.utils.SSOClientUtil;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 蒋志鹏 on 2018/7/13.
 */
public class SSOClientFilter implements Filter{
    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;

    @Value("${WEB_HOST_RUL}")
    private String WEB_HOST_RUL;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        System.out.println("开始拦截-------------------------");
        HttpSession session = request.getSession();
        Boolean isLogin = (Boolean) session.getAttribute("isLogin");
        //有无局部会话
        if(isLogin!=null&&isLogin){
            filterChain.doFilter(request,response);
            return;
        }
        System.out.println(WEB_HOST_RUL);
        SSOClientUtil.redirectTSSOURL(request,response,WEB_HOST_RUL,SSO_BASE_URL);
    }

    @Override
    public void destroy() {

    }
}
