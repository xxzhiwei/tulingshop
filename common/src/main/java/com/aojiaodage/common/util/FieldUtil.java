package com.aojiaodage.common.util;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldUtil {
    static Pattern underlineCompile = Pattern.compile("_[a-z]");
    static Pattern camelCompile = Pattern.compile("[A-Z]");

    public static String camel(String input) {
        Matcher matcher = underlineCompile.matcher(input);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String replacement = matcher.group(0).toUpperCase(Locale.ROOT).replace("_", "");
            matcher.appendReplacement(sb, replacement);
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String underline(String input) {
        Matcher matcher = camelCompile.matcher(input);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String replacement = "_" + matcher.group(0).toLowerCase(Locale.ROOT);
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        String appId = FieldUtil.camel("app_id");
        System.out.println(appId);
        String appId1 = FieldUtil.underline("appId");
        System.out.println(appId1);
    }
}
