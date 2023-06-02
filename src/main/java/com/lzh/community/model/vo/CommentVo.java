package com.lzh.community.model.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CommentVo {
    private String id;

    private String content;

    private String topicId;

    private String userId;

    private String username;

    private Date createTime;

    private String avatar;
}
