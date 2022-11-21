package com.aojiaodage.admin.service.impl;

import com.aojiaodage.admin.dao.MemberDao;
import com.aojiaodage.admin.dto.MemberForm;
import com.aojiaodage.admin.dto.MemberQuery;
import com.aojiaodage.admin.entity.Member;
import com.aojiaodage.admin.service.MemberService;
import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.common.util.PaginationUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberDao, Member> implements MemberService {

    @Override
    public Page<Member> getPagination(MemberQuery query) {

        Page<Member> page = PaginationUtil.getPage(query);

        String keywords = query.getKeywords();

        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(keywords)) {
            queryWrapper.and(qw -> qw.like(Member::getUsername, keywords)
                    .or()
                    .like(Member::getNickname, keywords)
                    .or()
                    .like(Member::getPhone, keywords));
        }
        return page(page, queryWrapper);
    }

    @Override
    public void updateStatus(MemberForm form) {
        Integer id = form.getId();

        Member member = getById(id);

        if (member == null) {
            throw new CustomException("数据不存在，id：" + id);
        }

        Integer status = form.getStatus();

        if (!status.equals(member.getStatus())) {
            member.setStatus(status);

            updateById(member);
        }
    }
}
