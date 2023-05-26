package com.lzh.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lzh.community.common.api.ApiResult;
import com.lzh.community.model.entity.BmsBillboard;
import com.lzh.community.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/board")
@Slf4j
public class BoardController extends BaseController{

    @Autowired
    private BoardService boardService;

    @GetMapping("/show")
    public ApiResult<BmsBillboard> getNotice(){
        List<BmsBillboard> list = boardService.list(new
                LambdaQueryWrapper<BmsBillboard>().eq(BmsBillboard::isShow, true));
//        log.info(String.valueOf(list));
//        log.info(String.valueOf(list.size()));
        return ApiResult.success(list.get(list.size() - 1));
    }
}
