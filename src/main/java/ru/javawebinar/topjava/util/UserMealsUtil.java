package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 1), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(8, 0), LocalTime.of(21, 0), 700);
        mealsTo.forEach(System.out::println);

        System.out.println();

        List<UserMealWithExcess> mealsTo2 = filteredByStreams(meals, LocalTime.of(8, 0), LocalTime.of(21, 0), 700);
        mealsTo2.forEach(System.out::println);

        System.out.println();

        List<UserMealWithExcess> mealsTo3 = filteredByStreamsWithCreateCollector(meals, LocalTime.of(8, 0), LocalTime.of(21, 0), 700);
        mealsTo3.forEach(System.out::println);

        System.out.println();
        System.out.println(getTime(meals));

    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> filteredMealWithExcesses = new ArrayList<>();

        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                filteredMealWithExcesses.add(new UserMealWithExcess(meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        meal.getCalories() > caloriesPerDay));
            }
        }

        filteredMealWithExcesses.sort(Comparator.comparing(UserMealWithExcess::getDateTime));

        return filteredMealWithExcesses;

    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        meal.getCalories() > caloriesPerDay))
                .sorted(Comparator.comparing(UserMealWithExcess::getDateTime))
                .collect(Collectors.toList());

    }

    public static List<UserMealWithExcess> filteredByStreamsWithCreateCollector(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        return meals.stream().collect(new UserMealWithExcessCollector(startTime, endTime, caloriesPerDay));

    }

    private static double getTime(List<UserMeal> meals) {
        for (int i = 0; i < 20; i ++) { //прогрев JVM
            filteredByCycles(meals, LocalTime.of(8, 0), LocalTime.of(21, 0), 700);
        }
        int count = 1000; //первоначальное кол-во повтора выполнения testMethod

        while(true) {
            long begin =  System.nanoTime();

            for (int i = 0; i < count; i ++)
                filteredByCycles(meals, LocalTime.of(8, 0), LocalTime.of(21, 0), 700);

            long end = System.nanoTime();

            if ((end - begin) < 1000000000) { //Прогон тестов пока суммарное выполнения count раз
                count *= 100000;              //testMethod`a не будет равно несколько секунд
                continue;
            }

            return (double)(end - begin) / count;
        }
    }
}
