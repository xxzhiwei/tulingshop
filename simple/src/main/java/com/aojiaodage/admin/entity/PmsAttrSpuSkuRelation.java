package com.aojiaodage.admin.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * attr&spu&sku关联表(PmsAttrSpuSkuRelation)实体类
 *
 * @author makejava
 * @since 2022-10-21 14:57:55
 */
@Getter
@Setter
public class PmsAttrSpuSkuRelation implements Serializable {
    private static final long serialVersionUID = 552861436082492689L;
    
    private Integer id;
    
    private Integer spuId;
    
    private Integer skuId;
    
    private String value;
    
    private Integer attrId;
}

