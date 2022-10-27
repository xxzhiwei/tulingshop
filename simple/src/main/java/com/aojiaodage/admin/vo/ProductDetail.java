package com.aojiaodage.admin.vo;

import com.aojiaodage.admin.entity.PmsProductSku;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDetail {
    private Integer id;
    private Integer brandId;
    private Integer productCategoryId;
    private String name;
    private String description;
    private Integer publishStatus;
    private Integer newStatus;
    private Integer recommendStatus;
    private Double price;
    private Integer giftGrowth;
    private Integer giftPoint;
    private String subTitle;
    private Integer stock;
    private String unit;
    private Double weight;
    private List<PmsAttrSpuRelationWithGroupId> attrs;
    private List<PmsProductSku> skus;
    private String brandName;
    private String productCategoryName;
}
