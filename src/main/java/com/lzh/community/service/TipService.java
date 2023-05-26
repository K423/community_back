package com.lzh.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzh.community.model.entity.Tip;

public interface TipService extends IService<Tip> {
    Tip getRandomTip();
}
