package com.aojiaodage.admin.service.impl;

import com.aojiaodage.admin.dao.OmsOrderDao;
import com.aojiaodage.admin.dto.OrderQuery;
import com.aojiaodage.admin.entity.OmsOrder;
import com.aojiaodage.admin.entity.OmsOrderItem;
import com.aojiaodage.admin.service.OmsOrderItemService;
import com.aojiaodage.admin.service.OmsOrderService;
import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.common.util.PaginationUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderDao, OmsOrder> implements OmsOrderService {

    @Autowired
    OmsOrderItemService orderItemService;

    @Override
    public Page<OmsOrder> getPagination(OrderQuery query) {

        String keywords = query.getKeywords();
        BigDecimal min = query.getMin();
        BigDecimal max = query.getMax();
        Date start = query.getStart();
        Date end = query.getEnd();
        Integer status = query.getStatus();
        LambdaQueryWrapper<OmsOrder> queryWrapper = new LambdaQueryWrapper<>();
        Page<OmsOrder> page = PaginationUtil.getPage(query);

        if (StringUtils.hasText(keywords)) {
            queryWrapper.and(qw -> qw.
                    like(OmsOrder::getOrderSn, keywords)
                    .or()
                    .like(OmsOrder::getMemberUsername, keywords)
            );
        }
        if (min != null) {
            queryWrapper.ge(OmsOrder::getTotalAmount, min);
        }
        if (max != null) {
            queryWrapper.le(OmsOrder::getTotalAmount, max);
        }
        if (start != null) {
            queryWrapper.ge(OmsOrder::getCreateTime, start);
        }
        if (end != null) {
            queryWrapper.ge(OmsOrder::getCreateTime, end);
        }
        if (status != null) {
            queryWrapper.eq(OmsOrder::getStatus, status);
        }

        return page(page, queryWrapper);
    }

    @Override
    public OmsOrder getDetail(Integer id) {

        OmsOrder order = getById(id);

        if (order == null) {
            throw new CustomException("数据不存在，id：" + id);
        }

        LambdaQueryWrapper<OmsOrderItem> orderItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderItemLambdaQueryWrapper.eq(OmsOrderItem::getOrderId, order.getId());
        List<OmsOrderItem> orderItems = orderItemService.list(orderItemLambdaQueryWrapper);
        order.setItems(orderItems);
        return order;
    }
}
