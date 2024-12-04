package com.example.park_api.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.sql.Time;
import java.util.TimeZone;

@Configuration
public class SpringTimezoneconfig {

    @PostConstruct
    public void timezoneConfig(){
        TimeZone.setDefault(TimeZone.getTimeZone("America/Cuiaba"));
    }
}
