package com.aojiaodage.portal.service;

import com.aojiaodage.portal.dto.ConfirmForm;
import com.aojiaodage.portal.dto.OrderForm;
import com.aojiaodage.portal.entity.OmsOrder;
import com.aojiaodage.portal.vo.OrderConfirmation;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OmsOrderService extends IService<OmsOrder> {
    // 确认订单信息
    OrderConfirmation confirm(ConfirmForm form);

    // 创建订单
    void create(OrderForm form);
}
