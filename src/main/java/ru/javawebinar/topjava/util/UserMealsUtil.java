package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> result = new ArrayList<>();
        Map<LocalDate, Integer> caloriesByDay = new HashMap<>();
        LocalDate ld = null;
        LocalTime lt = null;
        LocalDateTime ldt = null;
        for(UserMeal userMeal : meals){
            ld = userMeal.getDateTime().toLocalDate();
            if (!caloriesByDay.containsKey(ld))
                caloriesByDay.put(ld, userMeal.getCalories());
            else
                caloriesByDay.put(ld, caloriesByDay.get(ld) + userMeal.getCalories());
        }
        for (UserMeal userMeal : meals){
            ld = userMeal.getDateTime().toLocalDate();
            lt = userMeal.getDateTime().toLocalTime();
            ldt = userMeal.getDateTime();
            Integer calory = caloriesByDay.get(ld);
            if (lt.isAfter(startTime) && lt.isBefore(endTime))
                result.add(new UserMealWithExcess(ldt, userMeal.getDescription(), userMeal.getCalories(), calory > caloriesPerDay));
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesByDay = meals.stream()
                .collect(groupingBy(um -> um.getDateTime().toLocalDate(),
                        summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(um -> TimeUtil.isBetweenHalfOpen(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> createUserExeeded(um, caloriesByDay.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static UserMealWithExcess createUserExeeded(UserMeal um, boolean excess){
        return new UserMealWithExcess(um.getDateTime(), um.getDescription(), um.getCalories(), excess);
    }
}
