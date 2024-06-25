package com.example.yourcompany.employeeattendancesystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "break_time")
    private LocalDateTime breakTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "work_hour")
    private String workHour; // 添加工作时长字段
    
    @Transient
    private String breakTimeStr;

    // Getter and Setter for fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(LocalDateTime breakTime) {
        this.breakTime = breakTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getWorkHour() {
        return workHour;
    }

    public void setWorkHour(String workHour) {
        this.workHour = workHour;
    }
    
    public String getBreakTimeStr() {
        return breakTimeStr;
    }

    public void setBreakTimeStr(String breakTimeStr) {
        this.breakTimeStr = breakTimeStr;
    }
}
