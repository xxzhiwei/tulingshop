package com.aojiaodage.portal.controller;

import com.aojiaodage.common.util.R;
import com.aojiaodage.portal.dto.SearchQuery;
import com.aojiaodage.portal.entity.PmsProduct;
import com.aojiaodage.portal.service.SearchService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 如果不用es，搜索sku太过于困难
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("/pagination")
    public R<?> getPagination(SearchQuery query) {
        Page<PmsProduct> pagination = searchService.getPagination(query);
        return searchService.build(query, pagination);
    }
}
