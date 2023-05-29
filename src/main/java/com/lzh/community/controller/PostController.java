package com.lzh.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzh.community.common.api.ApiResult;
import com.lzh.community.model.vo.PostVO;
import com.lzh.community.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController extends BaseController{

    @Autowired
    private PostService postService;

    @PostMapping("/post/list")
    public ApiResult<Page<PostVO>> list(@RequestParam(value = "tab", defaultValue = "latest") String tab,
                                        @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize){
        Page<PostVO> postVOPage = postService.getList(new Page<>(pageNo, pageSize), tab);
        return ApiResult.success(postVOPage);
    }
}
