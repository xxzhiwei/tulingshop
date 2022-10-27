package com.aojiaodage.admin.controller;

import com.aojiaodage.admin.dto.AttrGroupForm;
import com.aojiaodage.admin.entity.PmsAttrGroup;
import com.aojiaodage.common.dto.Query;
import com.aojiaodage.admin.service.PmsProductAttrGroupService;
import com.aojiaodage.common.validator.interfaces.Del;
import com.aojiaodage.common.validator.interfaces.Save;
import com.aojiaodage.common.validator.interfaces.Update;
import com.aojiaodage.common.util.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/attrGroup")
@RestController
public class PmsProductAttrGroupController {

    @Autowired
    PmsProductAttrGroupService pmsProductAttrGroupService;

    @GetMapping("/pagination")
    public R<?> getPagination(Query query) {
        Page<PmsAttrGroup> pagination = pmsProductAttrGroupService.getPagination(query);
        return R.ok(pagination);
    }

    @GetMapping("/list")
    public R<List<?>> getList() {
        List<?> list = pmsProductAttrGroupService.getList();
        return R.ok(list);
    }

    @GetMapping("/categoryId/{id}/list")
    public R<List<?>> getListByCategoryId(@PathVariable Integer id) {
        List<?> list = pmsProductAttrGroupService.getListByCategoryId(id);
        return R.ok(list);
    }

    @PostMapping("/save")
    public R<?> save(@Validated(value = Save.class) @RequestBody AttrGroupForm form) {
        pmsProductAttrGroupService.save(form);
        return R.ok();
    }

    @PostMapping("/update")
    public R<?> update(@Validated(value = Update.class) @RequestBody AttrGroupForm form) {
        pmsProductAttrGroupService.update(form);
        return R.ok();
    }

    @PostMapping("/del")
    public R<?> del(@Validated(value = Del.class) @RequestBody AttrGroupForm form) {
        pmsProductAttrGroupService.del(form);
        return R.ok();
    }
}
