package com.aojiaodage.admin.util;

import com.aojiaodage.admin.entity.User;

public class PayloadUtil {
    private static final ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static User get() {
        return threadLocal.get();
    }

    public static void set(User user) {
        threadLocal.set(user);
    }

    public static void remove() {
        threadLocal.remove();
    }
}
