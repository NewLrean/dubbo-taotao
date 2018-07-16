package com.taotao.mapper;


import com.taotao.pojo.TbUser;

public interface TbUserMapper {
   TbUser checkName(String username);

   TbUser checkPhone(String phone);

   TbUser checkEmail(String email);

   void insertUser(TbUser tbUser);

   TbUser loginUser(String username,String password);
}