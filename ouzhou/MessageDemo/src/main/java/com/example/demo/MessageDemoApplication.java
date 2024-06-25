package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;

import java.util.Locale;

@SpringBootApplication
public class MessageDemoApplication implements CommandLineRunner {

    @Autowired
    private MessageSource messageSource;

    public static void main(String[] args) {
        SpringApplication.run(MessageDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Locale enLocale = Locale.forLanguageTag("en");
        Locale zhLocale = Locale.forLanguageTag("ch");

        System.out.println("English:");
        System.out.println(messageSource.getMessage("greeting", null, enLocale));
        System.out.println(messageSource.getMessage("farewell", null, enLocale));

        System.out.println("Chinese:");
        System.out.println(messageSource.getMessage("greeting", null, zhLocale));
        System.out.println(messageSource.getMessage("farewell", null, zhLocale));
    }
}

