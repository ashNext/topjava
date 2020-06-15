package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealRepository {
    // null if not found, when updated
    Meal save(Meal meal, int currentUserId);

    // false if not found
    boolean delete(int id, int currentUserId);

    // null if not found
    Meal get(int id, int currentUserId);

    Collection<Meal> getAll(int currentUserId);

    Collection<Meal> getMeals(LocalDate startDate, LocalDate endDate, int currentUserId);
}
