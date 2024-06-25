package com.example.yourcompany.employeeattendancesystem.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.yourcompany.employeeattendancesystem.model.Attendance;
import com.example.yourcompany.employeeattendancesystem.model.MonthlyWorkHours;
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

                // 将字符串类型的休息时间转换为 LocalDateTime 类型
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                attendance.setBreakTime(LocalDateTime.parse(attendance.getBreakTimeStr(), formatter));
            }

            // 检查其他必填字段
            if (attendance.getEmployeeId() == null || attendance.getCheckIn() == null || attendance.getEndTime() == null) {
                throw new IllegalArgumentException("请填写完整的打卡信息");
            }

            // 保存打卡记录到数据库，传递休息时间小时数
            attendanceService.saveAttendance(attendance, breakTimeHours);
            return "打卡成功!";
        } catch (IllegalArgumentException e) {
            return "出错了: " + e.getMessage();
        } catch (Exception e) {
            return "提交打卡记录时出错了： " + e.getMessage();
        }
    }

    @GetMapping("/records")
    public String attendanceRecords() {
        return "attendanceRecords"; // 返回打卡记录页面的HTML文件名
    }
    
    
    @GetMapping("/search")
    @ResponseBody
    public List<Attendance> searchAttendance(@RequestParam Integer employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }//查询员工ID

    @GetMapping("/all")
    @ResponseBody
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }
        /**
         * 按月份获取指定员工的总工作时长
         */
        @GetMapping("/totalWorkHoursByMonth/{employeeId}")
        public List<MonthlyWorkHours> getTotalWorkHoursByMonth(@PathVariable Integer employeeId) {
            return attendanceService.getTotalWorkHoursByMonth(employeeId);
    }
}

