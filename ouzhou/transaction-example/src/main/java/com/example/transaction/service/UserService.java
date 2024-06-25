package com.example.transaction.service;

import com.example.transaction.entity.User;
import com.example.transaction.entity.Salary;
import com.example.transaction.repository.UserRepository;
import com.example.transaction.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @Transactional
    public void createUserAndSalary(User user, Salary salary) {
        userRepository.save(user);
        salaryRepository.save(salary);
    }
}
