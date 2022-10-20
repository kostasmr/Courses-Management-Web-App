package com.example.demo;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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

import com.example.demo.student.StudentService;
import com.example.demo.student.Students;
import com.example.demo.student.StudentsRepository;
 
@SpringBootTest
public class StudentServiceTests {
    
    @Autowired
    private StudentsRepository repo;
    
    @Autowired
    private StudentService service;
    
    @Test
    public void testGetFullName(){
    	//before
    	String id_name = "Maria Papadopoulou";
    	
    	//then
    	Students student =repo.findByStudentId((long) 1);
    	String name = service.getFullName(student);
    	
    	//after
    	assertThat(name).isNotNull();
    	assertEquals(name,id_name);
    }
    
    @Test
    public void testGetStudent(){
    	//before
    	String id_name = "Dimitra";
    	
    	//then
    	Students student = service.get((long) 2);
    	
    	//after
    	assertThat(student).isNotNull();
    	assertEquals(student.getFirstName(),id_name);
    }
    
    @Test
    @Transactional 
    @Rollback(true)
    public void testDeleteStudent(){
    	//before
    	Students student = repo.findByStudentId((long) 2);
    	assertThat(student).isNotNull();
    	assertEquals(student.getFirstName(),"Dimitra");
    	
    	//then
    	service.delete((long) 2);
    	
    	//after
    	Students deleted_student = repo.findByStudentId((long) 2);
    	assertThat(deleted_student).isNull();
    }
    
    @Test
    @Transactional 
    @Rollback(true)
    public void testDeleteStudentsByCourse(){
    	//before
    	List<Students> students = repo.findByProfessorCourse("Mamoulis@gmail.com", "Java");
    	Students student = repo.findByStudentId((long)1);
    	assertThat(students).isNotNull();
    	assertTrue(students.contains(student));
    	
    	//then
    	service.deleteStudentsByCourse("Java");
    	
    	//after
    	List<Students> deleted_students = repo.findByProfessorCourse("Mamoulis@gmail.com", "Java");
    	
    	assertEquals(deleted_students.size(),0);
    	assertFalse(deleted_students.contains(student));
    }
}
