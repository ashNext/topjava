package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    private static int mealId = START_SEQ + 2;

    public static final Meal MEAL_NOT_IN_DB = new Meal(-1, LocalDateTime.of(2020, Month.JUNE, 1, 0, 0, 0), "Еда не в базе", 0);

    public static final Meal MEAL_01_U = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 20, 7, 40, 0), "Завтрак", 500);
    public static final Meal MEAL_02_U = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 20, 13, 0, 0), "Обед", 1000);
    public static final Meal MEAL_03_U = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 20, 19, 0, 0), "Ужин", 500);
    public static final Meal MEAL_04_U = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 21, 7, 40, 0), "Завтрак", 300);
    public static final Meal MEAL_05_U = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 21, 12, 0, 0), "Ланч", 100);
    public static final Meal MEAL_06_U = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 21, 14, 0, 0), "Обед", 1000);
    public static final Meal MEAL_07_U = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 21, 19, 30, 0), "Ужин", 700);
    public static final Meal MEAL_08_U = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 22, 8, 40, 0), "Завтрак", 700);
    public static final Meal MEAL_09_U = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 22, 14, 0, 0), "Обед", 700);
    public static final Meal MEAL_10_U = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 22, 20, 0, 0), "Ужин", 500);

    public static final List<Meal> MEALS_LIST_ALL_U = Arrays.asList(MEAL_10_U, MEAL_09_U, MEAL_08_U, MEAL_07_U, MEAL_06_U, MEAL_05_U, MEAL_04_U, MEAL_03_U, MEAL_02_U, MEAL_01_U);

    public static final Meal MEAL_01_A = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 20, 7, 40, 0), "Завтрак", 600);
    public static final Meal MEAL_02_A = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 20, 12, 0, 0), "Ланч", 100);
    public static final Meal MEAL_03_A = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 20, 13, 0, 0), "Обед", 700);
    public static final Meal MEAL_04_A = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 20, 19, 0, 0), "Ужин", 700);
    public static final Meal MEAL_05_A = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 21, 7, 40, 0), "Завтрак", 900);
    public static final Meal MEAL_06_A = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 21, 14, 0, 0), "Обед", 800);
    public static final Meal MEAL_07_A = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 21, 19, 30, 0), "Ужин", 200);
    public static final Meal MEAL_08_A = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 22, 8, 40, 0), "Завтрак", 700);
    public static final Meal MEAL_09_A = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 22, 12, 0, 0), "Ланч", 100);
    public static final Meal MEAL_10_A = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 22, 14, 0, 0), "Обед", 900);
    public static final Meal MEAL_11_A = new Meal(mealId++, LocalDateTime.of(2020, Month.JUNE, 22, 20, 0, 0), "Ужин", 400);

    public static final LocalDate START_DATE_BETWEEN_U = LocalDate.of(2020, Month.JUNE, 21);
    public static final LocalDate END_DATE_BETWEEN_U = LocalDate.of(2020, Month.JUNE, 22);
    public static final List<Meal> MEALS_LIST_BETWEEN_DATE_U = Arrays.asList(MEAL_10_U, MEAL_09_U, MEAL_08_U, MEAL_07_U, MEAL_06_U, MEAL_05_U, MEAL_04_U);

    public static final LocalDate FROM_DATE_BETWEEN_A = LocalDate.of(2020, Month.JUNE, 21);
    public static final List<Meal> MEALS_LIST_FROM_DATE_A = Arrays.asList(MEAL_11_A, MEAL_10_A, MEAL_09_A, MEAL_08_A, MEAL_07_A, MEAL_06_A, MEAL_05_A);

    public static final LocalDate TO_DATE_BETWEEN_A = LocalDate.of(2020, Month.JUNE, 21);
    public static final List<Meal> MEALS_LIST_TO_DATE_A = Arrays.asList(MEAL_07_A, MEAL_06_A, MEAL_05_A, MEAL_04_A, MEAL_03_A, MEAL_02_A, MEAL_01_A);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JUNE, 22, 22, 22, 22), "Новая еда (2)", 222);
    }

    public static Meal getUpdated(Meal meal) {
        Meal updated = new Meal(meal);
        updated.setDescription("UpdatedMeal");
        updated.setCalories(666);
        updated.setDateTime(LocalDateTime.of(2020, Month.JUNE, 11, 11, 11, 11));
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

}
