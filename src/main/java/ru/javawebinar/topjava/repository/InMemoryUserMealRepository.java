package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryUserMealRepository implements UserMealRepository{
    private Map <Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new UserMeal(LocalDateTime.of(2021, Month.APRIL, 6, 7, 0), "Завтрак", 500));
        save(new UserMeal(LocalDateTime.of(2021, Month.APRIL, 6, 12, 0), "Обед", 1000));
        save(new UserMeal(LocalDateTime.of(2021, Month.APRIL, 6, 18, 0), "Ужин", 1000));
        save(new UserMeal(LocalDateTime.of(2021, Month.APRIL, 7, 7, 0), "Завтрак", 500));
        save(new UserMeal(LocalDateTime.of(2021, Month.APRIL, 7, 12, 0), "Обед", 600));
        save(new UserMeal(LocalDateTime.of(2021, Month.APRIL, 7, 18, 0), "Ужин", 700));
    }
    @Override
    public UserMeal save(UserMeal userMeal) {
        if (userMeal.isNew()){
            userMeal.setId(counter.incrementAndGet());
        }
        return repository.put(userMeal.getId(), userMeal);
    }

    @Override
    public UserMeal delete(int id) {
        return repository.remove(id);
    }

    @Override
    public UserMeal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<UserMeal> getAll() {
        return repository.values();
    }

}
