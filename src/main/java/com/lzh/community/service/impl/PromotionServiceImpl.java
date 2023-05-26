package com.lzh.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzh.community.mapper.PromotionMapper;
import com.lzh.community.model.entity.Promotion;
import com.lzh.community.service.PromotionService;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl extends ServiceImpl<PromotionMapper, Promotion>
        implements PromotionService {
}
