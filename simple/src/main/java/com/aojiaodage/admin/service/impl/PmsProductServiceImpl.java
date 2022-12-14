package com.aojiaodage.admin.service.impl;

import com.aojiaodage.admin.dao.PmsProductDao;
import com.aojiaodage.admin.dto.PmsProductForm;
import com.aojiaodage.admin.dto.PmsProductQuery;
import com.aojiaodage.admin.dto.PmsProductStatusForm;
import com.aojiaodage.admin.entity.*;
import com.aojiaodage.admin.service.*;
import com.aojiaodage.admin.vo.PmsAttrSpuRelationWithGroupId;
import com.aojiaodage.admin.vo.ProductDetail;
import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.common.util.PaginationUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductDao, PmsProduct> implements PmsProductService {

    @Autowired
    PmsAttrSpuRelationService pmsAttrSpuRelationService;

    @Autowired
    PmsAttrSpuSkuRelationService pmsAttrSpuSkuRelationService;

    @Autowired
    PmsProductSkuService pmsProductSkuService;

    @Autowired
    PmsBrandService pmsBrandService;

    @Autowired
    PmsProductCategoryService pmsProductCategoryService;

    @Autowired
    PmsProductAttrService pmsProductAttrService;

    @Override
    public Page<PmsProduct> getPagination(PmsProductQuery query) {
        Page<PmsProduct> page = PaginationUtil.getPage(query);
        LambdaQueryWrapper<PmsProduct> pmsProductLambdaQueryWrapper = new LambdaQueryWrapper<>();

        pmsProductLambdaQueryWrapper.eq(PmsProduct::getDeleteStatus, 0);
        Integer publishStatus = query.getPublishStatus();
        Integer categoryId = query.getCategoryId();
        Integer brandId = query.getBrandId();
        String productSn = query.getProductSn();
        String keyword = query.getKeywords();

        // ????????????????????????????????????????????????
        if (publishStatus != null) {
            pmsProductLambdaQueryWrapper.eq(PmsProduct::getPublishStatus, publishStatus);
        }
        if (categoryId != null) {
            pmsProductLambdaQueryWrapper.eq(PmsProduct::getProductCategoryId, categoryId);
        }
        if (brandId != null) {
            pmsProductLambdaQueryWrapper.eq(PmsProduct::getBrandId, brandId);
        }
        if (productSn != null) {
            pmsProductLambdaQueryWrapper.eq(PmsProduct::getProductSn, productSn);
        }
        if (StringUtils.hasText(keyword)) {
            pmsProductLambdaQueryWrapper.like(PmsProduct::getName, keyword);
        }
        this.page(page, pmsProductLambdaQueryWrapper);
        return page;
    }

    private void checkAndSetCategory(PmsProductForm form, PmsProduct product) {
        Integer productCategoryId = form.getProductCategoryId();

        PmsProductCategory category = pmsProductCategoryService.getById(productCategoryId);
        if (category == null) {
            throw new CustomException("?????????????????????????????????" + productCategoryId);
        }

        product.setProductCategoryName(category.getName());
    }

    private void checkAndSetBrand(PmsProductForm form, PmsProduct product) {

        Integer brandId = form.getBrandId();
        PmsBrand brand = pmsBrandService.getById(brandId);
        if (brand == null) {
            throw new CustomException("?????????????????????????????????" + brandId);
        }
        product.setBrandName(brand.getName());
    }

    private void checkAttr(PmsProductForm form) {
        Set<Integer> attrIds = new HashSet<>();
        List<PmsAttrSpuRelation> attrSpuRelations = form.getAttrs();

        for (PmsAttrSpuRelation attrSpuRelation : attrSpuRelations) {
            Integer attrId = attrSpuRelation.getAttrId();
            attrIds.add(attrId);
        }

        List<PmsProductSku> skus = form.getSkus();

        for (PmsProductSku sku : skus) {
            List<PmsAttrSpuSkuRelation> attrSpuSkuRelations = sku.getAttrs();
            for (PmsAttrSpuSkuRelation attrSpuSkuRelation : attrSpuSkuRelations) {
                attrIds.add(attrSpuSkuRelation.getAttrId());
            }
        }

        LambdaQueryWrapper<PmsAttr> pmsAttrLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsAttrLambdaQueryWrapper.in(PmsAttr::getId, attrIds);

        int count = pmsProductAttrService.count(pmsAttrLambdaQueryWrapper);
        if (count != attrIds.size()) {
            throw new CustomException("????????????????????????????????????");
        }
    }

    private List<PmsAttrSpuSkuRelation> getAttrSpuSkuRelations(List<PmsProductSku> skus, PmsProduct product) {
        List<PmsAttrSpuSkuRelation> attrSpuSkuRelations = new ArrayList<>();
        for (PmsProductSku sku : skus) {
            List<PmsAttrSpuSkuRelation> skuAttrs = sku.getAttrs();
            for (PmsAttrSpuSkuRelation pmsAttrSpuSkuRelation : skuAttrs) {
                pmsAttrSpuSkuRelation.setSkuId(sku.getId());

                if (pmsAttrSpuSkuRelation.getSpuId() == null) {
                    pmsAttrSpuSkuRelation.setSpuId(product.getId());
                }
            }
            attrSpuSkuRelations.addAll(skuAttrs);
        }
        return attrSpuSkuRelations;
    }

    @Transactional
    @Override
    public void save(PmsProductForm form) {
        checkAttr(form);
        PmsProduct product = new PmsProduct();

        BeanUtils.copyProperties(form, product);

        product.setProductSn(UUID.randomUUID().toString().replaceAll("-", ""));
        product.setDeleteStatus(0);
        checkAndSetCategory(form, product);
        checkAndSetBrand(form, product);

        // ??????spu
        save(product);

        // ?????????????????????attrs?????????PmsAttrSpuRelation
        List<PmsAttrSpuRelation> attrSpuRelations = form.getAttrs();
        attrSpuRelations.forEach(item -> item.setSpuId(product.getId()));

        pmsAttrSpuRelationService.saveBatch(attrSpuRelations);

        List<PmsProductSku> skus = form.getSkus();

        // ??????sku
        skus.forEach(item -> item.setSpuId(product.getId()));
        pmsProductSkuService.saveBatch(skus);

        // ??????????????????????????????
        List<PmsAttrSpuSkuRelation> attrSpuSkuRelations = getAttrSpuSkuRelations(skus, product);
        pmsAttrSpuSkuRelationService.saveBatch(attrSpuSkuRelations);
    }

    @Transactional
    @Override
    public void update(PmsProductForm form) {
        Integer id = form.getId();
        PmsProduct product = getById(id);

        if (product == null) {
            throw new CustomException("???????????????");
        }
        checkAttr(form);

        Integer productCategoryId = form.getProductCategoryId();
        Integer brandId = form.getBrandId();

        product.setName(form.getName());
        product.setDescription(form.getDescription());
        product.setPublishStatus(form.getPublishStatus());
        product.setNewStatus(form.getNewStatus());
        product.setRecommendStatus(form.getRecommendStatus());
        product.setPrice(form.getPrice());
        product.setGiftGrowth(form.getGiftGrowth());
        product.setGiftPoint(form.getGiftPoint());
        product.setSubTitle(form.getSubTitle());
        product.setUnit(form.getUnit());
        product.setWeight(form.getWeight());

        // ??????????????????
        if (!productCategoryId.equals(product.getProductCategoryId())) {
            checkAndSetCategory(form, product);
            product.setProductCategoryId(productCategoryId);
        }

        // ??????????????????
        if (!brandId.equals(product.getBrandId())) {
            checkAndSetBrand(form, product);
            product.setBrandId(brandId);
        }

        updateById(product);

        // ???????????????????????????value?????????????????????????????????attrSpuRelations???skus???attrSpuSkuRelations??????product?????????
        List<PmsAttrSpuRelation> attrSpuRelations = form.getAttrs();
        pmsAttrSpuRelationService.updateBatchById(attrSpuRelations);

        List<PmsProductSku> skus = form.getSkus();

        // &sku??????????????????????????????
        List<PmsProductSku> skusSaved = skus.stream().filter(item -> item.getId() != null).collect(Collectors.toList());
        pmsProductSkuService.updateBatchById(skusSaved);

        // &????????????????????????????????????
        List<PmsAttrSpuSkuRelation> attrSpuSkuRelationsSaved = getAttrSpuSkuRelations(skusSaved, product);
        pmsAttrSpuSkuRelationService.updateBatchById(attrSpuSkuRelationsSaved);

        // &??????sku&??????????????????????????????
        skus.removeAll(skusSaved);

        if (skus.size() > 0) {
            skus.forEach(item -> item.setSpuId(product.getId()));
            pmsProductSkuService.saveBatch(skus);

            List<PmsAttrSpuSkuRelation> attrSpuSkuRelations = getAttrSpuSkuRelations(skus, product);
            pmsAttrSpuSkuRelationService.saveBatch(attrSpuSkuRelations);
        }

        // ??????sku&sku???????????????????????????????????????
        List<Integer> removing = form.getRemoving();
        if (removing != null && removing.size() > 0) {
            pmsProductSkuService.removeByIds(removing);

            LambdaQueryWrapper<PmsAttrSpuSkuRelation> attrSpuSkuRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            attrSpuSkuRelationLambdaQueryWrapper.in(PmsAttrSpuSkuRelation::getSkuId, removing);
            pmsAttrSpuSkuRelationService.remove(attrSpuSkuRelationLambdaQueryWrapper);
        }
    }

    // 0=????????????1=?????????2=??????
    @Override
    public void removeFromShelf(PmsProductStatusForm form) {
        baseMapper.updatePublishStatus(form.getIds(), 2);
    }

    @Override
    public void publish(PmsProductStatusForm form) {
        baseMapper.updatePublishStatus(form.getIds(), 1);
    }

    @Override
    public void del(PmsProductForm form) {
        Integer id = form.getId();

        PmsProduct product = getById(id);

        if (product == null) {
            throw new CustomException("???????????????");
        }

        LambdaQueryWrapper<PmsProductSku> productSkuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        productSkuLambdaQueryWrapper.eq(PmsProductSku::getSpuId, id);

        int count = pmsProductSkuService.count(productSkuLambdaQueryWrapper);

        if (count > 0) {
            throw new CustomException("???????????????????????????????????????sku");
        }
    }

    @Override
    public ProductDetail getDetail(Integer id) {

        PmsProduct product = getById(id);

        if (product == null) {
            throw new CustomException("??????????????????id???" + id);
        }

        ProductDetail detail = new ProductDetail();
        BeanUtils.copyProperties(product, detail);

        // ??????????????????
        List<PmsAttrSpuRelationWithGroupId> attrSpuRelations = pmsAttrSpuRelationService.getListBySpuId(product.getId());
        detail.setAttrs(attrSpuRelations);

        // ??????sku
        LambdaQueryWrapper<PmsProductSku> pmsProductSkuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsProductSkuLambdaQueryWrapper.eq(PmsProductSku::getSpuId, product.getId());
        List<PmsProductSku> skus = pmsProductSkuService.list(pmsProductSkuLambdaQueryWrapper);

        // ??????????????????
        LambdaQueryWrapper<PmsAttrSpuSkuRelation> attrSpuSkuRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        attrSpuSkuRelationLambdaQueryWrapper.eq(PmsAttrSpuSkuRelation::getSpuId, product.getId());
        List<PmsAttrSpuSkuRelation> attrSpuSkuRelations = pmsAttrSpuSkuRelationService.list(attrSpuSkuRelationLambdaQueryWrapper);

        for (PmsProductSku sku : skus) {
            // ???????????????????????????sku.setAttrs()????????????????????????
            sku.setAttrs(attrSpuSkuRelations.stream().filter(item -> item.getSkuId().equals(sku.getId())).collect(Collectors.toList()));
        }
        detail.setSkus(skus);
        return detail;
    }
}
