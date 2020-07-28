package ru.javawebinar.topjava.util.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(String s) {
        if (StringUtils.isEmpty(s))
            return null;
        LocalDateTime dateTime = LocalDateTime.parse(s);
        return dateTime.toLocalTime();
    }
}
