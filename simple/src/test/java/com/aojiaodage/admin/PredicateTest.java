package com.aojiaodage.admin;

import com.aojiaodage.admin.entity.PmsProductCategory;
import com.aojiaodage.admin.service.PmsProductCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.function.Predicate;

@SpringBootTest
public class PredicateTest {

    @Autowired
    PmsProductCategoryService pmsProductCategoryService;

    @Test
    public void test1() {
        List<PmsProductCategory> list = pmsProductCategoryService.getList();
        Predicate<PmsProductCategory> rootPredicate = item -> {
            return item.getParentId().equals(0L);
        };

        PmsProductCategory pmsProductCategory = list.get(0);
        boolean test = rootPredicate.test(pmsProductCategory);
        System.out.println(test);
    }
}
