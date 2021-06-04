package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 1), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(8, 0), LocalTime.of(21, 0), 2009);
        mealsTo.forEach(System.out::println);

        System.out.println();

        List<UserMealWithExcess> mealsTo2 = filteredByStreams(meals, LocalTime.of(8, 0), LocalTime.of(21, 0), 2009);
        mealsTo2.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> allCaloriesByDays = new HashMap<>();
        meals.forEach(meal -> allCaloriesByDays.merge(meal.getDateTime().toLocalDate(),
                meal.getCalories(),
                Integer::sum));

        List<UserMealWithExcess> mealTo = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealTo.add(new UserMealWithExcess(meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        allCaloriesByDays.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        });

        return mealTo;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> allCaloriesOfDay = meals.stream()
                .collect(Collectors.toMap(
                        meal -> meal.getDateTime().toLocalDate(),
                        UserMeal::getCalories,
                        Integer::sum));

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        allCaloriesOfDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
