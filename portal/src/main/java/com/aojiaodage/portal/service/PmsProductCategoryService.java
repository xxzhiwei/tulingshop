package com.aojiaodage.portal.service;

import com.aojiaodage.portal.entity.PmsProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PmsProductCategoryService extends IService<PmsProductCategory> {

    List<PmsProductCategory> getList();
}
