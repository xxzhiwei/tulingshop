package com.aojiaodage.admin.entity;

import com.aojiaodage.common.interfaces.Children;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 产品分类(PmsProductCategory)实体类
 *
 * @author makejava
 * @since 2022-10-18 11:04:15
 */
@Getter
@Setter
public class PmsProductCategory implements Serializable, Children<PmsProductCategory> {
    private static final long serialVersionUID = -59625163069909894L;

    @TableId
    private Integer id;
    /**
     * 上机分类的编号：0表示一级分类
     */
    private Integer parentId;
    
    private String name;
    /**
     * 分类级别：0->1级；1->2级
     */
    private Integer level;
    
    private Integer productCount;
    
    private String productUnit;
    /**
     * 是否显示在导航栏：0->不显示；1->显示
     */
    private Integer navStatus;
    /**
     * 显示状态：0->不显示；1->显示
     */
    private Integer showStatus;
    
    private Integer sort;
    /**
     * 图标
     */
    private String icon;
    
    private String keywords;
    /**
     * 描述
     */
    private String description;

    @TableField(exist = false)
    private List<PmsProductCategory> children;
}

