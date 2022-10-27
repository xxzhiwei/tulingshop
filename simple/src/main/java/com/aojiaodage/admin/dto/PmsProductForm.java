package com.aojiaodage.admin.dto;

import com.aojiaodage.admin.entity.PmsAttrSpuRelation;
import com.aojiaodage.admin.entity.PmsProductSku;
import com.aojiaodage.common.validator.interfaces.Del;
import com.aojiaodage.common.validator.interfaces.Save;
import com.aojiaodage.common.validator.interfaces.Update;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class PmsProductForm {
    @NotNull(message = "id不能为空", groups = {Update.class, Del.class})
    private Integer id;
    @NotNull(message = "brandId不能为空", groups = {Update.class, Save.class})
    private Integer brandId;
    @NotNull(message = "productCategoryId不能为空", groups = {Update.class, Save.class})
    private Integer productCategoryId;
    @NotBlank(message = "name不能为空", groups = {Update.class, Save.class})
    private String name;
    private String description;
    private Integer publishStatus;
    private Integer newStatus;
    private Integer recommendStatus;
    private Double price;
    private Integer giftGrowth;
    private Integer giftPoint;
    private String subTitle;
    private String unit;
    private Double weight;
    @NotEmpty(message = "attrs不能为空", groups = {Update.class, Save.class})
    private List<PmsAttrSpuRelation> attrs;
    @NotEmpty(message = "skus不能为空", groups = {Update.class, Save.class})
    private List<PmsProductSku> skus;
    private List<Integer> removing; // 保存将要删除的skuId
}
