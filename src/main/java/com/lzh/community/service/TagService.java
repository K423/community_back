package com.lzh.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzh.community.model.entity.Tag;

import java.util.List;

public interface TagService extends IService<Tag> {
    /**
     * 插入标签
     *
     * @param tags
     * @return
     */
    List<Tag> insertTags(List<String> tags);
}
