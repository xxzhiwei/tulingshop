package com.aojiaodage.portal.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaleAttr {
    private Integer id;
    private String name;
    private List<AttrValueWithSkuId> items;
}
