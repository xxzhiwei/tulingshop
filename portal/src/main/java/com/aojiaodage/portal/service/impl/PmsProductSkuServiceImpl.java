package com.aojiaodage.portal.service.impl;

import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.portal.dao.PmsProductSkuDao;
import com.aojiaodage.portal.entity.ProductSku;
import com.aojiaodage.portal.service.PmsProductSkuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PmsProductSkuServiceImpl extends ServiceImpl<PmsProductSkuDao, ProductSku> implements PmsProductSkuService {
    @Override
    public ProductSku getDetail(Integer id) {
        ProductSku sku = getById(id);
        if (sku == null) {
            throw new CustomException("数据不存在，id：" + id);
        }
        return sku;
    }
}
