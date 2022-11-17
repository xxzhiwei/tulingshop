package com.aojiaodage.admin.service;

import com.aojiaodage.admin.dto.OrderQuery;
import com.aojiaodage.admin.entity.OmsOrder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OmsOrderService extends IService<OmsOrder> {

    Page<OmsOrder> getPagination(OrderQuery query);
    OmsOrder getDetail(Integer id);
}
