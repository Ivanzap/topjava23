package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService service;

    @Test
    public void get() {
        assertThrows(NotFoundException.class, () -> service.get(8, USER_ID));
    }

    @Test
    public void delete() {
        assertThrows(NotFoundException.class, () -> service.delete(8, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        LocalDate startDate = LocalDate.of(2021, Month.JUNE, 18);
        LocalDate endDate = LocalDate.of(2021, Month.JUNE, 19);
        List<Meal> inclusive = service.getBetweenInclusive(startDate, endDate, USER_ID);
        List<Meal> listMeal = mealsUser.stream()
                .filter(meal -> meal.getDate().compareTo(startDate) >= 0
                        && meal.getDate().compareTo(endDate) <= 0)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        assertMatch(inclusive, listMeal);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(ADMIN_ID);
        mealsAdmin.sort(Comparator.comparing(Meal::getDateTime).reversed());
        assertMatch(all, mealsAdmin);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(8, ADMIN_ID), getUpdated());
        assertThrows(NotFoundException.class, () -> service.get(8, USER_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(LocalDateTime.of(2021, Month.JUNE, 20, 8, 30), "Прием пищи", 800), USER_ID));
    }
}