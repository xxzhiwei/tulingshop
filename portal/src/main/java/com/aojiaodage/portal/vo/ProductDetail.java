package com.aojiaodage.portal.vo;

import com.aojiaodage.portal.entity.PmsProduct;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDetail extends PmsProduct {
    private List<AttrGroup> attrGroups;
    private List<SaleAttr> saleAttrs;
}
