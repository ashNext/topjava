package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImplInternalStorage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDao mealDao;

    @Override
    public void init() throws ServletException {
        mealDao = new MealDaoImplInternalStorage();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "/meals.jsp";

        String action = req.getParameter("action");
        if (action != null && !action.isEmpty()) {
            log.debug("action=" + action);

            if (action.equalsIgnoreCase("delete")) {
                Long mealId = Long.parseLong(req.getParameter("mealId"));
                mealDao.deleteById(mealId);
                resp.sendRedirect(req.getContextPath() + "/meals");
                return;
            } else if (action.equalsIgnoreCase("edit")) {
                Long mealId = Long.parseLong(req.getParameter("mealId"));
                Meal meal = mealDao.getById(mealId);
                req.setAttribute("meal", meal);
                forward = "/mealEdit.jsp";
            } else if (action.equalsIgnoreCase("new")) {
                forward = "/mealEdit.jsp";
            }
        } else {
            req.setAttribute("localDateTimeFormatter", TimeUtil.DATE_TIME_FORMATTER);
            req.setAttribute("meals", MealsUtil.allToDTO(mealDao.getAll()));
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(forward);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String paramDateTime = req.getParameter("dtl");
        String paramDescription = req.getParameter("descr");
        String paramCalories = req.getParameter("cal");
        String paramMealId = req.getParameter("mealId");
        if (paramDateTime != null && !paramDateTime.isEmpty() && paramDescription != null && !paramDescription.isEmpty() && paramCalories != null && !paramCalories.isEmpty()) {
            log.debug(String.format("DateTime='%s', Description='%s', Calories='%s'", paramDateTime, paramDescription, paramCalories));
            LocalDateTime dateTime = LocalDateTime.parse(paramDateTime);
            int calories = Integer.parseInt(paramCalories);

            Meal meal = new Meal(dateTime, paramDescription, calories);

            if (paramMealId != null && !paramMealId.isEmpty()) {
                meal.setId(Long.parseLong(paramMealId));
                mealDao.update(meal);
            } else {
                mealDao.add(meal);
            }

            resp.sendRedirect(req.getContextPath() + "/meals");
        } else {
            resp.sendRedirect(req.getContextPath() + "/meals?action=" + req.getParameter("action") + (paramMealId != null ? "&mealId=" + paramMealId : ""));
        }
    }
}
