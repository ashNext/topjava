package ru.javawebinar.topjava.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

public class UserToValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserTo.class.equals(aClass);
    }

    private User existsEmail(String email) {
        try {
            return userService.getByEmail(email);
        } catch (NotFoundException e) {
            return null;
        }
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserTo userTo = (UserTo) o;

        User user = existsEmail(userTo.getEmail());
        if (user != null) {
            if (!user.getId().equals(userTo.getId())) {
                errors.rejectValue("email", "user.exist");
            }
        }
    }
}
