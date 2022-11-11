package com.aojiaodage.portal.service.impl;

import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.portal.dao.DeliveryDao;
import com.aojiaodage.portal.dto.DeliveryForm;
import com.aojiaodage.portal.entity.Member;
import com.aojiaodage.portal.entity.UmsMemberReceiveAddress;
import com.aojiaodage.portal.service.DeliveryService;
import com.aojiaodage.portal.util.PayloadUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeliveryServiceImpl extends ServiceImpl<DeliveryDao, UmsMemberReceiveAddress> implements DeliveryService {
    @Override
    public List<UmsMemberReceiveAddress> getList() {
        Member member = PayloadUtil.get();

        LambdaQueryWrapper<UmsMemberReceiveAddress> deliveryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        deliveryLambdaQueryWrapper.eq(UmsMemberReceiveAddress::getMemberId, member.getId());

        return list(deliveryLambdaQueryWrapper);
    }

    @Override
    public void save(DeliveryForm form) {
        UmsMemberReceiveAddress delivery = new UmsMemberReceiveAddress();

        BeanUtils.copyProperties(form, delivery);
        Member member = PayloadUtil.get();

        delivery.setMemberId(member.getId());
        delivery.setDefaultStatus(0);
        save(delivery);
    }

    @Override
    public void del(DeliveryForm form) {
        Integer id = form.getId();

        UmsMemberReceiveAddress delivery = getById(id);

        if (delivery == null) {
            throw new CustomException("数据不存在，id：" + id);
        }

        removeById(id);
    }

    @Override
    public void update(DeliveryForm form) {
        Integer id = form.getId();

        UmsMemberReceiveAddress delivery = getById(id);

        if (delivery == null) {
            throw new CustomException("数据不存在，id：" + id);
        }

        BeanUtils.copyProperties(form, delivery);

        updateById(delivery);
    }

    @Transactional
    @Override
    public void setDefault(DeliveryForm form) {
        Integer id = form.getId();
        UmsMemberReceiveAddress delivery = getById(id);

        if (delivery == null) {
            throw new CustomException("数据不存在，id：" + id);
        }

        delivery.setDefaultStatus(1);

        LambdaQueryWrapper<UmsMemberReceiveAddress> deliveryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer memberId = PayloadUtil.get().getId();
        deliveryLambdaQueryWrapper.eq(UmsMemberReceiveAddress::getMemberId, memberId);
        deliveryLambdaQueryWrapper.eq(UmsMemberReceiveAddress::getDefaultStatus, 1);
        UmsMemberReceiveAddress defaultDelivery = getOne(deliveryLambdaQueryWrapper);

        if (defaultDelivery != null) {
            defaultDelivery.setDefaultStatus(0);
            updateById(defaultDelivery);
        }

        updateById(delivery);
    }
}
