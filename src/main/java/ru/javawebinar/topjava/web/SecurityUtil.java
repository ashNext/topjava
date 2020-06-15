package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    private static int authUser=1;

    public static void setAuthUserId(int authUser) {
        SecurityUtil.authUser = authUser;
    }

    public static int authUserId() {
        return authUser;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}