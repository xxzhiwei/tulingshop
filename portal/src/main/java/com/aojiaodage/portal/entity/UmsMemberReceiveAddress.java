package com.aojiaodage.portal.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 会员收货地址表(UmsMemberReceiveAddress)实体类
 *
 * @author makejava
 * @since 2022-11-10 15:05:58
 */
@Setter
@Getter
public class UmsMemberReceiveAddress implements Serializable {
    private static final long serialVersionUID = 974574638905673538L;
    
    private Integer id;
    
    private Integer memberId;
    /**
     * 收货人名称
     */
    private String name;
    
    private String phoneNumber;
    /**
     * 是否为默认
     */
    private Integer defaultStatus;
    /**
     * 邮政编码
     */
    private String postCode;
    /**
     * 省份/直辖市
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区
     */
    private String region;
    /**
     * 详细地址(街道)
     */
    private String detailAddress;


}

