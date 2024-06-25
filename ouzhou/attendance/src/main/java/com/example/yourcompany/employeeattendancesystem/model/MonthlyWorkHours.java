package com.example.yourcompany.employeeattendancesystem.model;

import java.time.YearMonth;

public class MonthlyWorkHours {

    private Integer employeeId;
    private YearMonth yearMonth;
    private String totalWorkHours; // 可以是格式化后的字符串，如 "8h 30m"

    // Getters and setters
    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getTotalWorkHours() {
        return totalWorkHours;
    }

    public void setTotalWorkHours(String totalWorkHours) {
        this.totalWorkHours = totalWorkHours;
    }
}
