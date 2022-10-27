package com.aojiaodage.admin.dao;

import com.aojiaodage.admin.entity.PmsAttrSpuRelation;
import com.aojiaodage.admin.vo.PmsAttrSpuRelationWithGroupId;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface PmsAttrSpuRelationDao extends BaseMapper<PmsAttrSpuRelation> {
    List<PmsAttrSpuRelationWithGroupId> selectListBySpuId(Integer id);
}
