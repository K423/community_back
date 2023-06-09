package com.lzh.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lzh.community.mapper.TagMapper;

import com.lzh.community.model.entity.Post;
import com.lzh.community.model.entity.Tag;
import com.lzh.community.service.Post2TagService;
import com.lzh.community.service.PostService;
import com.lzh.community.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
        implements TagService {

    @Autowired
    private Post2TagService post2TagService;
    @Autowired
    private PostService postService;

    @Override
    public List<Tag> insertTags(List<String> tags) {
        List<Tag> list = new ArrayList<>();
        for (String tagName : tags){
            Tag tag = this.baseMapper.selectOne(new LambdaQueryWrapper<Tag>().eq(Tag::getName, tagName));
            if (tag == null){
                tag = Tag.builder().name(tagName).build();
                this.baseMapper.insert(tag);
            }else {
                tag.setTopicCount(tag.getTopicCount() + 1);
                this.baseMapper.updateById(tag);
            }
            list.add(tag);
        }
        return list;
    }

    @Override
    public Page<Post> selectTopicsByTagId(Page<Post> topicPage, String id) {

        // 获取关联的话题ID
        Set<String> set = post2TagService.selectTopicIdsByTagId(id);
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Post::getId, set);
        return postService.page(topicPage, wrapper);
    }
}
