package com.example.demo;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.course.Course;
import com.example.demo.course.CourseService;
import com.example.demo.course.CoursesRepository;
import com.example.demo.user.CustomUserDetailsService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
 
@SpringBootTest
public class CourseServiceTests {
     
    @Autowired
    private CourseService service;
    
    @Autowired
    private CoursesRepository repo;
    
    @Test
    public void testGetCourse(){
    	Course course = service.get(2);
    	
    	assertThat(course.getCourseName()).isNotNull();
    	assertEquals(course.getCourseName(),"Mathematics");
    	
    }
    
    @Test
    @Transactional 
    @Rollback(true)
    public void testDeleteCourse(){

    	//before
    	Course course = service.get(3);
    	assertThat(course.getCourseName()).isNotNull();
    	assertEquals(course.getCourseName(),"Java");
    	
    	//then
    	service.delete(3);
    	
    	//after
    	Course deleted_course = repo.findByCourse((long) 3);
    	assertThat(deleted_course).isNull();
    	
    }
    
}
