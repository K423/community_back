package com.lzh.community.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzh.community.common.api.ApiResult;
import com.lzh.community.model.dto.CreatePostData;
import com.lzh.community.model.entity.Post;
import com.lzh.community.model.entity.User;
import com.lzh.community.model.vo.PostVO;
import com.lzh.community.service.PostService;
import com.lzh.community.service.UserService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.lzh.community.jwt.JWT.USER_NAME;

@RestController
public class PostController extends BaseController{

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping("/post/list")
    public ApiResult<Page<PostVO>> list(@RequestParam(value = "tab", defaultValue = "latest") String tab,
                                        @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize){
        Page<PostVO> postVOPage = postService.getList(new Page<>(pageNo, pageSize), tab);
        return ApiResult.success(postVOPage);
    }

    @PostMapping("/post/create")
    public ApiResult<Post> create(@RequestHeader(value = USER_NAME) String userName,
                                  @RequestBody CreatePostData data){
        User user = userService.getUserByUsername(userName);
        Post post = postService.create(data, user);
        return ApiResult.success(post);
    }

    @GetMapping("/post")
    public ApiResult<Map<String, Object>> view(@RequestParam("id") String id){
        Map<String, Object> map = postService.viewTopic(id);
        return ApiResult.success(map);
    }

    @GetMapping("/post/recommend")
    public ApiResult<List<Post>> getRecommend(@RequestParam("topicId") String id){
        List<Post> posts = postService.getRecommend(id);
        return ApiResult.success(posts);
    }

    @PostMapping("/post/update")
    public ApiResult<Post> update(@RequestHeader(value = USER_NAME) String userName,
                                  @Valid @RequestBody Post post){
        User user = userService.getUserByUsername(userName);
        Assert.isTrue(user.getId().equals(post.getUserId()), "非本人操作");
        post.setModifyTime(new Date());
        post.setContent(EmojiParser.parseToAliases(post.getContent()));
        postService.updateById(post);
        return ApiResult.success(post);
    }

    @DeleteMapping("/post/delete/{id}")
    public ApiResult<String> delete(@RequestHeader(value = USER_NAME) String userName,
                                    @PathVariable("id") String id) {
        User user = userService.getUserByUsername(userName);
        Post byId = postService.getById(id);
        Assert.notNull(byId, "来晚一步，话题已不存在");
        Assert.isTrue(byId.getUserId().equals(user.getId()), "你为什么可以删除别人的话题？？？");
        postService.removeById(id);
        return ApiResult.success(null,"删除成功");
    }

}
