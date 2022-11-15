package com.aojiaodage.portal.controller;

import com.aojiaodage.common.util.R;
import com.aojiaodage.portal.dto.ConfirmForm;
import com.aojiaodage.portal.dto.OrderForm;
import com.aojiaodage.portal.dto.OrderQuery;
import com.aojiaodage.portal.entity.OmsOrder;
import com.aojiaodage.portal.service.OmsOrderService;
import com.aojiaodage.portal.vo.OrderConfirmation;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OmsOrderService orderService;

    @PostMapping("/confirm")
    public R<?> confirm(@Valid @RequestBody ConfirmForm form) {
        OrderConfirmation confirmation = orderService.confirm(form);
        return R.ok(confirmation);
    }

    @PostMapping("/create")
    public R<?> create(@Valid @RequestBody OrderForm form) {
        String orderSn = orderService.create(form);
        return R.ok(orderSn);
    }

    @GetMapping("/pagination")
    public R<?> getPagination(OrderQuery query) {
        Page<OmsOrder> pagination = orderService.getPagination(query);
        return R.ok(pagination);
    }

    @GetMapping("/detail")
    public R<?> getDetail(@RequestParam String orderSn) {
        OmsOrder order = orderService.getByOrderSn(orderSn);
        return R.ok(order);
    }

    @GetMapping("/brief")
    public R<?> getBrief(@RequestParam String orderSn) {
        OmsOrder order = orderService.getByOrderSn(orderSn, false);
        return R.ok(order);
    }
}
