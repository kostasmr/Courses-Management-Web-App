package com.example.demo.student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface StudentsRepository extends JpaRepository<Students, Long> {
	
	@Query("SELECT u FROM Students u WHERE u.courseProfessor = ?1 and u.studentCourse = ?2")
	public List<Students> findByProfessorCourse(String courseProfessor,String studentCourse);
	
	@Query("SELECT u FROM Students u WHERE u.studentsId = ?1")
	Students findByStudentId(Long studentId);
	
	@Query("SELECT MAX(courseGrade) FROM Students WHERE courseProfessor = ?1 and studentCourse = ?2")
	float findMaxGrade(String courseProfessor,String studentCourse);
	
	@Query("SELECT MIN(courseGrade) FROM Students WHERE courseProfessor = ?1 and studentCourse = ?2")
	float findMinGrade(String courseProfessor,String studentCourse);
	
	@Query("SELECT SUM(courseGrade) FROM Students WHERE courseProfessor = ?1 and studentCourse = ?2")
	float findSumOfGrades(String courseProfessor,String studentCourse);
	
	@Query("SELECT COUNT(courseGrade) FROM Students WHERE courseProfessor = ?1 and studentCourse = ?2")
	float findCountGrades(String courseProfessor,String studentCourse);
	
	@Query("SELECT courseGrade FROM Students WHERE courseProfessor = ?1 and studentCourse = ?2")
	float[] findAllGrades(String courseProfessor,String studentCourse);
	
}
