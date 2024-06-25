package com.example.yourcompany.employeeattendancesystem.repository;


import com.example.yourcompany.employeeattendancesystem.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

}
