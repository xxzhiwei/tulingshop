package com.aojiaodage.admin.service.impl;

import com.aojiaodage.admin.dao.PmsAttrSpuRelationDao;
import com.aojiaodage.admin.entity.PmsAttrSpuRelation;
import com.aojiaodage.admin.service.PmsAttrSpuRelationService;
import com.aojiaodage.admin.vo.PmsAttrSpuRelationWithGroupId;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsAttrSpuRelationServiceImpl extends ServiceImpl<PmsAttrSpuRelationDao, PmsAttrSpuRelation> implements PmsAttrSpuRelationService {

    @Override
    public List<PmsAttrSpuRelationWithGroupId> getListBySpuId(Integer id) {
        return baseMapper.selectListBySpuId(id);
    }
}
