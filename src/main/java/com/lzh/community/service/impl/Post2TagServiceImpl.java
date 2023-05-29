package com.lzh.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzh.community.mapper.Post2TagMapper;
import com.lzh.community.model.entity.Post2Tag;
import com.lzh.community.service.Post2TagService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Post2TagServiceImpl extends ServiceImpl<Post2TagMapper, Post2Tag>
        implements Post2TagService {
    @Override
    public List<Post2Tag> selectByTopicId(String topicId) {

        QueryWrapper<Post2Tag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Post2Tag::getTopicId, topicId);

        return this.baseMapper.selectList(wrapper);
    }
}
