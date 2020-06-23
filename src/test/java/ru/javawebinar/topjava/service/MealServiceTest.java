package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private MealRepository repository;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_04_A.getId(), ADMIN_ID);
        assertMatch(meal, MEAL_04_A);
    }

    @Test
    public void getNotInDb() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_NOT_IN_DB.getId(), USER_ID));
    }

    @Test
    public void getAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_11_A.getId(), USER_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_08_U.getId(), USER_ID);
        assertNull(repository.get(MEAL_08_U.getId(), USER_ID));
    }

    @Test
    public void deleteNotInDb() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_NOT_IN_DB.getId(), USER_ID));
    }

    @Test
    public void deletedAlienMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_07_A.getId(), USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> between = service.getBetweenInclusive(START_DATE_BETWEEN_U, END_DATE_BETWEEN_U, USER_ID);
        assertMatch(between, MEALS_LIST_BETWEEN_DATE_U);
    }

    @Test
    public void getBetweenInclusiveFrom() {
        List<Meal> between = service.getBetweenInclusive(FROM_DATE_BETWEEN_A, null, ADMIN_ID);
        assertMatch(between, MEALS_LIST_FROM_DATE_A);
    }

    @Test
    public void getBetweenInclusiveTo() {
        List<Meal> between = service.getBetweenInclusive(null, TO_DATE_BETWEEN_A, ADMIN_ID);
        assertMatch(between, MEALS_LIST_TO_DATE_A);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEALS_LIST_ALL_U);
    }

    @Test
    public void getAllForUserWithEmptyMeals() {
        List<Meal> all = service.getAll(-1);
        assertThat(all).isEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    public void update() {
        Meal updated = getUpdated(MEAL_01_A);
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(MEAL_01_A.getId(), ADMIN_ID), updated);
    }

    @Test
    public void updateNotInDb() {
        Meal updated = getUpdated(MEAL_NOT_IN_DB);
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));

    }

    @Test
    public void updateAlienMeal() {
        Meal updated = getUpdated(MEAL_01_A);
        assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID));

    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }
}