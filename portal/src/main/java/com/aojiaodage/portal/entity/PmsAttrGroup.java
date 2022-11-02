package com.aojiaodage.portal.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * (PmsAttrGroup)实体类
 *
 * @author makejava
 * @since 2022-10-20 10:16:13
 */
@Getter
@Setter
@TableName(value = "pms_product_attr_group")
public class PmsAttrGroup implements Serializable {
    private static final long serialVersionUID = 550638927467496578L;
    
    private Integer id;
    
    private String name;
}

