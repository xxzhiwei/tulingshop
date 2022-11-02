package com.aojiaodage.portal.vo;

import com.aojiaodage.portal.entity.PmsAttr;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AttrGroup {
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
