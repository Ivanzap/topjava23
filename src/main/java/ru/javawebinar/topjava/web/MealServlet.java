package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx;
    private MealRestController mealRestController;

    @Override
    public void init() throws ServletException {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.parseInt(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) {
            mealRestController.create(meal);
        } else {
            mealRestController.update(meal, getId(request));
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                String fromDate = request.getParameter("fromDate");
                String toDate = request.getParameter("toDate");
                String fromTime = request.getParameter("fromTime");
                String toTime = request.getParameter("toTime");
                LocalDate startDate = !fromDate.isEmpty() ? LocalDate.parse(fromDate) : null;
                LocalDate endDate = !toDate.isEmpty() ? LocalDate.parse(toDate) : null;
                LocalTime startTime = !fromTime.isEmpty() ? LocalTime.parse(fromTime) : null;
                LocalTime endTime = !toTime.isEmpty() ? LocalTime.parse(toTime) : null;
                setDateTime(request, fromDate, toDate, fromTime, toTime);
                log.info("getFilteredList with {}, {}, {}, {}", startDate, endDate, startTime, endTime);
                request.setAttribute("meals", mealRestController.getFilteredList(startDate, endDate, startTime, endTime));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals", mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private void setDateTime(HttpServletRequest request, String fromDate, String toDate, String fromTime, String toTime) {
        if (!fromDate.isEmpty()) {
            request.setAttribute("fromDate", LocalDate.parse(fromDate));
        }
        if (!toDate.isEmpty()) {
            request.setAttribute("toDate", LocalDate.parse(toDate));
        }
        if (!fromTime.isEmpty()) {
            request.setAttribute("fromTime", LocalTime.parse(fromTime));
        }
        if (!toTime.isEmpty()) {
            request.setAttribute("toTime", LocalTime.parse(toTime));
        }
    }

    @Override
    public void destroy() {
        appCtx.close();
    }
}
