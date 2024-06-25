package com.example.yourcompany.employeeattendancesystem.repository;


import com.example.yourcompany.employeeattendancesystem.model.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
	List<Attendance> findByEmployeeId(Integer employeeId);
	
	
	@Query("SELECT a FROM Attendance a WHERE a.employeeId = :employeeId " +
	           "AND EXTRACT(YEAR FROM a.endTime) = :year AND EXTRACT(MONTH FROM a.endTime) = :month")
	    List<Attendance> findByEmployeeIdAndYearMonth(@Param("employeeId") int employeeId,
	                                                  @Param("year") int year,
	                                                  @Param("month") int month);
}


