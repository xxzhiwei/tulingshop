package com.aojiaodage.admin.controller;

import com.aojiaodage.admin.dto.PmsProductForm;
import com.aojiaodage.admin.dto.PmsProductQuery;
import com.aojiaodage.admin.dto.PmsProductStatusForm;
import com.aojiaodage.admin.entity.PmsProduct;
import com.aojiaodage.admin.service.PmsProductService;
import com.aojiaodage.admin.vo.ProductDetail;
import com.aojiaodage.common.validator.interfaces.Del;
import com.aojiaodage.common.validator.interfaces.Save;
import com.aojiaodage.common.validator.interfaces.Update;
import com.aojiaodage.common.validator.interfaces.UpdateField;
import com.aojiaodage.common.util.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/product")
@RestController
public class PmsProductController {

    @Autowired
    PmsProductService pmsProductService;

    @GetMapping("/pagination")
    public R<?> getPagination(PmsProductQuery query) {
        Page<PmsProduct> pagination = pmsProductService.getPagination(query);

        return R.ok(pagination);
    }

    @GetMapping("/detail/{id}")
    public R<?> getDetail(@PathVariable Integer id) {
        ProductDetail detail = pmsProductService.getDetail(id);
        return R.ok(detail);
    }

    @PostMapping("/save")
    public R<?> save(@Validated(value = Save.class) @RequestBody PmsProductForm form) {
        pmsProductService.save(form);
        return R.ok();
    }

    @PostMapping("/update")
    public R<?> update(@Validated(value = Update.class) @RequestBody PmsProductForm form) {
        pmsProductService.update(form);
        return R.ok();
    }

    @PostMapping("/publish")
    public R<?> publish(@Validated(value = UpdateField.class) @RequestBody PmsProductStatusForm form) {
        pmsProductService.publish(form);
        return R.ok();
    }

    @PostMapping("/remove")
    public R<?> remove(@Validated(value = UpdateField.class) @RequestBody PmsProductStatusForm form) {
        pmsProductService.removeFromShelf(form);
        return R.ok();
    }

    @PostMapping("/del")
    public R<?> del(@Validated(value = Del.class) @RequestBody PmsProductForm form) {
        pmsProductService.del(form);
        return R.ok();
    }
}
