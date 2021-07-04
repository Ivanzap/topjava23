package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.UserTestData.*;

public class UserServiceTest extends ServiceTest {

    @Autowired
    private UserService service;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setup() {
        cacheManager.getCache("users").clear();
    }

    public void create() {
        User created = service.create(getNew());
        int newId = created.id();
        User newUser = getNew();
        newUser.setId(newId);
        MATCHER.assertMatch(created, newUser);
        MATCHER.assertMatch(service.get(newId), newUser);
    }

    public void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "Duplicate", "user@yandex.ru", "newPass", Role.USER)));
    }

    public void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    public void get() {
        User user = service.get(USER_ID);
        MATCHER.assertMatch(user, UserTestData.user);
    }

    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    public void getByEmail() {
        User user = service.getByEmail("admin@gmail.com");
        MATCHER.assertMatch(user, admin);
    }

    public void update() {
        User updated = getUpdated();
        service.update(updated);
        MATCHER.assertMatch(service.get(USER_ID), getUpdated());
    }

    public void getAll() {
        List<User> all = service.getAll();
        MATCHER.assertMatch(all, admin, user);
    }
}