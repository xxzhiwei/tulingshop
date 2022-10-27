package com.aojiaodage.admin.controller;

import com.aojiaodage.admin.dto.AttrQuery;
import com.aojiaodage.admin.entity.PmsAttr;
import com.aojiaodage.common.util.R;
import com.aojiaodage.admin.service.PmsProductAttrService;
import com.aojiaodage.common.validator.interfaces.Del;
import com.aojiaodage.common.validator.interfaces.Get;
import com.aojiaodage.common.validator.interfaces.Save;
import com.aojiaodage.common.validator.interfaces.Update;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attr")
public class PmsProductAttrController {

    @Autowired
    PmsProductAttrService pmsProductAttrService;

    @GetMapping("/pagination")
    public R<?> getPagination(AttrQuery query) {
        Page<PmsAttr> pagination = pmsProductAttrService.getPagination(query);
        return R.ok(pagination);
    }

    @GetMapping("/list")
    public R<List<?>> getList(@Validated(value = Get.class) AttrQuery query) {
        Integer categoryId = query.getCategoryId();
        List<?> list = pmsProductAttrService.getSaleAttrListByCategoryId(categoryId);
        return R.ok(list);
    }

    @PostMapping("/save")
    public R<?> save(@Validated(value = Save.class) @RequestBody PmsAttr form) {
        pmsProductAttrService.saveAttr(form);
        return R.ok();
    }

    @PostMapping("/update")
    public R<?> update(@Validated(value = Update.class) @RequestBody PmsAttr form) {
        pmsProductAttrService.update(form);
        return R.ok();
    }

    @PostMapping("/del")
    public R<?> del(@Validated(value = Del.class) @RequestBody PmsAttr form) {
        pmsProductAttrService.del(form);
        return R.ok();
    }
}
