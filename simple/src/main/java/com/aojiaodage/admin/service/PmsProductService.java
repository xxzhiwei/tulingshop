package com.aojiaodage.admin.service;

import com.aojiaodage.admin.dto.PmsProductForm;
import com.aojiaodage.admin.dto.PmsProductQuery;
import com.aojiaodage.admin.dto.PmsProductStatusForm;
import com.aojiaodage.admin.entity.PmsProduct;
import com.aojiaodage.admin.vo.ProductDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface PmsProductService extends IService<PmsProduct> {

    Page<PmsProduct> getPagination(PmsProductQuery query);

    void save(PmsProductForm form);

    ProductDetail getDetail(Integer id);

    void update(PmsProductForm form);

    void removeFromShelf(PmsProductStatusForm form);

    void publish(PmsProductStatusForm form);

    void del(PmsProductForm form);
}
