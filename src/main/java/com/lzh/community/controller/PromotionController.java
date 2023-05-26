package com.lzh.community.controller;

import com.lzh.community.common.api.ApiResult;
import com.lzh.community.model.entity.Promotion;
import com.lzh.community.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/promotion")
public class PromotionController extends BaseController{

    @Autowired
    private PromotionService promotionService;

    @GetMapping("/all")
    public ApiResult<List<Promotion>> listApiResult(){
        List<Promotion> list = promotionService.list();
        return ApiResult.success(list);
    }
}
