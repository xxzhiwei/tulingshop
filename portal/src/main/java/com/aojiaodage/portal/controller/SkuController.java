package com.aojiaodage.portal.controller;

import com.aojiaodage.common.util.R;
import com.aojiaodage.portal.entity.ProductSku;
import com.aojiaodage.portal.service.PmsProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    PmsProductSkuService pmsProductSkuService;

    @GetMapping("/detail/{id}")
    public R<?> getDetail(@PathVariable Integer id) {
        ProductSku detail = pmsProductSkuService.getDetail(id);
        return R.ok(detail);
    }
}
