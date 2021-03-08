package ru.javawebinar.topjava;


import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.repository.InMemoryUserMealRepository;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {
    public static void main(String[] args) {
        UserMealRepository repository = new InMemoryUserMealRepository();
        Collection<UserMeal> list = repository.getAll();
        Collection<UserMealWithExceed> userMealWithExceeds = MealsUtil.getWithExceeded(list, 1900);
        print(repository.getAll());
        UserMeal removed = repository.delete(1);
        System.out.println(removed.getId());
        print(repository.getAll());
    }

    private static void print(Collection<UserMeal> userMeals){
        Collection<UserMealWithExceed> userMealWithExceeds = MealsUtil.getWithExceeded(userMeals, 1900);
        System.out.println("COUNT: " + userMealWithExceeds.size());
        for(UserMealWithExceed um : userMealWithExceeds){
            System.out.println(um.toString());
        }
        System.out.println();
    }
}
