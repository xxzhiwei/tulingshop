package com.aojiaodage.admin.dto;

import com.aojiaodage.common.dto.Query;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandQuery extends Query {
    private String firstLetter;
}
