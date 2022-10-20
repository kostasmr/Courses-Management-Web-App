package com.example.demo.course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CoursesRepository extends JpaRepository<Course, Long> {
	
	@Query("SELECT u FROM Course u WHERE u.courseId = ?1")
	Course findByCourse(Long courseId);
	
	@Modifying
	@Query("SELECT u FROM Course u WHERE u.professorName = ?1")
	public List<Course> findByProfessor(String professorName);
	
	@Query("SELECT u FROM Course u WHERE u.courseName = ?1")
	Course findByCourseName(String courseName);
	
	@Modifying
	@Query("SELECT courseName FROM Course WHERE professorName = ?1")
	String[] findCoursesName(String professorName);
	
	@Query("SELECT courseName FROM Course")
	String[] findAllCoursesName();
}
