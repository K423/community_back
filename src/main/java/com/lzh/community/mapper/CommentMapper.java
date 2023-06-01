package com.lzh.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzh.community.model.entity.Comment;
import com.lzh.community.model.vo.CommentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * getCommentsByTopicID
     *
     * @param topicid
     * @return
     */
    List<CommentVo> getCommentsByTopicID(@Param("topicid") String topicid);
}
