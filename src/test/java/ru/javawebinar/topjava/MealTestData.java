package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final List<Meal> mealsUser = Arrays.asList(
            new Meal(100002, LocalDateTime.of(2021, Month.JUNE, 19, 9, 0), "Завтрак user", 500),
            new Meal(100003, LocalDateTime.of(2021, Month.JUNE, 19, 12, 30), "Обед user", 1100),
            new Meal(100004, LocalDateTime.of(2021, Month.JUNE, 19, 18, 30), "Ужин user", 700),
            new Meal(100005, LocalDateTime.of(2021, Month.JUNE, 20, 0, 0), "Еда в непонятное время user", 400),
            new Meal(100006, LocalDateTime.of(2021, Month.JUNE, 20, 13, 0), "Обед user", 1000),
            new Meal(100007, LocalDateTime.of(2021, Month.JUNE, 20, 18, 0), "Ужин user", 450)
    );
    public static final List<Meal> mealsAdmin = Arrays.asList(
            new Meal(100008, LocalDateTime.of(2021, Month.JUNE, 19, 7, 30), "Завтрак admin", 600),
            new Meal(100009, LocalDateTime.of(2021, Month.JUNE, 19, 12, 0), "Обед admin", 1200),
            new Meal(100010, LocalDateTime.of(2021, Month.JUNE, 19, 19, 0), "Ужин admin", 400)
    );

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, Month.JUNE, 21, 18, 0), "some description", 500);
    }

    public static Meal getUpdated() {
        Meal updated = mealsAdmin.get(1);
        updated.setDateTime(LocalDateTime.of(2021, Month.JUNE, 21, 12, 30));
        updated.setDescription("прием пищи");
        updated.setCalories(760);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
