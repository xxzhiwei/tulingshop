package com.aojiaodage.common.util;

import com.aojiaodage.common.interfaces.FieldConverter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

// Object -> Map转换工具
public class ObjectToMapConverter {
    public static Map<String, Object> convert(Object object) {
        return convert(object, null);
    }

    private static String getFieldName(String field, FieldConverter fieldConverter) {
        return fieldConverter != null ? fieldConverter.convert(field) : field;
    }

    public static Map<String, Object> convert(Object object, FieldConverter fieldConverter) {
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = object.getClass().getDeclaredFields();

        try {
            for (Field field : declaredFields) {
                field.setAccessible(true);
                map.put(getFieldName(field.getName(), fieldConverter), field.get(object));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
