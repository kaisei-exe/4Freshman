package com.example.yourcompany.employeeattendancesystem.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.yourcompany.employeeattendancesystem.model.Attendance;
import com.example.yourcompany.employeeattendancesystem.repository.AttendanceRepository;
import com.example.yourcompany.employeeattendancesystem.service.AttendanceService;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {
	

    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/form")
    public String attendanceForm() {
        return "attendanceForm"; // 返回打卡页面的HTML文件名
    }

    @PostMapping("/submit")
    @ResponseBody
    public String submitAttendance(@RequestBody Attendance attendance) {
        
    		
    		try {
    			// 设置默认休息时间为0小时
    				int breakTimeHours = 0;
    			// 如果输入了休息时间字符串并且不为空，设定为1小时
    		        if (attendance.getBreakTimeStr() != null && !attendance.getBreakTimeStr().isEmpty()) {
    		            breakTimeHours = 1;
    		        }
    		        
    		        attendanceService.saveAttendance(attendance, breakTimeHours);
    		        
                // 将字符串类型的休息时间转换为 Date 类型
                if (attendance.getBreakTimeStr() != null && !attendance.getBreakTimeStr().isEmpty()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date breakTime = dateFormat.parse(attendance.getBreakTimeStr());
                    attendance.setBreakTime(breakTime);
                }

                // 检查其他必填字段
                if (attendance.getEmployeeId() == null || attendance.getCheckIn() == null
                        || attendance.getEndTime() == null) {
                    throw new IllegalArgumentException("请填写完整的打卡信息");
                }
    	
    	// 保存打卡记录到数据库
        attendanceRepository.save(attendance);
        return "打卡成功!";
    		} catch (ParseException e) {
                return "Error: Invalid date format for break time";
            } catch (IllegalArgumentException e) {
                return "Error: " + e.getMessage();
            } catch (Exception e) {
                return "Error while submitting attendance: " + e.getMessage();
            }           
 
   }

    @GetMapping("/records")
    public String attendanceRecords() {
        return "attendanceRecords"; // 返回打卡记录页面的HTML文件名
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }
}

