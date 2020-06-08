package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;

public class MealDaoImpl implements MealDao {

    @Override
    public Meal add(Meal meal) {
        Long newId = MealsUtil.getCounter().getAndIncrement();
        meal.setId(newId);
        MealsUtil.getMealMap().put(newId, meal);
        return meal;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(MealsUtil.getMealMap().values());
    }

    @Override
    public Meal getById(Long id) {
        return MealsUtil.getMealMap().get(id);
    }

    @Override
    public Meal update(Meal meal) {
        return MealsUtil.getMealMap().computeIfPresent(meal.getId(), (k, v) -> v = meal);
    }

    @Override
    public void deleteById(Long id) {
        MealsUtil.getMealMap().remove(id);
    }
}
