package com.aojiaodage.admin.controller;

import com.aojiaodage.admin.dto.OrderQuery;
import com.aojiaodage.admin.entity.OmsOrder;
import com.aojiaodage.admin.service.OmsOrderService;
import com.aojiaodage.common.util.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OmsOrderController {

    @Autowired
    OmsOrderService orderService;

    @GetMapping("/pagination")
    public R<?> getPagination(OrderQuery query) {
        Page<OmsOrder> pagination = orderService.getPagination(query);
        return R.ok(pagination);
    }

    @GetMapping("/detail/{id}")
    public R<?> getDetail(@PathVariable Integer id) {
        OmsOrder detail = orderService.getDetail(id);
        return R.ok(detail);
    }
}
