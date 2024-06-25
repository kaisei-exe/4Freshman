package com.example.demo;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String index(Model model) {
        Message message = new Message();
        message.setTitle("Welcome to our website!");
        message.setContent("by Thymeleaf!");
        
        model.addAttribute("message", message);
        return "index";
    }
}

