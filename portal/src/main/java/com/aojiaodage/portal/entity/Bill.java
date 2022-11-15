package com.aojiaodage.portal.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@TableName("oms_bill")
@Getter
@Setter
public class Bill {
    private Integer id;
    /**
     * 订单号（对外业务号）
     */
    private String orderSn;
    /**
     * 交易流水号
     */
    private String tradeNo;
    /**
     * 支付总金额
     */
    private BigDecimal totalAmount;
    /**
     * 交易内容
     */
    private String subject;
    /**
     * 支付状态
     */
    private String paymentStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 确认时间
     */
    private Date confirmTime;
    /**
     * 回调内容
     */
    private String callbackContent;
    /**
     * 回调时间
     */
    private Date callbackTime;
}
