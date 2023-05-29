package com.lzh.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzh.community.model.entity.Post2Tag;
import com.lzh.community.model.entity.Tag;

import java.util.List;

public interface Post2TagService extends IService<Post2Tag> {
    /**
     * 获取Topic Tag 关联记录
     *
     * @param topicId TopicId
     * @return
     */
    List<Post2Tag> selectByTopicId(String topicId);

    /**
     * 创建中间关系
     *
     * @param id
     * @param tags
     * @return
     */
    void createTopicTag(String id, List<Tag> tags);
}
