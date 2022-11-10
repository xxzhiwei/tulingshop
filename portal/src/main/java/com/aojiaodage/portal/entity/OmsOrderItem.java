package com.aojiaodage.portal.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 订单中所包含的商品(OmsOrderItem)实体类
 *
 * @author makejava
 * @since 2022-11-10 15:05:41
 */
@Getter
@Setter
public class OmsOrderItem implements Serializable {
    private static final long serialVersionUID = 816517887799037225L;
    
    private Integer id;
    /**
     * 订单id
     */
    private Integer orderId;
    /**
     * 订单编号
     */
    private String orderSn;
    
    private Integer productId;
    
    private String productPic;
    
    private String productName;
    
    private String productBrand;
    
    private String productSn;
    /**
     * 销售价格
     */
    private Double productPrice;
    /**
     * 购买数量
     */
    private Integer productQuantity;
    /**
     * 商品sku编号
     */
    private Integer productSkuId;
    /**
     * 商品sku条码
     */
    private String productSkuCode;
    /**
     * 商品分类id
     */
    private Integer productCategoryId;
    /**
     * 商品的销售属性
     */
    private String sp1;
    
    private String sp2;
    
    private String sp3;
    /**
     * 商品促销名称
     */
    private String promotionName;
    /**
     * 商品促销分解金额
     */
    private Double promotionAmount;
    /**
     * 优惠券优惠分解金额
     */
    private Double couponAmount;
    /**
     * 积分优惠分解金额
     */
    private Double integrationAmount;
    /**
     * 该商品经过优惠后的分解金额
     */
    private Double realAmount;
    
    private Integer giftIntegration;
    
    private Integer giftGrowth;
    /**
     * 商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
     */
    private String productAttr;


}

