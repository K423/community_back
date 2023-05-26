package com.lzh.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzh.community.mapper.TipMapper;
import com.lzh.community.model.entity.Tip;
import com.lzh.community.service.TipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TipServiceImpl extends ServiceImpl<TipMapper, Tip>
        implements TipService {
    @Override
    public Tip getRandomTip() {
        Tip tip = null;
        try {
            tip = this.baseMapper.getRandomTip();
        } catch (Exception e){
            log.info("error");
        }
        return tip;
    }
}
