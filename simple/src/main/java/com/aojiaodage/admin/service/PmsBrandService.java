package com.aojiaodage.admin.service;

import com.aojiaodage.admin.dto.BrandForm;
import com.aojiaodage.admin.dto.BrandQuery;
import com.aojiaodage.admin.entity.PmsBrand;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PmsBrandService extends IService<PmsBrand> {
    Page<PmsBrand> getPagination(BrandQuery query);
    List<PmsBrand> getList();
    void save(BrandForm form);
    void update(BrandForm form);

    void del(BrandForm form);
}
