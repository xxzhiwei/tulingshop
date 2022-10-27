package com.aojiaodage.admin.vo;

import com.aojiaodage.admin.entity.PmsAttr;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductAttrGroupWithAttr {
    private Integer id;
    private String name;
    private List<PmsAttr> attrs;

    @Override
    public String toString() {
        return "ProductAttrGroupWithAttr{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", attrs count=" + attrs.size() +
                '}';
    }
}
