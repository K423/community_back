package com.lzh.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzh.community.model.entity.Tip;
import org.springframework.stereotype.Repository;

@Repository
public interface TipMapper extends BaseMapper<Tip> {
    Tip getRandomTip();
}
