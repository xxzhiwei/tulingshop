package com.aojiaodage.portal.controller;

import com.aojiaodage.common.util.R;
import com.aojiaodage.portal.dto.ConfirmForm;
import com.aojiaodage.portal.dto.OrderForm;
import com.aojiaodage.portal.entity.OmsOrder;
import com.aojiaodage.portal.service.OmsOrderService;
import com.aojiaodage.portal.vo.OrderConfirmation;
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
        orderService.create(form);
        return R.ok();
    }

    @GetMapping("/detail/{id}")
    public R<?> getDetail(@PathVariable Integer id) {
        OmsOrder detail = orderService.getDetail(id);
        return R.ok(detail);
    }
}
