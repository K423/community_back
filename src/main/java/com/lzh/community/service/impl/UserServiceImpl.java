package com.lzh.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzh.community.jwt.JWT;
import com.lzh.community.model.dto.LoginData;
import com.lzh.community.utils.MD5Utils;
import com.lzh.community.common.exception.ApiAsserts;
import com.lzh.community.mapper.UserMapper;
import com.lzh.community.model.dto.RegisterData;
import com.lzh.community.model.entity.User;
import com.lzh.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public User executeRegister(RegisterData registerData) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, registerData.getName())
                .or()
                .eq(User::getEmail, registerData.getEmail());
        User user = baseMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(user)) {
            ApiAsserts.fail("账号或邮箱已存在");
        }
        User addUser = User.builder()
                .username(registerData.getName())
                .alias(registerData.getName())
                .password(registerData.getPass())  //.password(MD5Utils.getPwd(dto.getPass())) 密码加密存储
                .email(registerData.getEmail())
                .createTime(new Date())
                .status(true)
                        .build();
        baseMapper.insert(addUser);
        return addUser;
    }

    @Override
    public User getUserByUsername(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public String executeLogin(LoginData loginData) {

        String token = null;
        try {
            User user = getUserByUsername(loginData.getUsername());
//            密码加密存储后可运行
//            String encodePwd = MD5Utils.getPwd(loginData.getPassword());
//            if(!encodePwd.equals(user.getPassword()))
//            {
//                throw new Exception("密码错误");
//            }
            token = JWT.generateToken(String.valueOf(user.getUsername()));
        }catch (Exception e){
            log.warn("用户不存在or验证失败 ====>{}", loginData.getUsername());
        }
        return token;
    }
}
