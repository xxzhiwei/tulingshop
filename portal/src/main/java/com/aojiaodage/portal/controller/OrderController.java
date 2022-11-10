package com.aojiaodage.portal.controller;

import com.aojiaodage.common.util.R;
import com.aojiaodage.portal.dto.ConfirmForm;
import com.aojiaodage.portal.service.OmsOrderService;
import com.aojiaodage.portal.vo.OrderConfirmation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OmsOrderService omsOrderService;

    @PostMapping("/confirm")
    public R<?> confirm(@Valid @RequestBody ConfirmForm form) {
        OrderConfirmation confirmation = omsOrderService.confirm(form);
        return R.ok(confirmation);
    }

    @PostMapping("/create")
    public R<?> create() {
        return R.ok();
    }
}
