package com.aojiaodage.admin.service;

import com.aojiaodage.admin.dto.AttrQuery;
import com.aojiaodage.admin.entity.PmsAttr;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PmsProductAttrService extends IService<PmsAttr> {

    List<PmsAttr> getSaleAttrListByCategoryId(Integer id);

    void update(PmsAttr form);

    void saveAttr(PmsAttr form);

    void del(PmsAttr form);

    Page<PmsAttr> getPagination(AttrQuery query);
}
