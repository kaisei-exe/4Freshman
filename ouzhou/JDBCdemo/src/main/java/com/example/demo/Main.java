package com.example.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        JdbcTemplate jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");

        // 执行SQL语句示例
        int rowCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user", Integer.class);
        System.out.println("Row count: " + rowCount);
        
        
        ((ClassPathXmlApplicationContext) context).close();
    }
}