package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

/**
 * Created by 蒋志鹏 on 2018/7/14.
 */
public interface UserService {
    TaotaoResult checkData(String param,int type);

    TaotaoResult insertUser(TbUser tbUser);

    TaotaoResult toLogin(String username,String password);

    TaotaoResult getUserByToken(String token);

    TaotaoResult logout();
}
