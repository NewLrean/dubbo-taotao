package com.taotao.sso.service.impl;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.redis.JedisClient;
import com.taotao.sso.service.UserService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

/**
 * Created by 蒋志鹏 on 2018/7/14.
 */

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    TbUserMapper tbUserMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${TOKEN_USER_INFO}")
    private String TOKEN_USER_INFO;

    @Value("${TOKEN_USER_TIME}")
    private int TOKEN_USER_TIME;

    @Override
    public TaotaoResult checkData(String param, int type) {

        if (type==1){
            TbUser tbUser = tbUserMapper.checkName(param);
            if (tbUser!=null){
                return TaotaoResult.bulid(false);
            }
        }else if(type==2){

            TbUser tbUser = tbUserMapper.checkPhone(param);
            if (tbUser!=null){
                return TaotaoResult.bulid(false);
            }
        }
       /* else if(type==3){

            TbUser tbUser = tbUserMapper.checkEmail(param);
            if (tbUser!=null){
                return TaotaoResult.bulid(false);
            }
        }*/
        else {
            return TaotaoResult.bulid(500,"数据不可用");
        }
        return TaotaoResult.bulid(true);
    }

    @Override
    public TaotaoResult insertUser(TbUser tbUser) {
        if(StringUtils.isEmpty(tbUser.getUsername())){
            return TaotaoResult.bulid(400,"用户名不能为空");
        }
        if(StringUtils.isEmpty(tbUser.getPassword())){
            return TaotaoResult.bulid(400,"密码不能为空");
        }
        if(StringUtils.isEmpty(tbUser.getPhone())){
            return TaotaoResult.bulid(400,"电话号码不能为空");
        }
        /*if(StringUtils.isEmpty(tbUser.getEmail())){
            return TaotaoResult.bulid(400,"邮箱不能为空");
        }*/
        TbUser tbUser1=null;
        tbUser1= tbUserMapper.checkName(tbUser.getUsername());
        if(tbUser1!=null){
                    return TaotaoResult.bulid(400,"用户已经被注册");
        }
        tbUser1= tbUserMapper.checkPhone(tbUser.getPhone());
                if (tbUser1!=null){
                    return TaotaoResult.bulid(400,"电话已经被注册");
                }
       /* tbUser1 = tbUserMapper.checkEmail(tbUser.getEmail());
                if (tbUser1!=null){
                    return TaotaoResult.bulid(400,"邮箱已经被注册");
                }*/

        Date date=new Date();
                tbUser.setCreated(date);
                tbUser.setUpdated(date);
        String s = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(s);

        tbUserMapper.insertUser(tbUser);


        return TaotaoResult.bulid();
    }

    @Override
    public TaotaoResult toLogin(String username, String password) {

        if(StringUtils.isEmpty(username)){
            return TaotaoResult.bulid(400,"用户名不能为空");
        }


        if(StringUtils.isEmpty(password)){
            return TaotaoResult.bulid(400,"密码不能为空");
        }

        TbUser tbUser = tbUserMapper.loginUser(username, DigestUtils.md5DigestAsHex(password.getBytes()));
        if(tbUser==null){
            return TaotaoResult.bulid(400,"登陆失败");
        }

        String toString = UUID.randomUUID().toString();
        String token = toString.replace("-", "");
        tbUser.setPassword(null);
        System.out.println(tbUser);
        String json = JsonUtils.objectToJson(tbUser);
        jedisClient.set(TOKEN_USER_INFO+":"+token,json);
        jedisClient.expire(TOKEN_USER_INFO+":"+token,TOKEN_USER_TIME);
        return TaotaoResult.bulid(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(TOKEN_USER_INFO + ":" + token);
        TbUser tbUser=null;
        if(json!=null) {
            tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
            return TaotaoResult.bulid(tbUser);
        }
        return TaotaoResult.bulid(500,"登录失效");
    }

    @Override
    public TaotaoResult logout() {
        if(jedisClient.exists(TOKEN_USER_INFO))
        jedisClient.del(TOKEN_USER_INFO);
        return TaotaoResult.bulid();
    }
}
