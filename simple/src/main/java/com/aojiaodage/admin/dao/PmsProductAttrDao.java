package com.aojiaodage.admin.dao;

import com.aojiaodage.admin.entity.PmsAttr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface PmsProductAttrDao extends BaseMapper<PmsAttr> {
    List<PmsAttr> selectListBySpuId(Integer id);
    List<PmsAttr> selectListBySkuId(Integer id);
}
