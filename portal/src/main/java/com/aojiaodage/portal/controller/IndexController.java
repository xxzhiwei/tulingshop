package com.aojiaodage.portal.controller;

import com.aojiaodage.common.util.R;
import com.aojiaodage.portal.entity.PmsProductCategory;
import com.aojiaodage.portal.service.PmsProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 主页控制器
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    PmsProductCategoryService pmsProductCategoryService;

    @GetMapping("/aggregation")
    public R<?> getAggregation() {
        Map<String, Object> map = new HashMap<>();
        // 获取分类数据【这个没那么简单，应该是单独维护的一个表：有跳转商品的、有搜索关键词的、有分类的】
        List<PmsProductCategory> categoryList = pmsProductCategoryService.getList();
        map.put("categoryList", categoryList);
        // ...
        return R.ok(map);
    }
}
