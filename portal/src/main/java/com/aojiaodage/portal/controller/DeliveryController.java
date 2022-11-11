package com.aojiaodage.portal.controller;

import com.aojiaodage.common.util.R;
import com.aojiaodage.common.validator.interfaces.Del;
import com.aojiaodage.common.validator.interfaces.Save;
import com.aojiaodage.common.validator.interfaces.Update;
import com.aojiaodage.common.validator.interfaces.UpdateField;
import com.aojiaodage.portal.dto.DeliveryForm;
import com.aojiaodage.portal.entity.UmsMemberReceiveAddress;
import com.aojiaodage.portal.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @GetMapping("/list")
    public R<?> getList() {
        List<UmsMemberReceiveAddress> list = deliveryService.getList();
        return R.ok(list);
    }

    @PostMapping("/save")
    public R<?> save(@Validated(value = Save.class) @RequestBody DeliveryForm form) {
        deliveryService.save(form);
        return R.ok();
    }

    @PostMapping("/del")
    public R<?> del(@Validated(value = Del.class) @RequestBody DeliveryForm form) {
        deliveryService.del(form);
        return R.ok();
    }

    @PostMapping("/update")
    public R<?> update(@Validated(value = Update.class) @RequestBody DeliveryForm form) {
        deliveryService.update(form);
        return R.ok();
    }

    @PostMapping("/setDefault")
    public R<?> setDefault(@Validated(value = UpdateField.class) @RequestBody DeliveryForm form) {
        deliveryService.setDefault(form);
        return R.ok();
    }
}
