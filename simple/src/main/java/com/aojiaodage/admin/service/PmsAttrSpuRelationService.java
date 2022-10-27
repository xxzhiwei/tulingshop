package com.aojiaodage.admin.service;

import com.aojiaodage.admin.entity.PmsAttrSpuRelation;
import com.aojiaodage.admin.vo.PmsAttrSpuRelationWithGroupId;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PmsAttrSpuRelationService extends IService<PmsAttrSpuRelation> {
    List<PmsAttrSpuRelationWithGroupId> getListBySpuId(Integer id);
}
