package com.aojiaodage.portal.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 品牌表(PmsBrand)实体类
 *
 * @author makejava
 * @since 2022-10-18 14:32:18
 */
@Getter
@Setter
public class PmsBrand implements Serializable {
    private static final long serialVersionUID = 347400976088506608L;

    @TableId
    private Integer id;
    
    private String name;
    /**
     * 首字母
     */
    private String firstLetter;
    
    private Integer sort;
    /**
     * 是否为品牌制造商：0->不是；1->是
     */
    private Integer factoryStatus;
    
    private Integer showStatus;
    /**
     * 产品数量
     */
    private Integer productCount;
    /**
     * 产品评论数量
     */
    private Integer productCommentCount;
    /**
     * 品牌logo
     */
    private String logo;
    /**
     * 专区大图
     */
    private String bigPic;
    /**
     * 品牌故事
     */
    private String brandStory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PmsBrand pmsBrand = (PmsBrand) o;

        return getId().equals(pmsBrand.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}

