package com.aojiaodage.portal.service;

import com.aojiaodage.portal.dto.ConfirmForm;
import com.aojiaodage.portal.dto.OrderForm;
import com.aojiaodage.portal.dto.OrderQuery;
import com.aojiaodage.portal.entity.OmsOrder;
import com.aojiaodage.portal.vo.OrderConfirmation;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface OmsOrderService extends IService<OmsOrder> {
    // 确认订单信息
    OrderConfirmation confirm(ConfirmForm form);

    // 创建订单
    String create(OrderForm form);

    OmsOrder getByOrderSn(String orderSn);

    List<OmsOrder> cancelByTask();

    Page<OmsOrder> getPagination(OrderQuery query);
}
