package com.aojiaodage.admin.service.impl;

import com.aojiaodage.admin.dao.PmsProductAttrGroupDao;
import com.aojiaodage.admin.dto.AttrGroupForm;
import com.aojiaodage.admin.entity.PmsAttrGroup;
import com.aojiaodage.admin.service.PmsProductAttrGroupService;
import com.aojiaodage.admin.vo.ProductAttrGroupWithAttr;
import com.aojiaodage.admin.service.PmsProductCategoryService;
import com.aojiaodage.common.dto.Query;
import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.common.util.PaginationUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PmsProductAttrGroupServiceImpl extends ServiceImpl<PmsProductAttrGroupDao, PmsAttrGroup> implements PmsProductAttrGroupService {

    @Autowired
    PmsProductCategoryService pmsProductCategoryService;

    @Override
    public List<ProductAttrGroupWithAttr> getListByCategoryId(Integer id) {
        return baseMapper.selectListByCategoryId(id);
    }

    @Override
    public void del(AttrGroupForm form) {
        Integer id = form.getId();
        PmsAttrGroup attrGroup = getById(id);

        if (attrGroup == null) {
            throw new CustomException("数据不存在");
        }

        removeById(id);
    }

    @Override
    public void update(AttrGroupForm form) {
        Integer id = form.getId();

        PmsAttrGroup attrGroup = getById(id);

        if (attrGroup == null) {
            throw new CustomException("数据不存在");
        }
        attrGroup.setName(form.getName());

        updateById(attrGroup);
    }

    @Override
    public void save(AttrGroupForm form) {

        PmsAttrGroup attrGroup = new PmsAttrGroup();
        attrGroup.setName(form.getName());

        save(attrGroup);
    }

    @Override
    public Page<PmsAttrGroup> getPagination(Query query) {
        Page<PmsAttrGroup> page = PaginationUtil.getPage(query);
        String keyword = query.getKeywords();

        LambdaQueryWrapper<PmsAttrGroup> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(keyword)) {
            queryWrapper.like(PmsAttrGroup::getName, keyword);
        }

        page(page, queryWrapper);
        return page;
    }

    @Override
    public List<?> getList() {
        return list();
    }
}
