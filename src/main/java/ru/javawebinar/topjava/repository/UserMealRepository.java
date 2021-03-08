package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.util.Collection;
import java.util.List;

public interface UserMealRepository {
    UserMeal save(UserMeal userMeal);

    UserMeal delete(int id);

    UserMeal get(int id);

    Collection<UserMeal> getAll();
}
