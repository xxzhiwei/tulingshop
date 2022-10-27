package com.aojiaodage.admin.dao;

import com.aojiaodage.admin.entity.PmsProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductDao extends BaseMapper<PmsProduct> {
    // removed代表「下架」
    // published代表「上架」
    void updatePublishStatus(@Param("list") List<Integer> ids, @Param("status") Integer status);
}
