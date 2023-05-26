package com.lzh.community.model.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("bms_tip")
public class Tip implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * content
     */
    @TableField("content")
    private String content;

    /**
     * author
     */
    @TableField("author")
    private String author;

    /**
     * display
     */
    @TableField("`type`")
    private boolean type;
}
