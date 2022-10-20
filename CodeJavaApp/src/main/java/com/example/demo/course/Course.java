package com.example.demo.course;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.example.demo.student.Students;

@Entity 
@Table(name="courses")

public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="course_id")
    private Long courseId;
	
	@Column(name="Professor",nullable=false,length=45)
	private String professorName;
	
	@Column(name="Course",nullable=false,unique=true)
	private String courseName;
	
	@Column(name="Description",nullable=false,unique=false,length=100)
	private String description; 
	
	@Column(name="project_percentage",nullable=true,length=100)
	private float percentage;
	
	public Course() {
			
	}

	public Course(Long courseId,String courseName, String description, String professorName, float percentage) {
		// TODO Auto-generated constructor stub
		this.courseId=courseId;
		this.courseName=courseName;
		this.description=description;
		this.professorName=professorName;
		this.percentage=percentage;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getProfessorName() {
		return professorName;
	}

	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
}