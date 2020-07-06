package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {
    @Autowired
    private MealService service;

    @Test
    public void getWithUser() throws Exception {
        Meal actual = service.getWithUser(ADMIN_MEAL_ID + 1, ADMIN_ID);
        MEAL_MATCHER.assertMatchFieldByField(actual, ADMIN_MEAL2);
    }
}