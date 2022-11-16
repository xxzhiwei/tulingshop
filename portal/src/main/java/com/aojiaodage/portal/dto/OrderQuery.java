package com.aojiaodage.portal.dto;

import com.aojiaodage.common.dto.Query;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class OrderQuery extends Query {
    private BigDecimal min;
    private BigDecimal max;
    private Date start;
    private Date end;
    private Integer status;
}
