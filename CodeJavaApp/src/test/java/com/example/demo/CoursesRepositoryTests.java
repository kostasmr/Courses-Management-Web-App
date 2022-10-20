 package com.example.demo;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.course.Course;
import com.example.demo.course.CoursesRepository;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CoursesRepositoryTests {
 
    @Autowired
    private TestEntityManager entityManager;
     
    @Autowired
    private CoursesRepository repo;
     
    @Test
    @Transactional 
    @Rollback(true)
    public void testCreateCourse() {

        Course course = new Course();
        course.setCourseName("Mikroepexergastes");
        course.setDescription("CPU DESIGN");
        course.setProfessorName("Tenentes@gmail.com");
        course.setPercentage((float)0.5);
         
        Course savedCourse = repo.save(course);
         
        Course existCourse = entityManager.find(Course.class, savedCourse.getCourseId());
         
        assertThat(course.getCourseName()).isEqualTo(existCourse.getCourseName());
         
    }
    
    @Test
    public void testFindByCourse(){

    	Course course =repo.findByCourse((long) 3);
    	
    	assertThat(course).isNotNull();
    	assertEquals(course.getCourseName(),"Java");
    	
    }
    
    @Test
    public void testFindByProfessor() {
    	//before
    	Course correct_course =repo.findByCourse((long) 1);
    	Course wrong_course =repo.findByCourse((long) 3);
    	String professor = "Anastasiadis@gmail.com";
    	
    	//then
    	List<Course> listCourses = repo.findByProfessor(professor);
    	listCourses.forEach(System.out::println);
    	
    	//after
    	assertThat(listCourses).isNotNull();
    	assertTrue(listCourses.contains(correct_course));
    	assertFalse(listCourses.contains(wrong_course));
    	assertEquals(listCourses.size(),1);

    }
    
    @Test
    public void testFindByCourseName(){

    	Course course =repo.findByCourseName("Java");
    	
    	assertThat(course).isNotNull();
    	assertEquals(course.getProfessorName(),"Mamoulis@gmail.com");
    	
    }
    
    @Test
    public void testFindCoursesName(){
    	//before
    	String professor_course = "Java";
    	String another_course = "Βελτιστοποίηση";
    	
    	//then
    	String[] courses = repo.findCoursesName("Mamoulis@gmail.com");
    	ArrayList<String> all_courses = new ArrayList<String>();
    	
    	for(int i=0;i<courses.length;i++)
    	{
    		all_courses.add(courses[i]);
    	}
    	
    	//after
    	assertThat(courses).isNotNull();
    	assertEquals(courses.length,3);
    	assertTrue(all_courses.contains(professor_course));
    	assertFalse(all_courses.contains(another_course));
    	
    }

    @Test
    public void testFindAllCoursesName(){
    	//before
    	String exist_course ="Java";
    	String non_exist_course ="Html";

    	//then
    	String[] courses = repo.findAllCoursesName();
    	ArrayList<String> all_courses = new ArrayList<String>();
    	
    	for(int i=0;i<courses.length;i++)
    	{
    		all_courses.add(courses[i]);
    	}
    	
    	//after
    	assertThat(courses).isNotNull();
    	assertEquals(courses.length,5);
    	assertTrue(all_courses.contains(exist_course));
    	assertFalse(all_courses.contains(non_exist_course));
    	
    }

}