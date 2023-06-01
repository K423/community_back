package com.lzh.community.controller;

import com.lzh.community.common.api.ApiResult;
import com.lzh.community.model.dto.CommentData;
import com.lzh.community.model.entity.Comment;
import com.lzh.community.model.entity.User;
import com.lzh.community.model.vo.CommentVo;
import com.lzh.community.service.CommentService;
import com.lzh.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import java.util.List;

import static com.lzh.community.jwt.JWT.USER_NAME;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @GetMapping("/get_comments")
    public ApiResult<List<CommentVo>> getCommentsByID(@RequestParam(value = "topicid", defaultValue = "1")
                                                              String topicId) {
        List<CommentVo> voList = commentService.getCommentsByTopicID(topicId);
        return ApiResult.success(voList);
    }

    @PostMapping("/add_comment")
    public ApiResult<Comment> addComment(@RequestHeader(value = USER_NAME) String userName,
                                         @RequestBody CommentData data) {
        User user = userService.getUserByUsername(userName);
        Comment comment = commentService.create(data, user);
        return ApiResult.success(comment);
    }
}
