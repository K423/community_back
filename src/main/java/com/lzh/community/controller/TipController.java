package com.lzh.community.controller;

import com.lzh.community.common.api.ApiResult;
import com.lzh.community.model.entity.Tip;
import com.lzh.community.service.TipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tip")
public class TipController extends BaseController{

    @Autowired
    private TipService tipService;

    @GetMapping("/today")
    public ApiResult<Tip> getRandomTip(){
        Tip tip = tipService.getRandomTip();
        return ApiResult.success(tip);
    }

}
