package com.aojiaodage.portal.service;

import com.aojiaodage.portal.entity.PmsAttr;
import com.aojiaodage.portal.vo.SaleAttr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PmsProductAttrService extends IService<PmsAttr> {
    List<SaleAttr> getSaleAttrListBySpuId(Integer id);
}
