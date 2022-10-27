package com.aojiaodage.portal.util;

import com.aojiaodage.portal.entity.Member;

public class PayloadUtil {
    private static final ThreadLocal<Member> threadLocal = new ThreadLocal<>();

    public static Member get() {
        return threadLocal.get();
    }

    public static void set(Member member) {
        threadLocal.set(member);
    }

    public static void remove() {
        threadLocal.remove();
    }
}
