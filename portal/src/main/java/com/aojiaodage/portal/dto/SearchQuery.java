package com.aojiaodage.portal.dto;

import com.aojiaodage.common.dto.Query;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchQuery extends Query {
    private Integer categoryId;
    private Integer brandId;
    private Integer sort; // 1=默认，2=销量（降序），3=价格降序，4=价格降序，5=新品
}
