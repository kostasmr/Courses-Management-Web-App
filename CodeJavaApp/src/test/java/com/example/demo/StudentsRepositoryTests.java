package com.example.demo;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.student.Students;
import com.example.demo.student.StudentsRepository;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class StudentsRepositoryTests {
 
    @Autowired
    private TestEntityManager entityManager;
     
    @Autowired
    private StudentsRepository repo;
     
    @Test
    @Transactional 
    @Rollback(true)
    public void testCreateStudent() {
        Students student = new Students();
        student.setFirstName("Paulos");
        student.setLastName("Paulou");
        student.setStudentEmail("paulos@gmail.com");
        student.setCourseProfessor("Mamoulis@gmail.com");
        student.setExamsGrade(7);
        student.setProjectGrade(6);
        student.setCourseGrade((float)0.5,(float)0.5);
        student.setStudentCourse("C++");
         
        Students savedStudent = repo.save(student);
         
        Students existStudent = entityManager.find(Students.class, savedStudent.getStudentsId());
         
        assertThat(student.getStudentEmail()).isEqualTo(existStudent.getStudentEmail());
         
    }
   
    @Test
    public void testFindByProfessorCourse() {
    	//before
    	String professor = "Mamoulis@gmail.com";
    	String course = "Python";
    	Students java_student = repo.findByStudentId((long)1);
    	Students python_student = repo.findByStudentId((long)10);
    	
    	//then
    	List<Students> listStudents = repo.findByProfessorCourse(professor,course);
    	listStudents.forEach(System.out::println);
    	
    	//after
    	assertThat(listStudents).isNotNull();
    	assertTrue(listStudents.contains(python_student));
    	assertFalse(listStudents.contains(java_student));
    }
    
    @Test
    public void testFindByStudentId() {
    	Students student = repo.findByStudentId((long)1);
    	
    	assertThat(student).isNotNull();
    	assertEquals(student.getStudentEmail(),"papadopoulou@gmail.com");
    	assertEquals(student.getStudentCourse(),"Java");
    }
    
    @Test
    public void testFindAllGrades() {
    	//before
    	float exist_grade = (float) 7;
    	float non_exist_grade  = (float) 1;
    	String professor = "Mamoulis@gmail.com"; 
    	String course = "Java";
    	
    	//then
    	float[] grades = repo.findAllGrades(professor, course);
    	
    	ArrayList<Float> all_grades = new ArrayList<Float>();
    	for(int i=0;i<grades.length;i++)
    	{
    		all_grades.add(grades[i]);
    	}
    	
    	//after
    	assertThat(grades).isNotNull();
    	assertEquals(grades.length,4);
    	assertTrue(all_grades.contains(exist_grade));
    	assertFalse(all_grades.contains(non_exist_grade));
    }
    
    @Test
    public void testFindMaxGrade() {
    	//before
    	String professor = "Mamoulis@gmail.com"; 
    	String course = "Java";
    	
    	//then
    	float max = repo.findMaxGrade(professor, course);
    	
    	//after
    	assertThat(max).isNotNull();
    	assertEquals(max,(float)9.2);
    }
    
    @Test
    public void testFindMinGrade() {
    	//before
    	String professor = "Mamoulis@gmail.com"; 
    	String course = "Java";
    	
    	//then
    	float min = repo.findMinGrade(professor, course);
    	
    	//after
    	assertThat(min).isNotNull();
    	assertEquals(min,(float)3.2);
    }
    
    @Test
    public void testFindSumGrade() {
    	//before
    	String professor = "Mamoulis@gmail.com"; 
    	String course = "Java";
    	
    	//then
    	float sum = repo.findSumOfGrades(professor, course);
    	
    	//after
    	assertThat(sum).isNotNull();
    	assertEquals(sum,(float)22.8);
    }
    
    @Test
    public void testFindCountGrade() {
    	//before
    	String professor = "Mamoulis@gmail.com"; 
    	String course = "Java";
    	
    	//then
    	float count = repo.findCountGrades(professor, course);
    	
    	//after
    	assertThat(count).isNotNull();
    	assertEquals(count,(float)4);
    }
}