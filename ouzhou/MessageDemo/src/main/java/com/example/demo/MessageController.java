package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/greeting")
    public String getGreeting(@RequestHeader(name = "Accept-Language", required = false) String language) {
        Locale locale = Locale.forLanguageTag(language != null ? language : "en");
        return messageSource.getMessage("greeting", null, locale);
    }

    @GetMapping("/farewell")
    public String getFarewell(@RequestHeader(name = "Accept-Language", required = false) String language) {
        Locale locale = Locale.forLanguageTag(language != null ? language : "en");
        System.out.println("Locale:"+locale);
        return messageSource.getMessage("farewell", null, locale);
       

    }
    
}

