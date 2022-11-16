package com.aojiaodage.portal.dao;

import com.aojiaodage.portal.dto.OrderQuery;
import com.aojiaodage.portal.entity.OmsOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsOrderDao extends BaseMapper<OmsOrder> {
    List<OmsOrder> selectOutOfDate();
    List<OmsOrder> selectPagination(Long offset, Long size, Integer memberId, OrderQuery query);
    int selectCount(@Param("memberId")Integer memberId, @Param("query") OrderQuery query);
}
