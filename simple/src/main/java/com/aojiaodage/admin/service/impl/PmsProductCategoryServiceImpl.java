package com.aojiaodage.admin.service.impl;

import com.aojiaodage.admin.dao.PmsProductCategoryDao;
import com.aojiaodage.admin.dto.CategoryForm;
import com.aojiaodage.admin.entity.PmsProduct;
import com.aojiaodage.admin.entity.PmsProductCategory;
import com.aojiaodage.admin.service.PmsProductCategoryService;
import com.aojiaodage.admin.service.PmsProductService;
import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.common.interfaces.ChildPredicate;
import com.aojiaodage.common.util.TreeMaker;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryDao, PmsProductCategory> implements PmsProductCategoryService {
    Predicate<PmsProductCategory> rootPredicate = item -> item.getParentId().equals(0);
    ChildPredicate<PmsProductCategory> childPredicate = (PmsProductCategory child, PmsProductCategory parent) -> child.getParentId().equals(parent.getId());

    @Lazy
    @Autowired
    PmsProductService pmsProductService;

    @Override
    public List<PmsProductCategory> getList() {
        List<PmsProductCategory> list = list();

        return TreeMaker.make(list, rootPredicate, childPredicate);
    }

    private void check(CategoryForm form) {
        Integer level = form.getLevel();
        if (level > 1) {
            throw new CustomException("只允许二级分类");
        }

        if (form.getLevel() == 0 && form.getParentId() != 0) {
            form.setParentId(0);
        }

        if (level == 1) {

            Integer parentId = form.getParentId();

            PmsProductCategory parent = getById(parentId);

            if (parent == null) {
                throw new CustomException("父分类不存在");
            }
        }
    }

    @Override
    public void save(CategoryForm form) {
        check(form);

        PmsProductCategory category = new PmsProductCategory();

        BeanUtils.copyProperties(form, category);

        save(category);
    }

    @Override
    public void update(CategoryForm form) {
        Integer id = form.getId();
        PmsProductCategory category = getById(id);

        if (category == null) {
            throw new CustomException("数据不存在");
        }

        category.setName(form.getName());

        updateById(category);
    }

    @Override
    public void del(CategoryForm form) {
        Integer id = form.getId();
        PmsProductCategory category = getById(id);

        if (category == null) {
            throw new CustomException("数据不存在");
        }

        LambdaQueryWrapper<PmsProduct> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
        productLambdaQueryWrapper.eq(PmsProduct::getProductCategoryId, category.getId());

        int count = pmsProductService.count(productLambdaQueryWrapper);

        if (count > 0) {
            throw new CustomException("删除失败，该分类下存在商品");
        }

        removeById(id);
    }
}
