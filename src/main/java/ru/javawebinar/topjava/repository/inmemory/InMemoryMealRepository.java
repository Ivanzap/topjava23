package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenHalfOpen;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        repository.put(counter.incrementAndGet(), new Meal(counter.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак user 1", 500, 1));
        repository.put(counter.incrementAndGet(), new Meal(counter.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед user 1", 1000, 1));
        repository.put(counter.incrementAndGet(), new Meal(counter.get(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин user 1", 600, 1));
        repository.put(counter.incrementAndGet(), new Meal(counter.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение user 1", 100, 1));
        repository.put(counter.incrementAndGet(), new Meal(counter.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак user 2", 1000, 2));
        repository.put(counter.incrementAndGet(), new Meal(counter.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед user 2", 500, 2));
        repository.put(counter.incrementAndGet(), new Meal(counter.get(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин user 2", 410, 2));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        } else if (repository.get(meal.getId()).getUserId() == userId) {
            meal.setUserId(userId);
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(int userId, int id) {
        if (repository.get(id).getUserId() == userId) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int userId, int id) {
        Meal meal = repository.get(id);
        if (meal.getUserId() == userId) {
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFilteredList(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId && isBetweenHalfOpen(meal.getDateTime(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

