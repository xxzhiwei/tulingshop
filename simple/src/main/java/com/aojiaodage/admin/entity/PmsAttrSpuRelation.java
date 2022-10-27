package com.aojiaodage.admin.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * attr&spu关联表(PmsAttrSpuRelation)实体类
 *
 * @author makejava
 * @since 2022-10-21 14:38:29
 */
@Getter
@Setter
public class PmsAttrSpuRelation implements Serializable {
    private static final long serialVersionUID = -73385933868607250L;
    
    private Integer id;
    
    private Integer spuId;
    
    private String value;
    
    private Integer attrId;
}

