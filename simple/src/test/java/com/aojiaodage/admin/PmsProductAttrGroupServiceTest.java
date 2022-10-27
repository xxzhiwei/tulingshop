package com.aojiaodage.admin;

import com.aojiaodage.admin.service.PmsProductAttrGroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PmsProductAttrGroupServiceTest {

    @Autowired
    PmsProductAttrGroupService pmsProductAttrGroupService;

    @Test
    public void test1() {
        // List<ProductAttrGroupWithAttr> productAttrGroupList = pmsProductAttrGroupService.getList(1);
        // productAttrGroupList.forEach(System.out::println);
    }
}
