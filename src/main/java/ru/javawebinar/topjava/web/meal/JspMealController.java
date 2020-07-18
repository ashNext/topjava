package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping(value = "/filter")
    public String getBetween(
            Model model,
            HttpServletRequest request
    ) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(value = "id") Integer id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/mealForm")
    public String mealForm(
            @RequestParam(value = "id", required = false) Integer id,
            Model model) {
        Meal meal;
        if (id == null) {
            meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        } else {
            meal = super.get(id);
        }
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/mealForm")
    public String save(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam("dateTime") String dateTime,
            @RequestParam("description") String description,
            @RequestParam("calories") int calories
    ) {
        Meal meal = new Meal(
                LocalDateTime.parse(dateTime),
                description,
                calories);
        if (id == null)
            super.create(meal);
        else
            super.update(meal, id);
        return "redirect:/meals";
    }
}
