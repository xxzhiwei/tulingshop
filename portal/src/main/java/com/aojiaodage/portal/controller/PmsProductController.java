package com.aojiaodage.portal.controller;

import com.aojiaodage.common.util.R;
import com.aojiaodage.portal.service.PmsProductService;
import com.aojiaodage.portal.vo.ProductDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class PmsProductController {

    @Autowired
    PmsProductService pmsProductService;

    @GetMapping("/detail/{id}")
    public R<?> getDetail(@PathVariable Integer id) {
        ProductDetail detail = pmsProductService.getDetail(id);
        return R.ok(detail);
    }
}
