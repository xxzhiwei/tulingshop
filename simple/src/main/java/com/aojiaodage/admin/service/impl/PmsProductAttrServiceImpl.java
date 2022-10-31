package com.aojiaodage.admin.service.impl;

import com.aojiaodage.admin.dao.PmsProductAttrDao;
import com.aojiaodage.admin.dto.AttrQuery;
import com.aojiaodage.admin.entity.*;
import com.aojiaodage.admin.service.*;
import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.common.util.PaginationUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsProductAttrServiceImpl extends ServiceImpl<PmsProductAttrDao, PmsAttr> implements PmsProductAttrService {

    @Autowired
    PmsProductAttrGroupService pmsProductAttrGroupService;

    @Autowired
    PmsProductCategoryService pmsProductCategoryService;

    @Autowired
    PmsAttrSpuRelationService pmsAttrSpuRelationService;

    @Autowired
    PmsAttrSpuSkuRelationService pmsAttrSpuSkuRelationService;

    @Override
    public List<PmsAttr> getSaleAttrListByCategoryId(Integer id) {

        LambdaQueryWrapper<PmsAttr> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PmsAttr::getCategoryId, id);
        queryWrapper.eq(PmsAttr::getType2, 2);

        return list(queryWrapper);
    }

    @Override
    public void update(PmsAttr form) {
        Integer id = form.getId();
        PmsAttr attr = getById(id);

        if (attr == null) {
            throw new CustomException("数据不存在");
        }
        attr.setType(form.getType());
        attr.setValue(form.getValue());
        attr.setName(form.getName());

        Integer categoryId = attr.getCategoryId();
        Integer groupId = attr.getGroupId();

        if (!categoryId.equals(attr.getCategoryId())) {
            PmsProductCategory category = pmsProductCategoryService.getById(categoryId);

            if (category == null) {
                throw new CustomException("分类数据不存在");
            }
            attr.setCategoryId(categoryId);
            attr.setCategoryName(category.getName());
        }

        // 更新规格属性分组id
        if (attr.getType2() == 1 && !attr.getGroupId().equals(groupId)) {
            PmsAttrGroup attrGroup = pmsProductAttrGroupService.getById(groupId);
            if (attrGroup == null) {
                throw new CustomException("分组数据不存在");
            }
            attr.setGroupId(groupId);
        }
        updateById(attr);
    }

    @Override
    public void saveAttr(PmsAttr form) {
        Integer type2 = form.getType2();
        Integer groupId = form.getGroupId();
        Integer categoryId = form.getCategoryId();
        Integer type = form.getType();
        PmsAttr attr = new PmsAttr();

        if (type2 == 1 && groupId == null) {
            throw new CustomException("添加规格属性时，groupId为必填");
        }

        if (type2 == 1) {
            PmsAttrGroup attrGroup = pmsProductAttrGroupService.getById(groupId);
            if (attrGroup == null) {
                throw new CustomException("分组数据不存在");
            }
            attr.setGroupId(groupId);
        }

        PmsProductCategory category = pmsProductCategoryService.getById(categoryId);

        if (category == null) {
            throw new CustomException("分类数据不存在");
        }

        attr.setValue(form.getValue());
        attr.setName(form.getName());
        attr.setCategoryId(categoryId);

        attr.setType2(type2);
        attr.setCategoryName(category.getName());

        // 销售属性都是多选【其实目前销售属性没用到type】
        if (type2 == 2) {
            attr.setType(2);
        }
        else {
            attr.setType(type);
        }

        save(attr);
    }

    @Override
    public void del(PmsAttr form) {
        Integer id = form.getId();
        PmsAttr attr = getById(id);

        if (attr == null) {
            throw new CustomException("数据不存在");
        }
        Integer type2 = attr.getType2();
        // 规格属性
        if (type2 == 1) {
            LambdaQueryWrapper<PmsAttrSpuRelation> attrSpuRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            attrSpuRelationLambdaQueryWrapper.eq(PmsAttrSpuRelation::getAttrId, id);
            int count = pmsAttrSpuRelationService.count(attrSpuRelationLambdaQueryWrapper);
            if (count > 0) {
                throw new CustomException("删除失败，存在与之关联的spu");
            }
        }
        // 销售属性
        else if (type2 == 2) {
            LambdaQueryWrapper<PmsAttrSpuSkuRelation> attrSpuSkuRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            attrSpuSkuRelationLambdaQueryWrapper.eq(PmsAttrSpuSkuRelation::getAttrId, id);
            int count = pmsAttrSpuSkuRelationService.count(attrSpuSkuRelationLambdaQueryWrapper);
            if (count > 0) {
                throw new CustomException("删除失败，存在与之关联的sku");
            }
        }

        removeById(id);
    }

    @Override
    public Page<PmsAttr> getPagination(AttrQuery query) {
        Integer categoryId = query.getCategoryId();
        Integer type2 = query.getType2();
        String keyword = query.getKeywords();

        LambdaQueryWrapper<PmsAttr> attrLambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (categoryId != null) {
            attrLambdaQueryWrapper.eq(PmsAttr::getCategoryId, categoryId);
        }

        if (type2 != null) {
            attrLambdaQueryWrapper.eq(PmsAttr::getType2, type2);
        }

        if (keyword != null) {
            attrLambdaQueryWrapper.like(PmsAttr::getName, keyword);
        }

        Page<PmsAttr> page = PaginationUtil.getPage(query);

        page(page, attrLambdaQueryWrapper);
        return page;
    }
}
