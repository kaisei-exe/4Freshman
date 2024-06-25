package com.example.transaction.controller;

import com.example.transaction.entity.User;
import com.example.transaction.entity.Salary;
import com.example.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String createUserAndSalary(@RequestBody User user, @RequestBody Salary salary) {
        userService.createUserAndSalary(user, salary);
        return "User and Salary created successfully";
    }
}
