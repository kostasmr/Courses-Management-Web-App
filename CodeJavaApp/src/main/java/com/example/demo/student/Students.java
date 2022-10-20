package com.example.demo.student;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "students")

public class Students{
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentsId;
       
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;
     
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;
    
    @Column(name = "grade", nullable = true, length = 20)
    private float courseGrade;
    
    @Column(name = "Professor", nullable = false, length = 45)
    private String courseProfessor;

    @Column(name = "email",unique = true, nullable = false, length = 45)
    private String studentEmail;
    
    @Column(name = "course",nullable = false)
    private String studentCourse;
    
    
    @Column(name = "Exams_Grade", nullable = true, length = 20)
    private float examsGrade;
    
    @Column(name = "Project_Grade", nullable = true, length = 20)
    private float projectGrade;
    
	public Students() {

	}
	
	public Students(long studentsId, String firstName, String lastName, String courseProfessor, String studentEmail, String studentCourse) {
		// TODO Auto-generated constructor stub
		this.studentsId = studentsId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.courseProfessor = courseProfessor;
		this.studentEmail = studentEmail;
		this.studentCourse = studentCourse;
	}
	public Long getStudentsId() {
		return studentsId;
	}
	public void setStudentsId(Long studentsId) {
		this.studentsId = studentsId;
	}
	public float getCourseGrade() {
		return courseGrade;
	}
	
	public float getExamsGrade() {
		return examsGrade;
	}
	public void setExamsGrade(float examsGrade) {
		this.examsGrade = examsGrade;
	}
	public float getProjectGrade() {
		return projectGrade;
	}
	public void setProjectGrade(float projectGrade) {
		this.projectGrade = projectGrade;
	}
	public void setCourseGrade(float project,float exams) {
		float finalGrade=0;
		float x1=(float) (project*getProjectGrade());
		float x2=(float)(exams*getExamsGrade());
		finalGrade=x1+x2;
		finalGrade = (float) (Math.round(finalGrade*10.0)/10.0);
		this.courseGrade = finalGrade;
		
	}
	public void setFirstName(String firstName) {
		this.firstName=firstName;
		
	}
	public String getFirstName() {
		return firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName=lastName;
		
	}

	public String getLastName() {
		return lastName;
	}
     
	public String getCourseProfessor() {
		return courseProfessor;
	}
	public void setCourseProfessor(String courseProfessor) {
		this.courseProfessor = courseProfessor;
	}
	
	public void setStudentEmail(String studentEmail) {
		this.studentEmail=studentEmail;
		
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentCourse(String studentCourse) {
		this.studentCourse=studentCourse;
		
	}
	public String getStudentCourse() {
		return studentCourse;
	}
}