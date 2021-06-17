package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.getFilteredTos;
import static ru.javawebinar.topjava.util.MealsUtil.getTos;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        checkNew(meal);
        log.info("create {}", meal);
        return service.create(authUserId(), meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(authUserId(), id);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(authUserId(), id);
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        List<Meal> meals = service.getAll(authUserId());
        return getTos(meals, authUserCaloriesPerDay());
    }

    public List<MealTo> getFilteredList(LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        fromDate = (fromDate != null) ? fromDate : LocalDate.MIN;
        toDate = (toDate != null) ? toDate : LocalDate.MAX;
        fromTime = (fromTime != null) ? fromTime : LocalTime.MIN;
        toTime = (toTime != null) ? toTime : LocalTime.MAX;
        log.info("getAll with starDate: {}, endDate: {}, startTime: {}, endTime: {}", fromDate, toDate, fromTime, toTime);
        List<Meal> meals = service.getFilteredList(authUserId(), fromDate, toDate);
        return getFilteredTos(meals, authUserCaloriesPerDay(), fromTime, toTime);
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        log.info("update {} with id {}", meal, id);
        service.update(authUserId(), meal);
    }
}