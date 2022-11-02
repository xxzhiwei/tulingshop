package com.aojiaodage.portal.service.impl;

import com.aojiaodage.portal.dao.PmsProductAttrDao;
import com.aojiaodage.portal.entity.PmsAttr;
import com.aojiaodage.portal.service.PmsProductAttrService;
import com.aojiaodage.portal.vo.SaleAttr;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsProductAttrServiceImpl extends ServiceImpl<PmsProductAttrDao, PmsAttr> implements PmsProductAttrService {
    @Override
    public List<SaleAttr> getSaleAttrListBySpuId(Integer id) {
        return baseMapper.selectSaleAttrListBySpuId(id);
    }
}
