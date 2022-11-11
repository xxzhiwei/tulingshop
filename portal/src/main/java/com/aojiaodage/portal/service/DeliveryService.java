package com.aojiaodage.portal.service;

import com.aojiaodage.portal.dto.DeliveryForm;
import com.aojiaodage.portal.entity.UmsMemberReceiveAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DeliveryService extends IService<UmsMemberReceiveAddress> {
    // 获取会员的收货地址列表
    List<UmsMemberReceiveAddress> getList();
    void save(DeliveryForm form);
    void del(DeliveryForm form);
    void update(DeliveryForm form);
    void setDefault(DeliveryForm form);
}
