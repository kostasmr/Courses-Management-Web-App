package com.example.demo.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService {
	
    @Autowired
    private StudentsRepository repo;
     
    public Students get(long id) {
        return repo.findById(id).get();
    }
     
    public void delete(long id) {
        repo.deleteById(id);
    }
    
    public String getFullName(Students students) {
        return students.getFirstName() + " " + students.getLastName();
    }
    
    public void deleteStudentsByCourse(String course) {
    	List<Students> students = repo.findAll();
    	for(int i=0;i<students.size();i++)
    	{
    		if(course.equals(students.get(i).getStudentCourse()))
    		{
    			delete(students.get(i).getStudentsId());
    		}
    	}
    }
}
