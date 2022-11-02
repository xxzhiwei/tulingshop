package com.aojiaodage.portal.service;

import com.aojiaodage.common.util.R;
import com.aojiaodage.portal.dto.SearchQuery;
import com.aojiaodage.portal.entity.PmsProduct;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface SearchService extends IService<PmsProduct> {
    Page<PmsProduct> getPagination(SearchQuery query);
    R<Map<String, Object>> build(SearchQuery query, Page<PmsProduct> page);
}
