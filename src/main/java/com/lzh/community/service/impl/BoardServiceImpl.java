package com.lzh.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzh.community.mapper.BoardMapper;
import com.lzh.community.model.entity.BmsBillboard;
import com.lzh.community.service.BoardService;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl extends ServiceImpl<BoardMapper, BmsBillboard>
        implements BoardService {
}
