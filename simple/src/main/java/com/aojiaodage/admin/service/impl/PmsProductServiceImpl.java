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
        String keyword = query.getKeyword();

        // 完全不会涉及到多表查询的问题。。
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
            throw new CustomException("保存失败，分类不存在：" + productCategoryId);
        }

        product.setProductCategoryName(category.getName());
    }

    private void checkAndSetBrand(PmsProductForm form, PmsProduct product) {

        Integer brandId = form.getBrandId();
        PmsBrand brand = pmsBrandService.getById(brandId);
        if (brand == null) {
            throw new CustomException("保存失败，品牌不存在：" + brandId);
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
            throw new CustomException("保存失败，属性已发生变更");
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

        // 保存spu
        save(product);

        // 保存规格参数【attrs其实是PmsAttrSpuRelation
        List<PmsAttrSpuRelation> attrSpuRelations = form.getAttrs();
        attrSpuRelations.forEach(item -> item.setSpuId(product.getId()));

        pmsAttrSpuRelationService.saveBatch(attrSpuRelations);

        List<PmsProductSku> skus = form.getSkus();

        // 保存sku
        skus.forEach(item -> item.setSpuId(product.getId()));
        pmsProductSkuService.saveBatch(skus);

        // 保存销售属性关联关系
        List<PmsAttrSpuSkuRelation> attrSpuSkuRelations = getAttrSpuSkuRelations(skus, product);
        pmsAttrSpuSkuRelationService.saveBatch(attrSpuSkuRelations);
    }

    @Transactional
    @Override
    public void update(PmsProductForm form) {
        Integer id = form.getId();
        PmsProduct product = getById(id);

        if (product == null) {
            throw new CustomException("数据不存在");
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

        // 更新分类名称
        if (!productCategoryId.equals(product.getProductCategoryId())) {
            checkAndSetCategory(form, product);
            product.setProductCategoryId(productCategoryId);
        }

        // 更新品牌名称
        if (!brandId.equals(product.getBrandId())) {
            checkAndSetBrand(form, product);
            product.setBrandId(brandId);
        }

        updateById(product);

        // 更新规格参数【只有value会改变】，应该要校验：attrSpuRelations、skus、attrSpuSkuRelations都是product下面的
        List<PmsAttrSpuRelation> attrSpuRelations = form.getAttrs();
        pmsAttrSpuRelationService.updateBatchById(attrSpuRelations);

        List<PmsProductSku> skus = form.getSkus();

        // &sku【已经存在于数据库】
        List<PmsProductSku> skusSaved = skus.stream().filter(item -> item.getId() != null).collect(Collectors.toList());
        pmsProductSkuService.updateBatchById(skusSaved);

        // &销售属性【已存在的关系】
        List<PmsAttrSpuSkuRelation> attrSpuSkuRelationsSaved = getAttrSpuSkuRelations(skusSaved, product);
        pmsAttrSpuSkuRelationService.updateBatchById(attrSpuSkuRelationsSaved);

        // &新增sku&新增销售属性关联关系
        skus.removeAll(skusSaved);

        if (skus.size() > 0) {
            skus.forEach(item -> item.setSpuId(product.getId()));
            pmsProductSkuService.saveBatch(skus);

            List<PmsAttrSpuSkuRelation> attrSpuSkuRelations = getAttrSpuSkuRelations(skus, product);
            pmsAttrSpuSkuRelationService.saveBatch(attrSpuSkuRelations);
        }

        // 删除sku&sku对应的销售属性（如果有的话
        List<Integer> removing = form.getRemoving();
        if (removing != null && removing.size() > 0) {
            pmsProductSkuService.removeByIds(removing);

            LambdaQueryWrapper<PmsAttrSpuSkuRelation> attrSpuSkuRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            attrSpuSkuRelationLambdaQueryWrapper.in(PmsAttrSpuSkuRelation::getSkuId, removing);
            pmsAttrSpuSkuRelationService.remove(attrSpuSkuRelationLambdaQueryWrapper);
        }
    }

    // 0=未发布，1=上架，2=下架
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
            throw new CustomException("数据不存在");
        }

        LambdaQueryWrapper<PmsProductSku> productSkuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        productSkuLambdaQueryWrapper.eq(PmsProductSku::getSpuId, id);

        int count = pmsProductSkuService.count(productSkuLambdaQueryWrapper);

        if (count > 0) {
            throw new CustomException("删除失败，该商品存在关联的sku");
        }
    }

    @Override
    public ProductDetail getDetail(Integer id) {

        PmsProduct product = getById(id);

        ProductDetail detail = new ProductDetail();
        BeanUtils.copyProperties(product, detail);

        // 查询规格参数
        List<PmsAttrSpuRelationWithGroupId> attrSpuRelations = pmsAttrSpuRelationService.getListBySpuId(product.getId());
        detail.setAttrs(attrSpuRelations);

        // 查询sku
        LambdaQueryWrapper<PmsProductSku> pmsProductSkuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pmsProductSkuLambdaQueryWrapper.eq(PmsProductSku::getSpuId, product.getId());
        List<PmsProductSku> skus = pmsProductSkuService.list(pmsProductSkuLambdaQueryWrapper);

        // 查询销售属性
        LambdaQueryWrapper<PmsAttrSpuSkuRelation> attrSpuSkuRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        attrSpuSkuRelationLambdaQueryWrapper.eq(PmsAttrSpuSkuRelation::getSpuId, product.getId());
        List<PmsAttrSpuSkuRelation> attrSpuSkuRelations = pmsAttrSpuSkuRelationService.list(attrSpuSkuRelationLambdaQueryWrapper);

        for (PmsProductSku sku : skus) {
            // 这里为什么可以直接sku.setAttrs()而不受类型检查？
            sku.setAttrs(attrSpuSkuRelations.stream().filter(item -> item.getSkuId().equals(sku.getId())).collect(Collectors.toList()));
        }
        detail.setSkus(skus);
        return detail;
    }
}
