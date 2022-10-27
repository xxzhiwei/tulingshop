package com.aojiaodage.admin.service;

import com.aojiaodage.admin.dto.CategoryForm;
import com.aojiaodage.admin.entity.PmsProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PmsProductCategoryService extends IService<PmsProductCategory> {

    List<PmsProductCategory> getList();
    void save(CategoryForm form);
    void update(CategoryForm form);
    void del(CategoryForm form);
}
