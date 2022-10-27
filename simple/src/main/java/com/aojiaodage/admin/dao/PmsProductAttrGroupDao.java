package com.aojiaodage.admin.dao;

import com.aojiaodage.admin.entity.PmsAttrGroup;
import com.aojiaodage.admin.vo.ProductAttrGroupWithAttr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface PmsProductAttrGroupDao extends BaseMapper<PmsAttrGroup> {
    List<ProductAttrGroupWithAttr> selectListByCategoryId(Integer id);
}
