package com.aojiaodage.portal.service;

import com.aojiaodage.portal.entity.ProductSku;
import com.baomidou.mybatisplus.extension.service.IService;

public interface PmsProductSkuService extends IService<ProductSku> {
    ProductSku getDetail(Integer id);
}
