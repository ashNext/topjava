package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MealDAOImpl implements MealDAO {
    private static class Holder {
        private static MealDAOImpl instance = new MealDAOImpl();
    }

    private List<Meal> meals;

    private MealDAOImpl() {
        meals = new ArrayList<>();
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        add(new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 7, 0), "Завтрак", 800));
        add(new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 16, 0), "Обед", 900));
        add(new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 21, 0), "Ужин", 250));
    }

    public static MealDAOImpl getInstance() {
        return Holder.instance;
    }

    @Override
    public void add(Meal meal) {
        if (meal.getId() == null) {
            if (meals.isEmpty())
                meal.setId(0L);
            else
                meal.setId(
                        meals.stream()
                                .max(Comparator.comparing(Meal::getId))
                                .get()
                                .getId() + 1
                );
        }
        meals.add(meal);
    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }

    @Override
    public Meal getById(Long id) {
        return meals.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        meals.remove(getById(id));
    }
}
