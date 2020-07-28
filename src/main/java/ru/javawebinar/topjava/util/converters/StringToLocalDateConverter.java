package ru.javawebinar.topjava.util.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
        if (StringUtils.isEmpty(s))
            return null;
        LocalDateTime dateTime = LocalDateTime.parse(s);
        return dateTime.toLocalDate();
    }
}
