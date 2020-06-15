package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int currentUserId) {
        return repository.save(meal, currentUserId);
    }

    public void delete(int id, int currentUserId) {
        checkNotFoundWithId(repository.delete(id, currentUserId), id);
    }

    public Meal get(int id, int currentUserId) {
        return checkNotFoundWithId(repository.get(id, currentUserId), id);
    }

    public Collection<Meal> getAll(int currentUserId) {
        return repository.getAll(currentUserId);
    }

    public Collection<Meal> getMeals(LocalDate startDate, LocalDate endDate, int currentUserId) {
        return repository.getMeals(startDate, endDate, currentUserId);
    }

    public void update(Meal meal, int currentUserId) {
        checkNotFoundWithId(repository.save(meal, currentUserId), meal.getId());
    }
}