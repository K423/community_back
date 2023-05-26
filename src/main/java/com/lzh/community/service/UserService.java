package com.lzh.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzh.community.model.dto.RegisterData;
import com.lzh.community.model.entity.User;

public interface UserService extends IService<User> {

    /**
     * 注册
     */
    User executeRegister(RegisterData registerData);
}
