package com.aojiaodage.portal.dao;

import com.aojiaodage.portal.entity.OmsOrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface OmsOrderItemDao  extends BaseMapper<OmsOrderItem> {
    List<OmsOrderItem> selectByOrderId(Integer id);
}
