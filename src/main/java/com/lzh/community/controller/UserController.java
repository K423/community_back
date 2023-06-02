package com.lzh.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzh.community.common.api.ApiErrorCode;
import com.lzh.community.common.api.ApiResult;
import com.lzh.community.model.dto.LoginData;
import com.lzh.community.model.dto.RegisterData;
import com.lzh.community.model.entity.Post;
import com.lzh.community.model.entity.User;
import com.lzh.community.service.PostService;
import com.lzh.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
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
    @Autowired
    private PostService postService;

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

    @GetMapping("/logout")
    public ApiResult<Object> logout(){
        return ApiResult.success(null, "退出成功");
    }
    @GetMapping("/{username}")
    public ApiResult<Map<String, Object>> getUserByName(@PathVariable("username") String username,
                                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(value = "size", defaultValue = "10") Integer size){
        Map<String, Object> map = new HashMap<>(16);
        User user = userService.getUserByUsername(username);
        Assert.notNull(user, "该用户不存在");
        Page<Post> page = postService.page(new Page<>(pageNo, size),
                new LambdaQueryWrapper<Post>().eq(Post::getUserId, user.getId()));
        map.put("user", user);
        map.put("topics", page);
        return ApiResult.success(map);
    }

    @PostMapping("/update")
    public ApiResult<User> update(@RequestBody User user){
        userService.updateById(user);
        return ApiResult.success();
    }
}
