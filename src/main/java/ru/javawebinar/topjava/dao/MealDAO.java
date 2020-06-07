package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {
    void add(Meal meal);
    List<Meal> getAll();
    Meal getById(Long id);
    void deleteById(Long id);
}
