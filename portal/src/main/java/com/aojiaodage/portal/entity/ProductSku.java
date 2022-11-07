package com.aojiaodage.portal.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * (ProductSku)实体类
 *
 * @author makejava
 * @since 2022-11-02 15:51:32
 */
@Getter
@Setter
@TableName("pms_product_sku")
public class ProductSku implements Serializable {
    private static final long serialVersionUID = -32496029806083449L;
    
    private Integer id;
    
    private Integer spuId;
    
    private String name;
    
    private BigDecimal price;
    
    private Integer sales;
    
    private Long stock;
    
    private Long stockLocked;


}

