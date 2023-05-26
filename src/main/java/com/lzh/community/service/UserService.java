package com.lzh.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzh.community.model.dto.LoginData;
import com.lzh.community.model.dto.RegisterData;
import com.lzh.community.model.entity.User;

public interface UserService extends IService<User> {

    /**
     * 注册
     */
    User executeRegister(RegisterData registerData);

    /**
     * 获取用户信息
     *
     * @param username
     * @return dbUser
     */
    User getUserByUsername(String username);
    /**
     * 用户登录
     *
     * @param loginData
     * @return 生成的JWT的token
     */
    String executeLogin(LoginData loginData);
}
