package com.aojiaodage.portal.dao;

import com.aojiaodage.portal.entity.OmsOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface OmsOrderDao extends BaseMapper<OmsOrder> {
    List<OmsOrder> selectOutOfDate();
    List<OmsOrder> selectPagination(Long offset, Long size, Integer memberId);
}
