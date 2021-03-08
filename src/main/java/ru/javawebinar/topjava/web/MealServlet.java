package ru.javawebinar.topjava.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.ext.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.repository.InMemoryUserMealRepository;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    UserMealRepository repository = new InMemoryUserMealRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        UserMeal um = new UserMeal(id.isEmpty() ? null :
                Integer.parseInt(id),
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.valueOf(req.getParameter("calories")));
        LOG.info(um.isNew() ? "Create {}" : "Update {}", um);
        repository.save(um);
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null){
            LOG.info("getAll");
            req.setAttribute("userMeals", MealsUtil.getWithExceeded(repository.getAll(), UserMealWithExceed.NORMA));
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        }else if (action.equals("delete")){
            int id = getId(req);
            LOG.info("Delete {}", id);
            if (repository.delete(id) == null){
                LOG.info("Meal {} was not deleted", id);
            }
            resp.sendRedirect("meals");
        }else {
            final UserMeal userMeal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now(), "Добавленный", 9999) :
                    repository.get(getId(req));
            LOG.info("Meal {}",userMeal.getCalories());
            req.setAttribute("meal", userMeal);
            req.getRequestDispatcher("mealEdit.jsp").forward(req, resp);
        }
    }

    private int getId(HttpServletRequest request){
        String stringId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(stringId);
    }
}
