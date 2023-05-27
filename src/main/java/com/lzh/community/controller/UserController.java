package com.lzh.community.controller;

import com.lzh.community.common.api.ApiResult;
import com.lzh.community.model.dto.LoginData;
import com.lzh.community.model.dto.RegisterData;
import com.lzh.community.model.entity.User;
import com.lzh.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.lzh.community.jwt.JWT.USER_NAME;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResult<Map<String, Object>> register(@Valid @RequestBody RegisterData registerData){

        User user = userService.executeRegister(registerData);
        if (ObjectUtils.isEmpty(user)){
            return ApiResult.failed("注册失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", user);
        return ApiResult.success(map);

    }

    @PostMapping("/login")
    public ApiResult<Map<String, String>> login(@Valid @RequestBody LoginData loginData){

        String token = userService.executeLogin(loginData);
        if (ObjectUtils.isEmpty(token)){
            return ApiResult.failed("账号密码错误");
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("token", token);
        return ApiResult.success(map, "登陆成功");

    }

    @GetMapping("/info")
    public ApiResult<User> getUser(@RequestHeader(value = USER_NAME) String userName){
        User user = userService.getUserByUsername(userName);
        return ApiResult.success(user);
    }
}
