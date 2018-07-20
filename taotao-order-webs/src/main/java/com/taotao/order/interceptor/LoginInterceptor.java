package com.taotao.order.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.SSOClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/7/13.
 */
public class LoginInterceptor implements HandlerInterceptor{

    @Value("${TT_CART}")
    private String TT_CART;

    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;

    @Value("${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;

    @Reference
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String token = CookieUtils.getCookieValue(request, COOKIE_TOKEN_KEY);
        StringBuffer requestURL = request.getRequestURL();
        if(StringUtils.isBlank(token)){
            SSOClientUtil.redirectTSSOURL(response,requestURL.toString(),SSO_BASE_URL);
            return false;
        }
        TaotaoResult result = userService.getUserByToken(token);
        if(result.getStatus()!=200){
            SSOClientUtil.redirectTSSOURL(response,requestURL.toString(),SSO_BASE_URL);
            return false;
        }

        List<TbItem> list = getCookies(request);
        if(list==null||list.size()==0){
            try {
                response.sendRedirect("http://localhost:9009/cart/cart.html");
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        TbUser tbUser = (TbUser) result.getData();
        request.setAttribute("user",tbUser);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public List<TbItem> getCookies(HttpServletRequest request){
        String json = CookieUtils.getCookieValue(request, TT_CART,true);
        if(StringUtils.isNotEmpty(json)) {
            List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
            return list;
        }else {
            return new ArrayList<TbItem>();
        }

    }
}
