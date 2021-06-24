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
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private static final int ID_MEAL_ADMIN = 100009;
    private static final Meal MEAL_USER_ID_100002 = mealsUser.get(0);
    private static final Meal MEAL_USER_ID_100003 = mealsUser.get(1);
    private static final Meal MEAL_USER_ID_100004 = mealsUser.get(2);
    private static final Meal MEAL_ADMIN_ID_100008 = mealsAdmin.get(0);
    private static final Meal MEAL_ADMIN_ID_100009 = mealsAdmin.get(1);
    private static final Meal MEAL_ADMIN_ID_100010 = mealsAdmin.get(2);

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(ID_MEAL_ADMIN+1, ADMIN_ID);
        assertMatch(meal, MEAL_ADMIN_ID_100010);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(ID_MEAL_ADMIN, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(ID_MEAL_ADMIN, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(ID_MEAL_ADMIN, ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(ID_MEAL_ADMIN, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        LocalDate startDate = LocalDate.of(2021, Month.JUNE, 18);
        LocalDate endDate = LocalDate.of(2021, Month.JUNE, 19);
        List<Meal> inclusive = service.getBetweenInclusive(startDate, endDate, USER_ID);
        assertMatch(inclusive, MEAL_USER_ID_100004, MEAL_USER_ID_100003, MEAL_USER_ID_100002);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, MEAL_ADMIN_ID_100010, MEAL_ADMIN_ID_100009, MEAL_ADMIN_ID_100008);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(ID_MEAL_ADMIN, ADMIN_ID), getUpdated());
        assertThrows(NotFoundException.class, () -> service.get(ID_MEAL_ADMIN, USER_ID));
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
                service.create(new Meal(MEAL_USER_ID_100002.getDateTime(), "Прием пищи", 800), USER_ID));
    }
}