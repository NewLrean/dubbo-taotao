package com.taotao.utils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 蒋志鹏 on 2018/7/13.
 */
public class SSOClientUtil extends HttpServlet {

    /**
     *
     * @param request
     * @param URL 当前的请求地址
     * @return
     */
    public static String getRedirectURL(HttpServletRequest request,String URL){
        return URL+request.getServletPath();
    }

    /**
     *
     * @param request
     * @param response
     * @param URL  当前请求地址
     * @param SERVER_URL  想要访问的路径
     * @throws IOException
     */
    public static void redirectTSSOURL(HttpServletRequest request, HttpServletResponse response,String URL,String SERVER_URL) throws IOException {
        String redirectURL = getRedirectURL(request, URL);
        StringBuffer buffer=new StringBuffer();
        buffer.append(SERVER_URL).append("/checkLogin?redirectUrl=")
                .append(redirectURL);
        response.sendRedirect(buffer.toString());
    }
}
