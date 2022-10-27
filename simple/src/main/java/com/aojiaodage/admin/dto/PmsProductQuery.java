package com.aojiaodage.admin.dto;

import com.aojiaodage.common.dto.Query;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PmsProductQuery extends Query {

    private Integer publishStatus;
    private String productSn;
    private Integer brandId;
    private Integer categoryId;
}
