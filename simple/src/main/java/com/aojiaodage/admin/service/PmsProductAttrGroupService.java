package com.aojiaodage.admin.service;

import com.aojiaodage.admin.dto.AttrGroupForm;
import com.aojiaodage.admin.entity.PmsAttrGroup;
import com.aojiaodage.admin.vo.ProductAttrGroupWithAttr;
import com.aojiaodage.common.dto.Query;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PmsProductAttrGroupService extends IService<PmsAttrGroup> {
    // 目前先规定：规格属性必须与分组绑定在一起
    List<ProductAttrGroupWithAttr> getListByCategoryId(Integer id);

    void del(AttrGroupForm form);

    void update(AttrGroupForm form);

    void save(AttrGroupForm form);

    Page<PmsAttrGroup> getPagination(Query query);

    List<?> getList();
}
