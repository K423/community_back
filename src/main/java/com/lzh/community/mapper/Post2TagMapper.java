package com.lzh.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzh.community.model.entity.Post2Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface Post2TagMapper extends BaseMapper<Post2Tag> {
    /**
     * 根据标签获取话题ID集合
     *
     * @param id
     * @return
     */
    Set<String> getTopicIdsByTagId(@Param("id") String id);
}
