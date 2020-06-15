package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(0, LocalDateTime.of(2020, Month.JUNE, 13, 10, 0), "Еда юзера 0", 500),
            new Meal(1, LocalDateTime.of(2020, Month.JUNE, 14, 10, 0), "Завтрак", 500),
            new Meal(1, LocalDateTime.of(2020, Month.JUNE, 14, 13, 0), "Обед", 1000),
            new Meal(1, LocalDateTime.of(2020, Month.JUNE, 14, 20, 0), "Ужин", 500),
            new Meal(1, LocalDateTime.of(2020, Month.JUNE, 15, 0, 0), "Еда на граничное значение", 100),
            new Meal(1, LocalDateTime.of(2020, Month.JUNE, 15, 10, 0), "Завтрак", 1000),
            new Meal(1, LocalDateTime.of(2020, Month.JUNE, 15, 13, 0), "Обед", 500),
            new Meal(1, LocalDateTime.of(2020, Month.JUNE, 15, 20, 0), "Ужин", 410),
            new Meal(2, LocalDateTime.of(2020, Month.JUNE, 14, 13, 0), "Обед's user 2", 500),
            new Meal(2, LocalDateTime.of(2020, Month.JUNE, 14, 20, 0), "Ужин's user 2", 410),
            new Meal(2, LocalDateTime.of(2020, Month.JUNE, 15, 13, 0), "Обед's user 2", 500),
            new Meal(2, LocalDateTime.of(2020, Month.JUNE, 15, 20, 0), "Ужин's user 2", 1510)
    );

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return filterByPredicate(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return filterByPredicate(meals, caloriesPerDay, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime));
    }

    public static List<MealTo> filterByPredicate(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
