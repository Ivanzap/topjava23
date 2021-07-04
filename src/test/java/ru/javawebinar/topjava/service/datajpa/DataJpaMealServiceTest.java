package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.service.MealServiceTest;

@RunWith(SpringRunner.class)
@ActiveProfiles("datajpa")
public class DataJpaMealServiceTest extends MealServiceTest {
    @Override
    @Test
    public void delete() {
        super.delete();
    }

    @Override
    @Test
    public void deleteNotFound() {
        super.deleteNotFound();
    }

    @Override
    @Test
    public void deleteNotOwn() {
        super.deleteNotOwn();
    }

    @Override
    @Test
    public void create() {
        super.create();
    }

    @Override
    @Test
    public void duplicateDateTimeCreate() {
        super.duplicateDateTimeCreate();
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
    public void getNotOwn() {
        super.getNotOwn();
    }

    @Override
    @Test
    public void update() {
        super.update();
    }

    @Override
    @Test
    public void updateNotOwn() {
        super.updateNotOwn();
    }

    @Override
    @Test
    public void getAll() {
        super.getAll();
    }

    @Override
    @Test
    public void getBetweenInclusive() {
        super.getBetweenInclusive();
    }

    @Override
    @Test
    public void getBetweenWithNullDates() {
        super.getBetweenWithNullDates();
    }
}
