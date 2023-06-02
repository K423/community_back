package com.lzh.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzh.community.mapper.Post2TagMapper;
import com.lzh.community.model.entity.Post2Tag;
import com.lzh.community.model.entity.Tag;
import com.lzh.community.service.Post2TagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class Post2TagServiceImpl extends ServiceImpl<Post2TagMapper, Post2Tag>
        implements Post2TagService {
    @Override
    public List<Post2Tag> selectByTopicId(String topicId) {

        QueryWrapper<Post2Tag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Post2Tag::getTopicId, topicId);

        return this.baseMapper.selectList(wrapper);
    }

    @Override
    public void createTopicTag(String id, List<Tag> tags) {
        this.baseMapper.delete(new LambdaQueryWrapper<Post2Tag>().eq(Post2Tag::getTopicId, id));
        tags.forEach(tag -> {
            Post2Tag post2Tag = new Post2Tag();
            post2Tag.setTopicId(id);
            post2Tag.setTagId(tag.getId());
            this.baseMapper.insert(post2Tag);
        });
    }

    @Override
    public Set<String> selectTopicIdsByTagId(String id) {
        return this.baseMapper.getTopicIdsByTagId(id);
    }
}
