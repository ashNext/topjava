package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    Meal add(Meal meal);
    List<Meal> getAll();
    Meal getById(Long id);
    Meal update(Meal meal);
    void deleteById(Long id);
}
