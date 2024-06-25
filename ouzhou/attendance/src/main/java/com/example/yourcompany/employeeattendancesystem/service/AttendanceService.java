package com.example.yourcompany.employeeattendancesystem.service;

import com.example.yourcompany.employeeattendancesystem.model.*;
import com.example.yourcompany.employeeattendancesystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
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
    public Attendance saveAttendance(Attendance attendance,int breakTimeHours) {
        LocalDateTime checkInDateTime = convertDateToLocalDateTime(attendance.getCheckIn());
        LocalDateTime endTimeDateTime = convertDateToLocalDateTime(attendance.getEndTime());

        if (checkInDateTime != null && endTimeDateTime != null) {
            // 设置默认的工作总时长，减去休息时间1小时
            Duration workDuration = Duration.between(checkInDateTime, endTimeDateTime).minusHours(breakTimeHours);
            attendance.setWorkHours(formatDuration(workDuration));
        }

        return attendanceRepository.save(attendance);
    }

    private LocalDateTime convertDateToLocalDateTime(Date checkIn) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * 删除打卡记录
     */
    @Transactional
    public void deleteAttendance(Integer employeeId) {
        attendanceRepository.deleteById(employeeId);
    }

    /**
     * 计算两个时间之间的工作时长（不含休息时间）
     */
    public Duration calculateWorkDuration(LocalDateTime checkIn, LocalDateTime endTime) {
        if (checkIn == null || endTime == null) {
            return Duration.ZERO;
        }
        return Duration.between(checkIn, endTime).minusHours(1); // 减去休息时间1小时
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

