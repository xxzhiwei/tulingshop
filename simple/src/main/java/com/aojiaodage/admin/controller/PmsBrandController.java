package com.aojiaodage.admin.controller;

import com.aojiaodage.admin.dto.BrandForm;
import com.aojiaodage.admin.dto.BrandQuery;
import com.aojiaodage.admin.entity.PmsBrand;
import com.aojiaodage.common.util.R;
import com.aojiaodage.admin.service.PmsBrandService;
import com.aojiaodage.common.validator.interfaces.Del;
import com.aojiaodage.common.validator.interfaces.Save;
import com.aojiaodage.common.validator.interfaces.Update;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/brand")
@RestController
public class PmsBrandController {

    @Autowired
    PmsBrandService pmsBrandService;

    @GetMapping("/pagination")
    public R<?> getPagination(BrandQuery query) {
        Page<PmsBrand> pagination = pmsBrandService.getPagination(query);
        return R.ok(pagination);
    }

    @GetMapping("/list")
    public R<List<PmsBrand>> getList() {
        List<PmsBrand> list = pmsBrandService.getList();
        return R.ok(list);
    }

    @PostMapping("/save")
    public R<?> save(@Validated(value = Save.class) @RequestBody BrandForm form) {
        pmsBrandService.save(form);
        return R.ok();
    }

    @PostMapping("/update")
    public R<?> update(@Validated(value = Update.class) @RequestBody BrandForm form) {
        pmsBrandService.update(form);
        return R.ok();
    }

    @PostMapping("/del")
    public R<?> del(@Validated(value = Del.class) @RequestBody BrandForm form) {
        pmsBrandService.del(form);
        return R.ok();
    }
}
