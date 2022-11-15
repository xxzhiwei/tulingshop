package com.aojiaodage.portal.controller;

import com.aojiaodage.common.util.R;
import com.aojiaodage.portal.dto.AlipayForm;
import com.aojiaodage.portal.service.impl.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/alipay")
    public R<?> alipay(@Valid @RequestBody AlipayForm form) {
        // 返回一个表单，在服务器渲染后，再去请求支付页面
        String body = paymentService.generateAlipay(form);
        return R.ok(body);
    }

    @PostMapping("/alipay/notify")
    public String alipayNotify(HttpServletRequest request) {
        return paymentService.handleAlipayNotify(request.getParameterMap());
    }
}
