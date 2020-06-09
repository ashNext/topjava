package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealDaoImplInternalStorage implements MealDao {

    private static final Map<Long, Meal> mealMap = new ConcurrentHashMap<>();

    private static AtomicLong counter = new AtomicLong();

    static {
        for (Meal meal : preparedList()) {
            meal.setId(counter.getAndIncrement());
            mealMap.put(meal.getId(), meal);
        }
    }

    private static List<Meal> preparedList() {
        return Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
                new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 7, 0), "Завтрак", 800),
                new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 16, 0), "Обед", 900),
                new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 21, 0), "Ужин", 250),
                new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 12, 0), "Ланч", 2222)
        );
    }

    @Override
    public Meal add(Meal meal) {
        Long newId = counter.getAndIncrement();
        meal.setId(newId);
        return mealMap.putIfAbsent(newId, meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public Meal getById(Long id) {
        return mealMap.get(id);
    }

    @Override
    public Meal update(Meal meal) {
        return mealMap.computeIfPresent(meal.getId(), (k, v) -> meal);
    }

    @Override
    public void deleteById(Long id) {
        mealMap.remove(id);
    }
}
