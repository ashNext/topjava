package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void getWithMeals() throws Exception {
        User user = service.getWithMeals(USER_ID);
        USER_MATCHER.assertMatch(user, USER);
    }

}