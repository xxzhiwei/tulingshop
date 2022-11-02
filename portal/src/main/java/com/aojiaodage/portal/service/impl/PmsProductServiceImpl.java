package com.aojiaodage.portal.service.impl;

import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.portal.dao.PmsProductAttrGroupDao;
import com.aojiaodage.portal.dao.PmsProductDao;
import com.aojiaodage.portal.entity.PmsProduct;
import com.aojiaodage.portal.service.PmsProductAttrService;
import com.aojiaodage.portal.service.PmsProductService;
import com.aojiaodage.portal.vo.AttrGroup;
import com.aojiaodage.portal.vo.ProductDetail;
import com.aojiaodage.portal.vo.SaleAttr;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductDao, PmsProduct> implements PmsProductService {

    @Autowired
    PmsProductAttrService pmsProductAttrService;

    @Autowired
    PmsProductAttrGroupDao pmsProductAttrGroupDao;

    @Override
    public ProductDetail getDetail(Integer id) {

        PmsProduct product = getById(id);

        if (product == null) {
            throw new CustomException("数据不存在，id：" + id);
        }

        ProductDetail detail = new ProductDetail();
        BeanUtils.copyProperties(product, detail);

        List<SaleAttr> saleAttrList = pmsProductAttrService.getSaleAttrListBySpuId(id);

        List<AttrGroup> attrGroupList = pmsProductAttrGroupDao.selectListByCategoryId(id);

        detail.setSaleAttrs(saleAttrList);
        detail.setAttrGroups(attrGroupList);
        return detail;
    }
}
