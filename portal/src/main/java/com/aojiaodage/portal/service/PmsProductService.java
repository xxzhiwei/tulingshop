package com.aojiaodage.portal.service;

import com.aojiaodage.portal.entity.PmsProduct;
import com.aojiaodage.portal.vo.ProductDetail;
import com.baomidou.mybatisplus.extension.service.IService;

public interface PmsProductService extends IService<PmsProduct> {
    ProductDetail getDetail(Integer id);
}
