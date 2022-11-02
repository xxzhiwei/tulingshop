package com.aojiaodage.portal.dao;

import com.aojiaodage.portal.entity.PmsAttr;
import com.aojiaodage.portal.vo.SaleAttr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface PmsProductAttrDao extends BaseMapper<PmsAttr> {
    List<SaleAttr> selectSaleAttrListBySpuId(Integer id);
}
