package com.aojiaodage.admin.service;

import com.aojiaodage.admin.dto.MemberForm;
import com.aojiaodage.admin.dto.MemberQuery;
import com.aojiaodage.admin.entity.Member;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface MemberService extends IService<Member> {
    Page<Member> getPagination(MemberQuery query);

    void updateStatus(MemberForm form);
}
