package ru.javawebinar.topjava.service.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.service.UserServiceTest;

@RunWith(SpringRunner.class)
@ActiveProfiles("jpa")
public class JpaUserServiceTest extends UserServiceTest {
    @Override
    @Test
    public void setup() {
        super.setup();
    }

    @Override
    @Test
    public void create() {
        super.create();
    }

    @Override
    @Test
    public void duplicateMailCreate() {
        super.duplicateMailCreate();
    }

    @Override
    @Test
    public void delete() {
        super.delete();
    }

    @Override
    @Test
    public void deletedNotFound() {
        super.deletedNotFound();
    }

    @Override
    @Test
    public void get() {
        super.get();
    }

    @Override
    @Test
    public void getNotFound() {
        super.getNotFound();
    }

    @Override
    @Test
    public void getByEmail() {
        super.getByEmail();
    }

    @Override
    @Test
    public void update() {
        super.update();
    }

    @Override
    @Test
    public void getAll() {
        super.getAll();
    }
}
