package com.lzh.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzh.community.mapper.CommentMapper;
import com.lzh.community.model.dto.CommentData;
import com.lzh.community.model.entity.Comment;
import com.lzh.community.model.entity.User;
import com.lzh.community.model.vo.CommentVo;
import com.lzh.community.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {
    @Override
    public List<CommentVo> getCommentsByTopicID(String topicid) {

        List<CommentVo> commentVoList = new ArrayList<>();
        try {
            commentVoList = this.baseMapper.getCommentsByTopicID(topicid);
        }catch (Exception e){
            log.info("error commentList");
        }
        return commentVoList;
    }

    @Override
    public Comment create(CommentData commentData, User user) {
        Comment comment = Comment.builder()
                .userId(user.getId())
                .content(commentData.getContent())
                .topicId(commentData.getTopic_id())
                .createTime(new Date())
                .build();
        this.baseMapper.insert(comment);
        return comment;
    }
}
