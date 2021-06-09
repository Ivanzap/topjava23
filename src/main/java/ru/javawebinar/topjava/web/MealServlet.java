package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final String MEALS = "/meals.jsp";
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final int CALORIES_PER_DAY = 2000;
    private List<MealTo> mealsTo;
    private MealDao dao;

    @Override
    public void init() throws ServletException {
        this.mealsTo = Collections.synchronizedList(new ArrayList<>());
        this.dao = new MealDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String forward="";

        String action = request.getParameter("action");

        if(action.equalsIgnoreCase("meals")) {
            forward = MEALS;
            mealsTo = filteredByStreams(dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            request.setAttribute("mealTo", mealsTo);
        } else if(action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deleteMeal(id);
            forward = MEALS;
            mealsTo = filteredByStreams(dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            request.setAttribute("mealTo", mealsTo);
        } else if(action.equalsIgnoreCase("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            forward = INSERT_OR_EDIT;
            Meal meal = dao.getMealById(id);
            request.setAttribute("meal", meal);
        } else {
            forward = INSERT_OR_EDIT;
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal();
        meal.setDateTime(LocalDateTime.parse(request.getParameter("dateTime")));
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");
        if(id == null || id.isEmpty()) {
            dao.addMeal(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            dao.updateMeal(meal);
        }

        mealsTo = filteredByStreams(dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
        request.setAttribute("mealTo", mealsTo);
        request.getRequestDispatcher(MEALS).forward(request, response);
    }
}
