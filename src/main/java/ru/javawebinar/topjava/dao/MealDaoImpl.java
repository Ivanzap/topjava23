package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MealDaoImpl implements MealDao {
    private static int MEAL_ID;

    List<Meal> meals;

    {
        meals = Collections.synchronizedList(new ArrayList<>());
        meals.add(new Meal(++MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(++MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(++MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(++MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(++MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(++MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(++MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    @Override
    public void deleteMeal(int id) {
        meals.remove(getMealById(id));
    }

    @Override
    public void updateMeal(Meal meal) {
        int id = meal.getId();
        Meal newMeal = getMealById(id);
        newMeal.setDateTime(meal.getDateTime());
        newMeal.setDescription(meal.getDescription());
        newMeal.setCalories(meal.getCalories());
        int index = meals.indexOf(getMealById(id));
        meals.set(index, newMeal);
    }

    @Override
    public List<Meal> getAllMeals() {
        return meals;
    }

    @Override
    public Meal getMealById(int id) {
        for (Meal meal : meals) {
            if(meal.getId() == id)
                return meal;
        }
        return null;
    }
}
