package com.lzh.community.controller;

import com.lzh.community.common.api.ApiResult;
import com.lzh.community.model.dto.RegisterData;
import com.lzh.community.model.entity.User;
import com.lzh.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
}
