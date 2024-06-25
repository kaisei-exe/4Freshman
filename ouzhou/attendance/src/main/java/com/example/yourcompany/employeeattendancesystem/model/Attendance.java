package com.example.yourcompany.employeeattendancesystem.model;

import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;


@Entity
public class Attendance {
	@Id
	private  Integer employeeId;
    
    private String workHours;
    private Date checkIn;
    private Date breakTime;
    private Date endTime;
    private String breakTimeStr;
    

    
    public Attendance() {
    	
    }

    public Attendance( Integer employeeId, Date checkIn, Date breakTime, Date endTime) {
        this.employeeId = employeeId;
        this.checkIn = checkIn;
        this.breakTime = breakTime;
        this.endTime = endTime;
        
    }

    // getters and setters
    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }
    

    public  Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId( Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(Date breakTime) {
        this.breakTime = breakTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public String getBreakTimeStr() {
        return breakTimeStr;
    }
    
    public void setBreakTimeStr(String breakTimeStr) {
        this.breakTimeStr = breakTimeStr;
    }
}
