package com.lzh.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzh.community.model.entity.Post;
import com.lzh.community.model.vo.PostVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostMapper extends BaseMapper<Post> {
    /**
     * 分页查询首页话题列表
     * <p>
     *
     * @param page
     * @param tab
     * @return
     */
    Page<PostVO> selectListAndPage(@Param("page") Page<PostVO> page,
                                   @Param("tab") String tab);
}