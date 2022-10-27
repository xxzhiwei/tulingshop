package com.aojiaodage.admin.controller;

import com.aojiaodage.admin.dto.CategoryForm;
import com.aojiaodage.admin.entity.PmsProductCategory;
import com.aojiaodage.admin.service.PmsProductCategoryService;
import com.aojiaodage.common.validator.interfaces.Del;
import com.aojiaodage.common.validator.interfaces.Save;
import com.aojiaodage.common.validator.interfaces.Update;
import com.aojiaodage.common.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
public class PmsProductCategoryController {

    @Autowired
    PmsProductCategoryService pmsProductCategoryService;

    @GetMapping("/list")
    public R<List<?>> getList() {
        List<PmsProductCategory> list = pmsProductCategoryService.getList();
        return R.ok(list);
    }

    @PostMapping("/update")
    public R<?> update(@Validated(value = Update.class) @RequestBody CategoryForm form) {
        pmsProductCategoryService.update(form);
        return R.ok();
    }

    @PostMapping("/del")
    public R<?> del(@Validated(value = Del.class) @RequestBody CategoryForm form) {
        pmsProductCategoryService.del(form);
        return R.ok();
    }

    @PostMapping("/save")
    public R<?> save(@Validated(value = Save.class) @RequestBody CategoryForm form) {
        pmsProductCategoryService.save(form);
        return R.ok();
    }
}
