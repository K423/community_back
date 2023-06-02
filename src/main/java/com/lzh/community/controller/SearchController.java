package com.lzh.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzh.community.common.api.ApiResult;
import com.lzh.community.model.vo.PostVO;
import com.lzh.community.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController extends BaseController{

    @Autowired
    private PostService postService;
    @GetMapping
    public ApiResult<Page<PostVO>> searchList(@RequestParam("keyword") String keyword,
                                              @RequestParam("pageNum") Integer pageNum,
                                              @RequestParam("pageSize") Integer pageSize){
        Page<PostVO> voPage = postService.searchByKey(keyword, new Page<>(pageNum, pageSize));
        return ApiResult.success(voPage);
    }
}
