package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMemory implements MealDao {
    private final AtomicInteger count = new AtomicInteger(0);

    private final List<Meal> meals;

    {
        meals = Collections.synchronizedList(new ArrayList<>());
        meals.add(new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public Meal add(Meal meal) {
        Meal newMeal = new Meal(count.incrementAndGet(),
                meal.getDateTime(),
                meal.getDescription(),
                meal.getCalories());
        meals.add(newMeal);
        return newMeal;
    }

    @Override
    public void delete(int id) {
        meals.remove(getById(id));
    }

    @Override
    public synchronized Meal update(Meal meal) {
        int id = meal.getId();
        int index = meals.indexOf(getById(id));
        meals.set(index, meal);
        return meal;
    }

    @Override
    public List<Meal> getAll() {
        return getMeals();
    }

    @Override
    public Meal getById(int id) {
        for (Meal meal : meals) {
            if(meal.getId() == id)
                return meal;
        }
        return null;
    }

    public List<Meal> getMeals() {
        return meals;
    }
}
