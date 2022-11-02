package com.aojiaodage.portal.service.impl;

import com.aojiaodage.common.interfaces.ChildPredicate;
import com.aojiaodage.common.util.TreeMaker;
import com.aojiaodage.portal.dao.PmsProductCategoryDao;
import com.aojiaodage.portal.entity.PmsProductCategory;
import com.aojiaodage.portal.service.PmsProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

// 虽然是操作同一张表，但是业务不同【真的能共用？理论上，提供一个模板，其他模块再此基础上拓展是可以的】
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryDao, PmsProductCategory> implements PmsProductCategoryService {

    Predicate<PmsProductCategory> rootPredicate = item -> item.getParentId().equals(0);
    ChildPredicate<PmsProductCategory> childPredicate = (PmsProductCategory child, PmsProductCategory parent) -> child.getParentId().equals(parent.getId());

    @Override
    public List<PmsProductCategory> getList() {
        List<PmsProductCategory> list = list();

        return TreeMaker.make(list, rootPredicate, childPredicate);
    }
}
