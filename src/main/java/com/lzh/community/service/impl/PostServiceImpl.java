package com.lzh.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzh.community.mapper.PostMapper;
import com.lzh.community.mapper.TagMapper;
import com.lzh.community.mapper.UserMapper;
import com.lzh.community.model.dto.CreatePostData;
import com.lzh.community.model.entity.Post;
import com.lzh.community.model.entity.Post2Tag;
import com.lzh.community.model.entity.Tag;
import com.lzh.community.model.entity.User;
import com.lzh.community.model.vo.DetailVo;
import com.lzh.community.model.vo.PostVO;
import com.lzh.community.service.Post2TagService;
import com.lzh.community.service.PostService;
import com.lzh.community.service.TagService;
import com.lzh.community.service.UserService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post>
        implements PostService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private Post2TagService post2TagService;
    @Autowired
    @Lazy
    private TagService tagService;

    @Autowired
    private UserService userService;

    private void p2t(Page<PostVO> iPage){
        iPage.getRecords().forEach(topic -> {
            List<Post2Tag> topicTags = post2TagService.selectByTopicId(topic.getId());
            if (!topicTags.isEmpty()) {
                List<String> tagIds = topicTags.stream().map(Post2Tag::getTagId).collect(Collectors.toList());
                List<Tag> tags = tagMapper.selectBatchIds(tagIds);
                topic.setTags(tags);
            }
        });
    }

    @Override
    public Page<PostVO> getList(Page<PostVO> page, String tab) {
        // 查询话题
        Page<PostVO> iPage = this.baseMapper.selectListAndPage(page, tab);
        // 查询话题的标签
        p2t(iPage);
        return iPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Post create(CreatePostData dto, User principal) {

        Post post = this.baseMapper.selectOne(new LambdaQueryWrapper<Post>().eq(Post::getTitle, dto.getTitle()));
        Assert.isNull(post, "主题已存在，请更改！");

        //封装
        Post p = Post.builder()
                .userId(principal.getId())
                .title(dto.getTitle())
                .content(EmojiParser.parseToAliases(dto.getContent()))
                .createTime(new Date())
                .build();
        this.baseMapper.insert(p);

        //用户积分
        int Score = principal.getScore() + 1;
        userMapper.updateById(principal.setScore(Score));

        //标签
        if (!ObjectUtils.isEmpty(dto.getTags())){
            List<Tag> tagList = tagService.insertTags(dto.getTags());
            //标签与主题关联
            post2TagService.createTopicTag(p.getId(), tagList);
        }

        return p;
    }

    @Override
    public Map<String, Object> viewTopic(String id) {

        Map<String, Object> map = new HashMap<>(16);
        Post post = this.baseMapper.selectById(id);
        Assert.notNull(post, "当前内容不存在或已被作者删除");
        //查询详情
        post.setView(post.getView() + 1);
        this.baseMapper.updateById(post);
        //emoji转码
        post.setContent(EmojiParser.parseToUnicode(post.getContent()));
        map.put("topic", post);
        //标签
        QueryWrapper<Post2Tag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Post2Tag::getTopicId, post.getId());
        Set<String> set = new HashSet<>();
        for (Post2Tag post2Tag : post2TagService.list(wrapper)){
            set.add(post2Tag.getTagId());
        }
        List<Tag> tags = tagService.listByIds(set);
        map.put("tags", tags);

        //作者
        DetailVo user = userService.getUser(post.getUserId());
        map.put("user", user);

        return map;
    }

    @Override
    public List<Post> getRecommend(String id) {
        return this.baseMapper.selectRecommend(id);
    }

    @Override
    public Page<PostVO> searchByKey(String keyword, Page<PostVO> page) {
        //查询
        Page<PostVO> ipage = this.baseMapper.searchByKey(page, keyword);
        //相关标签
        p2t(ipage);
        return ipage;
    }
}
