package com.aojiaodage.portal.dao;

import com.aojiaodage.portal.entity.PmsAttrGroup;
import com.aojiaodage.portal.vo.AttrGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface PmsProductAttrGroupDao extends BaseMapper<PmsAttrGroup> {
    List<AttrGroup> selectListByCategoryId(Integer id);
}
