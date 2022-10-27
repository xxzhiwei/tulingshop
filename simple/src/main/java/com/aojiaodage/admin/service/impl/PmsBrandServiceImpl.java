package com.aojiaodage.admin.service.impl;

import com.aojiaodage.admin.dao.PmsBrandDao;
import com.aojiaodage.admin.dto.BrandForm;
import com.aojiaodage.admin.dto.BrandQuery;
import com.aojiaodage.admin.entity.PmsBrand;
import com.aojiaodage.admin.entity.PmsProduct;
import com.aojiaodage.admin.service.PmsBrandService;
import com.aojiaodage.admin.service.PmsProductService;
import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.common.util.PaginationUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandDao, PmsBrand> implements PmsBrandService {

    @Lazy
    @Autowired
    PmsProductService pmsProductService;

    @Override
    public Page<PmsBrand> getPagination(BrandQuery query) {

        Page<PmsBrand> page = PaginationUtil.getPage(query);
        String keyword = query.getKeyword();
        String firstLetter = query.getFirstLetter();

        LambdaQueryWrapper<PmsBrand> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(keyword)) {
            queryWrapper.like(PmsBrand::getName, keyword);
        }
        if (StringUtils.hasText(firstLetter)) {
            queryWrapper.eq(PmsBrand::getFirstLetter, firstLetter);
        }

        page(page, queryWrapper);
        return page;
    }

    @Override
    public List<PmsBrand> getList() {
        return list();
    }

    @Override
    public void save(BrandForm form) {
        PmsBrand brand = new PmsBrand();
        BeanUtils.copyProperties(form, brand);

        save(brand);
    }

    @Override
    public void update(BrandForm form) {
        Integer id = form.getId();

        PmsBrand brand = getById(id);

        if (brand == null) {
            throw new CustomException("数据不存在");
        }

        brand.setName(form.getName());
        brand.setFirstLetter(form.getFirstLetter());
        brand.setLogo(form.getLogo());
        brand.setBrandStory(form.getBrandStory());

        updateById(brand);
    }

    @Override
    public void del(BrandForm form) {
        Integer id = form.getId();

        PmsBrand brand = getById(id);

        if (brand == null) {
            throw new CustomException("数据不存在");
        }
        LambdaQueryWrapper<PmsProduct> productLambdaQueryWrapper = new LambdaQueryWrapper<>();
        productLambdaQueryWrapper.eq(PmsProduct::getBrandId, id);

        int count = pmsProductService.count(productLambdaQueryWrapper);

        if (count > 0) {
            throw new CustomException("删除失败，该品牌下存在商品");
        }
        removeById(id);
    }
}
