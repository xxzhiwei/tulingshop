package com.aojiaodage.admin.entity;

import com.aojiaodage.common.validator.interfaces.Del;
import com.aojiaodage.common.validator.interfaces.Save;
import com.aojiaodage.common.validator.interfaces.Update;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 属性参考表(PmsAttr)实体类
 *
 * @author makejava
 * @since 2022-10-20 10:12:02
 */
@Getter
@Setter
public class PmsAttr implements Serializable {
    private static final long serialVersionUID = 421275058823139466L;

    @NotNull(message = "id不能为空", groups = {Del.class, Update.class})
    private Integer id;

    @NotNull(message = "name不能为空", groups = {Save.class, Update.class})
    @TableField(value = "`name`")
    private String name;

    @TableField(value = "`value`")
    private String value;
    /**
     * 1=单选，2=多选
     */
    @NotNull(message = "type不能为空", groups = {Save.class, Update.class})
    @TableField(value = "`type`")
    private Integer type;

    @NotNull(message = "categoryId不能为空", groups = {Save.class, Update.class})
    private Integer categoryId;

    private String categoryName;

    // type2一旦定义下来后，不能修改，否则会影响到其他已存在的记录（sku、spu）
    private Integer type2;

    // 若为规格属性，则该属性为必填
    private Integer groupId;

}

