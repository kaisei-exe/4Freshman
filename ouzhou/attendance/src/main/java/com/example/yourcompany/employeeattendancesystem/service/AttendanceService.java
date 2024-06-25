package com.example.yourcompany.employeeattendancesystem.service;

import com.example.yourcompany.employeeattendancesystem.model.*;
import com.example.yourcompany.employeeattendancesystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public Attendance saveAttendance(Attendance attendance) {
        try {
            return attendanceRepository.save(attendance);
        } catch (Exception e) {
            throw new RuntimeException("Error saving attendance: " + e.getMessage());
        }
    }
  
    /**
     * 查询所有打卡记录
     */
    public List<Attendance> getAllAttendances() {
        try {
            return attendanceRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching attendances: " + e.getMessage());
        }
    }

    /**
     * 根据ID查询打卡记录
     */
    public Optional<Attendance> getAttendanceById(Integer employeeId) {
        return attendanceRepository.findById(employeeId);
    }
    
    
    
    /**按年月查询方法**/
    public List<Attendance> getAttendanceByEmployeeIdAndYearMonth(int employeeId, int year, int month) {
        return attendanceRepository.findByEmployeeIdAndYearMonth(employeeId, year, month);
    }

    /**计算总的该月份的工作时长**/
    public Duration calculateTotalWorkDuration(List<Attendance> attendances) {
        Duration totalDuration = Duration.ZERO;
        for (Attendance attendance : attendances) {
            if (attendance.getCheckIn() != null && attendance.getEndTime() != null) {
                Duration workDuration = calculateWorkDuration(attendance.getCheckIn(), attendance.getEndTime());
                
                if (attendance.getBreakTime() != null) {
                    workDuration = workDuration.minusHours(1);
                }// 如果 breakTime 不为空，减去固定的1小时休息时间
                
                totalDuration = totalDuration.plus(workDuration);
            }
        }
        return totalDuration;
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
     * 格式化工作时长为便于阅读的形式
     */
    public String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        return String.format("%dh %dm", hours, minutes);
    }


}

