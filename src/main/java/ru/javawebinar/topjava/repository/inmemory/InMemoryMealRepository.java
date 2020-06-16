package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, HashMap<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach((uId, meals) -> meals.forEach(meal -> save(meal, uId)));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.putIfAbsent(userId, new HashMap<>());
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return get(meal.getId(), userId) == null ? null : repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return (get(id, userId) != null && repository.get(userId).remove(id) != null);
    }

    @Override
    public Meal get(int id, int userId) {
        HashMap<Integer, Meal> mealsUser = repository.get(userId);
        return (mealsUser != null && mealsUser.get(id) != null ? mealsUser.get(id) : null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFilteredByPredicate(userId, m -> true);
    }

    @Override
    public List<Meal> getFilteredBetweenDate(LocalDate startDate, LocalDate endDate, int userId) {
        return getFilteredByPredicate(userId, m -> DateTimeUtil.isBetweenHalfOpen(m.getDate(), startDate, endDate != null ? endDate.plusDays(1) : null));
    }

    private List<Meal> getFilteredByPredicate(int userId, Predicate<Meal> filter) {
        //если у пользователя еда ещё ниразу не была добавлена, то инициализируем ему пустую мапу
        repository.putIfAbsent(userId, new HashMap<>());
        return repository.get(userId).values().stream()
                .filter(filter)
                .sorted((a, b) -> b.getDateTime().compareTo(a.getDateTime()))
                .collect(Collectors.toList());
    }
}

