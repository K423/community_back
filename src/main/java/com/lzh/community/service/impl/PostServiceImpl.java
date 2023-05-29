package com.lzh.community.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzh.community.mapper.PostMapper;
import com.lzh.community.mapper.TagMapper;
import com.lzh.community.model.entity.Post;
import com.lzh.community.model.entity.Post2Tag;
import com.lzh.community.model.entity.Tag;
import com.lzh.community.model.vo.PostVO;
import com.lzh.community.service.Post2TagService;
import com.lzh.community.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
        implements PostService {

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private Post2TagService post2TagService;

    @Override
    public Page<PostVO> getList(Page<PostVO> page, String tab) {
        // 查询话题
        Page<PostVO> iPage = this.baseMapper.selectListAndPage(page, tab);
        // 查询话题的标签
        iPage.getRecords().forEach(topic -> {
            List<Post2Tag> topicTags = post2TagService.selectByTopicId(topic.getId());
            if (!topicTags.isEmpty()) {
                List<String> tagIds = topicTags.stream().map(Post2Tag::getTagId).collect(Collectors.toList());
                List<Tag> tags = tagMapper.selectBatchIds(tagIds);
                topic.setTags(tags);
            }
        });
        return iPage;
    }
}
