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
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static String MEALS = "/meals.jsp";
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static final int CALORIES_PER_DAY = 2000;

    private MealDao dao;

    @Override
    public void init() throws ServletException {
        this.dao = new MealDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String forward="";
        List<MealTo> mealsTo;
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
}
