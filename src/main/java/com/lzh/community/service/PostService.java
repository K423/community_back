package com.lzh.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzh.community.model.dto.CreatePostData;
import com.lzh.community.model.entity.Post;
import com.lzh.community.model.entity.User;
import com.lzh.community.model.vo.PostVO;

import java.util.List;
import java.util.Map;

public interface PostService extends IService<Post> {
    /**
     * 获取首页话题列表
     *
     * @param page
     * @param tab
     * @return
     */
    Page<PostVO> getList(Page<PostVO> page, String tab);

    /**
     * 发布
     *
     * @param dto
     * @param principal
     * @return
     */
    Post create(CreatePostData dto, User principal);

    /**
     * 查看话题详情
     *
     * @param id
     * @return
     */
    Map<String, Object> viewTopic(String id);

    /**
     * 获取随机推荐10篇
     *
     * @param id
     * @return
     */
    List<Post> getRecommend(String id);
}
