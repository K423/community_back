package com.lzh.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzh.community.model.dto.CommentData;
import com.lzh.community.model.entity.Comment;
import com.lzh.community.model.entity.User;
import com.lzh.community.model.vo.CommentVo;

import java.util.List;

public interface CommentService extends IService<Comment> {
    /**
     *
     *
     * @param topicid
     * @return {@link Comment}
     */
    List<CommentVo> getCommentsByTopicID(String topicid);

    /**
     * create
     */
    Comment create(CommentData commentData, User user);
}
