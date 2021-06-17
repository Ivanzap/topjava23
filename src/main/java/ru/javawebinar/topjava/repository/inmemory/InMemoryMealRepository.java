package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenHalfOpen;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByList;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        this.save(1, MealsUtil.meals.get(0));
        this.save(1, MealsUtil.meals.get(1));
        this.save(1, MealsUtil.meals.get(2));
        this.save(1, MealsUtil.meals.get(3));
        this.save(2, MealsUtil.meals.get(4));
        this.save(2, MealsUtil.meals.get(5));
        this.save(2, MealsUtil.meals.get(6));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            if (!repository.containsKey(userId)) {
                repository.put(userId, new ConcurrentHashMap<>());
            }
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        } else {
            return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
    }

    @Override
    public boolean delete(int userId, int id) {
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        return repository.get(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filteredByList(repository, userId, meal -> true);
    }

    @Override
    public List<Meal> getFilteredList(int userId, LocalDate startDate, LocalDate endDate) {
        return filteredByList(repository, userId, meal -> isBetweenHalfOpen(meal.getDateTime(), startDate, endDate));
    }
}

