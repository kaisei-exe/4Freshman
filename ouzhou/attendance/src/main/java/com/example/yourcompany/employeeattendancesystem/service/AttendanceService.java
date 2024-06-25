package com.example.yourcompany.employeeattendancesystem.service;

import com.example.yourcompany.employeeattendancesystem.model.*;
import com.example.yourcompany.employeeattendancesystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    /**
     * 查询所有打卡记录
     */
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    /**
     * 根据ID查询打卡记录
     */
    public Optional<Attendance> getAttendanceById(Integer employeeId) {
        return attendanceRepository.findById(employeeId);
    }

    /**
     * 保存打卡记录
     */
    @Transactional
    public Attendance saveAttendance(Attendance attendance , int breakTimeHours) {
        LocalDateTime checkInDateTime = attendance.getCheckIn();
        LocalDateTime endTimeDateTime = attendance.getEndTime();

        if (checkInDateTime != null && endTimeDateTime != null) {
            Duration workDuration = Duration.between(checkInDateTime, endTimeDateTime);

            // 如果 breakTime 不为空，减去固定的1小时休息时间
            if (attendance.getBreakTime() != null) {
                workDuration = workDuration.minusHours(1);
            }

            attendance.setWorkHour(formatDuration(workDuration));
        }

        // 确保设置了非NULL的工作时长
        if (attendance.getWorkHour() == null) {
            attendance.setWorkHour("0h 0m"); 
        }

        return attendanceRepository.save(attendance);
    }




    /**
     * 计算两个时间之间的工作时长（不含休息时间）
     */
    public Duration calculateWorkDuration(LocalDateTime checkIn, LocalDateTime endTime) {
        if (checkIn == null || endTime == null) {
            return Duration.ZERO;
        }
        return Duration.between(checkIn, endTime);
    }

    /**
    * 计算指定员工按月份的总工作时长
    */
   public List<MonthlyWorkHours> getTotalWorkHoursByMonth(Integer employeeId) {
       List<MonthlyWorkHours> monthlyWorkHoursList = new ArrayList<>();

       // 查询指定员工的所有打卡记录
       List<Attendance> attendances = attendanceRepository.findByEmployeeId(employeeId);

       // 使用Map按月份分组计算总工作时长
       Map<YearMonth, Duration> monthlyWorkHoursMap = new HashMap<>();
       for (Attendance attendance : attendances) {
           LocalDateTime checkIn = attendance.getCheckIn();
           LocalDateTime endTime = attendance.getEndTime();
           if (checkIn != null && endTime != null) {
               YearMonth yearMonth = YearMonth.from(checkIn);
               Duration workDuration = calculateWorkDuration(checkIn, endTime);
               monthlyWorkHoursMap.merge(yearMonth, workDuration, Duration::plus);
           }
       }

       // 将Map中的数据转换为MonthlyWorkHours对象列表
       monthlyWorkHoursMap.forEach((yearMonth, duration) -> {
           MonthlyWorkHours monthlyWorkHours = new MonthlyWorkHours();
           monthlyWorkHours.setEmployeeId(employeeId);
           monthlyWorkHours.setYearMonth(yearMonth);
           monthlyWorkHours.setTotalWorkHours(formatDuration(duration));
           monthlyWorkHoursList.add(monthlyWorkHours);
       });

       return monthlyWorkHoursList;
   }


    /**
     * 格式化工作时长为人类可读的格式（例如：8h 30m）
     */
    public String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        return String.format("%dh %dm", hours, minutes);
    }


}

