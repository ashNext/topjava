package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAOImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "/meals.jsp";

        String action = req.getParameter("action");
        if (action != null && !action.isEmpty()) {
            log.debug("action=" + action);

            if (action.equalsIgnoreCase("delete")) {
                Long mealId = Long.parseLong(req.getParameter("mealId"));
                MealDAOImpl.getInstance().deleteById(mealId);
            } else if (action.equalsIgnoreCase("edit")) {
                req.setAttribute("isEdit", true);
                Long mealId = Long.parseLong(req.getParameter("mealId"));
                Meal meal = MealDAOImpl.getInstance().getById(mealId);
                req.setAttribute("datetime", meal.getDateTime());
                req.setAttribute("description", meal.getDescription());
                req.setAttribute("calories", meal.getCalories());
                forward = "/mealEdit.jsp";
            } else if (action.equalsIgnoreCase("new")) {
                req.setAttribute("isEdit", false);
                forward = "/mealEdit.jsp";
            }
        }

        req.setAttribute("meals", MealsUtil.allToDTO(MealDAOImpl.getInstance().getAll()));

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(forward);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String dtl = req.getParameter("dtl");
        String descr = req.getParameter("descr");
        String cal = req.getParameter("cal");
        if (dtl != null && !dtl.isEmpty() && descr != null && !descr.isEmpty() && cal != null && !cal.isEmpty()) {
            log.debug(String.format("dtl='%s', descr='%s', cal='%s'", dtl, descr, cal));
            LocalDateTime dateTime = LocalDateTime.parse(dtl);
            int calories = Integer.parseInt(cal);

            String id = req.getParameter("mealId");
            if (id != null && !id.isEmpty()) {
                Meal meal = MealDAOImpl.getInstance().getById(Long.parseLong(id));
                if (meal != null) {
                    meal.setDateTime(dateTime);
                    meal.setDescription(descr);
                    meal.setCalories(calories);
                }
            } else {
                synchronized (this) {
                    MealDAOImpl.getInstance().add(new Meal(dateTime, descr, calories));
                }
            }

            req.setAttribute("meals", MealsUtil.allToDTO(MealDAOImpl.getInstance().getAll()));
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        } else {
            req.setAttribute("errText", "Fields must not be empty!");
            req.setAttribute("datetime", dtl);
            req.setAttribute("description", descr);
            req.setAttribute("calories", cal);
            req.getRequestDispatcher("/mealEdit.jsp").forward(req, resp);
        }
    }
}
