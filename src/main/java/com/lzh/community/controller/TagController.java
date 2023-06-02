package com.lzh.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzh.community.common.api.ApiResult;
import com.lzh.community.model.entity.Post;
import com.lzh.community.model.entity.Tag;
import com.lzh.community.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tag")
public class TagController extends BaseController {

    @Autowired
    private TagService tagService;

    @GetMapping("/{name}")
    public ApiResult<Map<String, Object>> getById(@PathVariable("name") String tagName,
                                                  @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                  @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Map<String, Object> map = new HashMap<>(16);
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getName, tagName);
        Tag one = tagService.getOne(wrapper);
        Assert.notNull(one, "找不到了ε=(´ο｀*)))唉");
        Page<Post> topPosts = tagService.selectTopicsByTagId(new Page<>(page, size), one.getId());

        //标签相关（文章数量排序）
        Page<Tag> topTags = tagService.page(new Page<>(1, 10),
                new LambdaQueryWrapper<Tag>().
                        notIn(Tag::getName, tagName).
                        orderByDesc(Tag::getTopicCount));
        map.put("topics", topPosts);
        map.put("hotTags", topTags);

        return ApiResult.success(map);
    }
}
