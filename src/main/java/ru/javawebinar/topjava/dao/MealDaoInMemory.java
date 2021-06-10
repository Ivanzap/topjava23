package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMemory implements MealDao {
    private final AtomicInteger count = new AtomicInteger(0);

    private final Map<Integer, Meal> mapMeals;

    {
        mapMeals = Collections.synchronizedMap(new HashMap<>());
        mapMeals.put(count.incrementAndGet(), new Meal(count.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        mapMeals.put(count.incrementAndGet(), new Meal(count.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        mapMeals.put(count.incrementAndGet(), new Meal(count.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        mapMeals.put(count.incrementAndGet(), new Meal(count.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        mapMeals.put(count.incrementAndGet(), new Meal(count.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        mapMeals.put(count.incrementAndGet(), new Meal(count.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        mapMeals.put(count.incrementAndGet(), new Meal(count.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public Meal add(Meal meal) {
        Meal newMeal = new Meal(count.incrementAndGet(),
                meal.getDateTime(),
                meal.getDescription(),
                meal.getCalories());
        mapMeals.put(count.get(), newMeal);
        return newMeal;
    }

    @Override
    public void delete(int id) {
        mapMeals.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        mapMeals.replace(meal.getId(), meal);
        return meal;
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mapMeals.values());
    }

    @Override
    public Meal getById(int id) {
        return mapMeals.get(id);
    }
}
