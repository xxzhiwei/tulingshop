package com.aojiaodage.admin.controller;

import com.aojiaodage.admin.dto.MemberForm;
import com.aojiaodage.admin.dto.MemberQuery;
import com.aojiaodage.admin.entity.Member;
import com.aojiaodage.admin.service.MemberService;
import com.aojiaodage.common.util.R;
import com.aojiaodage.common.validator.interfaces.UpdateField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/pagination")
    public R<?> getPagination(MemberQuery query) {
        Page<Member> pagination = memberService.getPagination(query);

        return R.ok(pagination);
    }

    @PostMapping("/updateStatus")
    public R<?> updateStatus(@Validated(value = UpdateField.class) @RequestBody MemberForm form) {
        memberService.updateStatus(form);
        return R.ok();
    }
}
