package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private MealDao dao;

    @Override
    public void init() throws ServletException {
        this.dao = new MealDaoInMemory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet MealServlet");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        int id;
        List<MealTo> mealsTo;
        if (action != null) {
            switch (action) {
                case "delete":
                    id = Integer.parseInt(request.getParameter("id"));
                    dao.delete(id);
                    response.sendRedirect("/topjava/meals");
                    break;
                case "edit":
                    id = Integer.parseInt(request.getParameter("id"));
                    Meal meal = dao.getById(id);
                    request.setAttribute("meal", meal);
                    request.setAttribute("nameForm", "Edit");
                    request.getRequestDispatcher("/meal.jsp").forward(request, response);
                    break;
                case "insert":
                    request.setAttribute("nameForm", "Add");
                    request.getRequestDispatcher("/meal.jsp").forward(request, response);
                    break;
                default:
                    mealsTo = filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
                    request.setAttribute("mealTo", mealsTo);
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                    break;
            }
        } else {
            mealsTo = filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            request.setAttribute("mealTo", mealsTo);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doPost MealServlet");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Meal meal;
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
            dao.add(meal);
        } else {
            meal = new Meal(Integer.parseInt(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
            dao.update(meal);
        }
        List<MealTo> mealsTo;
        mealsTo = filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
        request.setAttribute("mealTo", mealsTo);
        response.sendRedirect("/topjava/meals");
    }
}
