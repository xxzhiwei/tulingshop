package com.aojiaodage.portal.service.impl;

import com.aojiaodage.common.util.PaginationUtil;
import com.aojiaodage.common.util.R;
import com.aojiaodage.portal.dao.PmsProductDao;
import com.aojiaodage.portal.dto.SearchQuery;
import com.aojiaodage.portal.entity.PmsBrand;
import com.aojiaodage.portal.entity.PmsProduct;
import com.aojiaodage.portal.entity.PmsProductCategory;
import com.aojiaodage.portal.service.SearchService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class SearchServiceImpl extends ServiceImpl<PmsProductDao, PmsProduct> implements SearchService {
    /**
     * 分有几种情况：
     * 一、有keyword
     * 1、只有keyword的情况，生成brand与category列表返回【前端要显示】
     * 2、keyword+brandId时，只生成category【keyword+categoryId同理】
     * 二、无keyword
     * 1、只有brandId，生成分类列表
     * 2、只有categoryId，生成品牌列表【同时生成与当前categoryId同级的其他分类（用它的父分类id查询？）
     */
    @Override
    public Page<PmsProduct> getPagination(SearchQuery query) {
        query.setSize(50);

        Page<PmsProduct> page = PaginationUtil.getPage(query);

        LambdaQueryWrapper<PmsProduct> productQueryWrapper = new LambdaQueryWrapper<>();
        String keywords = query.getKeywords();
        Integer brandId = query.getBrandId();
        Integer categoryId = query.getCategoryId();
        Integer sort = query.getSort();

        // (keywords like '%?%' or name like '%?%' or subTitle like '%?%') and brandId = ? and categoryId = ?
        if (StringUtils.hasText(keywords)) {
            productQueryWrapper.and(qw -> qw
                    .like(PmsProduct::getKeywords, keywords)
                    .or()
                    .like(PmsProduct::getName, keywords)
                    .or()
                    .like(PmsProduct::getSubTitle, keywords)
            );
        }

        if (brandId != null) {
            productQueryWrapper.and(qw -> qw.eq(PmsProduct::getBrandId, brandId));
        }

        if (categoryId != null) {
            productQueryWrapper.and(qw -> qw.eq(PmsProduct::getProductCategoryId, categoryId));
        }

        if (sort != null) {
            if (sort == 1) {
                productQueryWrapper.orderByDesc(PmsProduct::getSort);
            }

            if (sort == 2) {
                productQueryWrapper.orderByDesc(PmsProduct::getSale);
            }

            if (sort == 3) {
                productQueryWrapper.orderByDesc(PmsProduct::getPrice);
            }

            if (sort == 4) {
                productQueryWrapper.orderByAsc(PmsProduct::getPrice);
            }

            if (sort == 5) {
                productQueryWrapper.orderByAsc(PmsProduct::getNewStatus);
            }
        }

        return page(page, productQueryWrapper);
    }

    // 构建搜索页数据
    public R<Map<String, Object>> build(SearchQuery query, Page<PmsProduct> page) {
        Integer brandId = query.getBrandId();
        Integer categoryId = query.getCategoryId();

        R<Map<String, Object>> r = R.ok(page);

        HashSet<PmsBrand> pmsBrands = new HashSet<>();
        Set<PmsProductCategory> pmsCategories = new HashSet<>();

        List<PmsProduct> records = page.getRecords();

        for (PmsProduct record : records) {
            if (brandId == null) {
                PmsBrand pmsBrand = new PmsBrand();
                pmsBrand.setId(record.getBrandId());
                pmsBrand.setName(record.getBrandName());
                pmsBrands.add(pmsBrand);
            }
            if (categoryId == null) {
                PmsProductCategory productCategory = new PmsProductCategory();
                productCategory.setId(record.getProductCategoryId());
                productCategory.setName(record.getProductCategoryName());
                pmsCategories.add(productCategory);
            }
        }
        Map<String, Object> data = r.getData();
        data.put("brands", pmsBrands);
        data.put("categories", pmsCategories);
        return r;
    }
}
