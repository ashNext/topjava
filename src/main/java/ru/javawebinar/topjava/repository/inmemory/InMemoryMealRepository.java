package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> save(m, m.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int currentUserId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return get(meal.getId(), currentUserId) == null ? null : repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int currentUserId) {
        return (get(id, currentUserId) != null && repository.remove(id) != null);
    }

    @Override
    public Meal get(int id, int currentUserId) {
        Meal meal = repository.get(id);
        return (meal != null && meal.getUserId() == currentUserId ? meal : null);
    }

    @Override
    public Collection<Meal> getAll(int currentUserId) {
        return getMeals(null, null, currentUserId);
    }

    @Override
    public Collection<Meal> getMeals(LocalDate startDate, LocalDate endDate, int currentUserId) {
        return repository.values().stream()
                .filter(m -> m.getUserId() == currentUserId && DateTimeUtil.isBetweenHalfOpen(m.getDate(), startDate, endDate != null ? endDate.plusDays(1) : null))
                .sorted((a, b) -> b.getDateTime().compareTo(a.getDateTime()))
                .collect(Collectors.toList());
    }
}

