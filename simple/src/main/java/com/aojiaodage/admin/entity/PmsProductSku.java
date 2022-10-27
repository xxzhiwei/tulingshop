package com.aojiaodage.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * (PmsProductSku)实体类
 *
 * @author makejava
 * @since 2022-10-21 14:27:26
 */
@Getter
@Setter
public class PmsProductSku implements Serializable {
    private static final long serialVersionUID = -99700519055430546L;

    private Integer id;
    
    private Integer spuId;
    
    private String name;
    
    private Double price;
    
    private Integer sales;

    private BigInteger stock;
    private BigInteger stockLocked;

    @TableField(exist = false)
    private List<PmsAttrSpuSkuRelation> attrs;
}

