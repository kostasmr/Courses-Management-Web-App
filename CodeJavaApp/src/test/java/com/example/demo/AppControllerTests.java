package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo.AppController;
import com.example.demo.course.Course;
import com.example.demo.course.CoursesRepository;
import com.example.demo.student.Students;
import com.example.demo.student.StudentsRepository;
import com.example.demo.user.CustomUserDetails;
import com.example.demo.user.CustomUserDetailsService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;



@SpringBootTest
@TestPropertySource(
		  locations = "classpath:application.properties")
@AutoConfigureMockMvc
class AppControllerTests {
	
	@Autowired
    private WebApplicationContext context;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	AppController appController;

    @Autowired
    private CoursesRepository courseRepo;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private StudentsRepository studentsRepo;
    
	@BeforeEach
    public void setup() {
		mockMvc = MockMvcBuilders
          .webAppContextSetup(context)
          .build();
    }
	
	@Test
	void testAppControllerIsNotNull() {
		assertNotNull(appController);
	}

	@Test
	void testMockMvcIsNotNull() {
		assertNotNull(mockMvc);
	}
	
	@Test 
	@WithMockUser
	void testHomeReturnsPage() throws Exception {
		this.mockMvc.perform(get("/")).
		andExpect(status().isOk()).
		andExpect(view().name("index"));			
	}
	
	@Test 
	@WithMockUser
	void testRegisterReturnsPage() throws Exception {
		this.mockMvc.perform(get("/register")).
		andExpect(status().isOk()).
		andExpect(model().attributeExists("user")).
		andExpect(view().name("signup_form"));		
	}
	
	@Test 
	@WithMockUser(value = "Mamoulis@gmail.com")
    @Transactional 
    @Rollback(true)
	void testProcessRegisterReturnsPage() throws Exception {
		
	    User user = new User((long)6,"lola", "lola", "lola@gmail.com","123456");			//Test to register a user.
	    User user2 = new User((long)7,"lola2", "lola2", "Mamoulis@gmail.com","123456");	  	//Test to register a user with an email that already exists. 
	    User user3 = new User((long)8,"Nikolaos", "Mamoulis", "xxx@gmail.com","123456");	//Test to register a user with a full name that already exists.
	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("id", Long.toString(user.getId()));
	    multiValueMap.add("firstName", user.getFirstName());
	    multiValueMap.add("lastName", user.getLastName());
	    multiValueMap.add("email", user.getEmail());
	    multiValueMap.add("password", user.getPassword());
	    
	    MultiValueMap<String, String> multiValueMap2 = new LinkedMultiValueMap<>();
	    multiValueMap2.add("id", Long.toString(user2.getId()));
	    multiValueMap2.add("firstName", user2.getFirstName());
	    multiValueMap2.add("lastName", user2.getLastName());
	    multiValueMap2.add("email", user2.getEmail());
	    multiValueMap2.add("password", user2.getPassword());

	    MultiValueMap<String, String> multiValueMap3 = new LinkedMultiValueMap<>();
	    multiValueMap3.add("id", Long.toString(user3.getId()));
	    multiValueMap3.add("firstName", user3.getFirstName());
	    multiValueMap3.add("lastName", user3.getLastName());
	    multiValueMap3.add("email", user3.getEmail());
	    multiValueMap3.add("password", user3.getPassword());
	    
		mockMvc.perform(post("/process_register")
				.params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/"));
		
		mockMvc.perform(post("/process_register")
				.params(multiValueMap2))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/register"));
		
		mockMvc.perform(post("/process_register")
				.params(multiValueMap3))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/register"));
	}
	
	@Test
	@WithMockUser(value="Mamoulis@gmail.com")
	void testUserHomeReturnsPage() throws Exception {
		mockMvc.perform(get("/users")).
		andExpect(status().isOk()).
		andExpect(model().attributeExists("listCourses","fullname")).
		andExpect(view().name("users"));		

	}
	
	@WithMockUser(value="Mamoulis@gmail.com")
	@Test
	void testAddCourseReturnsPage() throws Exception {        
		mockMvc.perform(get("/addCourse")).
		andExpect(status().isOk()).
		andExpect(model().attributeExists("fullname","course")).
		andExpect(view().name("addCourse_form"));		
	}
	
	@Test 
	@WithMockUser(value="Mamoulis@gmail.com")
    @Transactional 
    @Rollback(true)
	void testProcessAddCourseReturnsPage() throws Exception {
		
	    Course course = new Course((long)6,"assemply", "code", "ass@gmail.com",(long) 0.6);			//Test to add course.
	    Course course2 = new Course((long)7,"Java", "code", "Mamoulis@gmail.com",(long) 0.6);		//Test to add a course that is already in the list of the professor.
	    Course course3 = new Course((long)8,"Mathematics", "code", "Mamoulis@gmail.com",(long) 0.6);		//Test to add a course that already exists.
	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("courseId", Long.toString(course.getCourseId()));
	    multiValueMap.add("courseName", course.getCourseName());
	    multiValueMap.add("description", course.getDescription());
	    multiValueMap.add("professorName", course.getProfessorName());
	    multiValueMap.add("percentage", Float.toString(course.getPercentage()));
	    
	    MultiValueMap<String, String> multiValueMap2 = new LinkedMultiValueMap<>();
	    multiValueMap2.add("courseId", Long.toString(course2.getCourseId()));
	    multiValueMap2.add("courseName", course2.getCourseName());
	    multiValueMap2.add("description", course2.getDescription());
	    multiValueMap2.add("professorName", course2.getProfessorName());
	    multiValueMap2.add("percentage", Float.toString(course2.getPercentage()));
	    
	    MultiValueMap<String, String> multiValueMap3 = new LinkedMultiValueMap<>();
	    multiValueMap3.add("courseId", Long.toString(course3.getCourseId()));
	    multiValueMap3.add("courseName", course3.getCourseName());
	    multiValueMap3.add("description", course3.getDescription());
	    multiValueMap3.add("professorName", course3.getProfessorName());
	    multiValueMap3.add("percentage", Float.toString(course3.getPercentage()));
	    
		mockMvc.perform(post("/process_addCourse")
				.params(multiValueMap)).
				andExpect(status().isFound()).
				andExpect(view().name("redirect:/users"));	
		
		mockMvc.perform(post("/process_addCourse")
				.params(multiValueMap2)).
				andExpect(status().isFound()).
				andExpect(view().name("redirect:/addCourse"));	
		
		mockMvc.perform(post("/process_addCourse")
				.params(multiValueMap3)).
				andExpect(status().isFound()).
				andExpect(view().name("redirect:/addCourse"));	
	}
	
	
	@WithMockUser(value="Mamoulis@gmail.com")
	@Test
	void testBrowseStudentReturnsPage() throws Exception {      

		mockMvc.perform(
				put("/browseStd/{courseId}",(long) 5))
				.andExpect(status().isOk())
				.andExpect(view().name("browse_student"));		
	}
	
	@WithMockUser(value="Mamoulis@gmail.com")
	@Test
	void testCourseStatsReturnsPage() throws Exception {      

		mockMvc.perform(
				put("/statistics/{courseId}",(long) 5))
				.andExpect(status().isOk())
				.andExpect(view().name("course_stats"));		
	}
	
	@WithMockUser(value="Mamoulis@gmail.com")
	@Test
    @Transactional 
    @Rollback(true)
	void testDeleteCourseReturnsPage() throws Exception {      

		mockMvc.perform(
				post("/delete/{courseId}",(long) 5))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/users"));		
	}
	
	@WithMockUser(value="Mamoulis@gmail.com")
	@Test
    @Transactional 
    @Rollback(true)
	void testEditCourseReturnsPage() throws Exception {      

		mockMvc.perform(
				post("/edit/{courseId}",(long) 5))
				.andExpect(status().isOk())
				.andExpect(view().name("edit_course"));		
	}
	
	@WithMockUser(value="Mamoulis@gmail.com")
	@Test
    @Transactional 
    @Rollback(true)
	void testSaveCourseReturnsPage() throws Exception {      

	    Course course = new Course((long)6,"assemply", "code", "ass@gmail.com",(long) 0.6);
	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("courseId", Long.toString(course.getCourseId()));
	    multiValueMap.add("courseName", course.getCourseName());
	    multiValueMap.add("description", course.getDescription());
	    multiValueMap.add("professorName", course.getProfessorName());
	    multiValueMap.add("percentage", Float.toString(course.getPercentage()));
	    
		mockMvc.perform(
				post("/save")
				.params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/users"));		
	}
	
	@WithMockUser(value="Mamoulis@gmail.com")
	@Test
    @Transactional 
    @Rollback(true)
	void testDeleteStudentReturnsPage() throws Exception {      

		mockMvc.perform(
				post("/deleteStudent/{studentsId}",(long) 11))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/browseStd/"+(long)5));		
	}
	
	@WithMockUser(value="Mamoulis@gmail.com")
	@Test
    @Transactional 
    @Rollback(true)
	void testAddStudentReturnsPage() throws Exception {      

		mockMvc.perform(
				get("/add_Student/{courseId}",(long) 5))
				.andExpect(status().isOk())
				.andExpect(view().name("add_student"));		
	}
	
	//FALSE
	@WithMockUser(value="Mamoulis@gmail.com")
	@Test
    @Transactional 
    @Rollback(true)
	void testProcessAddStudentReturnsPage() throws Exception {      

	    Students student = new Students((long)6,"lola", "lola", "Mamoulis@gmail.com","lola@gmail.com","Java");		//Test to add new Student.
	    Students student2 = new Students((long)20,"Dimitra", "Antoniou", "Mamoulis@gmail.com","xxx@gmail.com","Java");		//Test to add new Student with a full name that already exists.
	    Students student3 = new Students((long)21,"xxx", "xxx", "Mamoulis@gmail.com","markou@gmail.com","C++");		//Test to add new Student with an email that already exists.
	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("studentsId", Long.toString(student.getStudentsId()));
	    multiValueMap.add("firstName", student.getFirstName());
	    multiValueMap.add("lastName", student.getLastName());
	    multiValueMap.add("courseProfessor", student.getCourseProfessor());
	    multiValueMap.add("studentEmail", student.getStudentEmail());
	    multiValueMap.add("studentCourse", student.getStudentCourse());
	    
	    MultiValueMap<String, String> multiValueMap2 = new LinkedMultiValueMap<>();
	    multiValueMap2.add("studentsId", Long.toString(student2.getStudentsId()));
	    multiValueMap2.add("firstName", student2.getFirstName());
	    multiValueMap2.add("lastName", student2.getLastName());
	    multiValueMap2.add("courseProfessor", student2.getCourseProfessor());
	    multiValueMap2.add("studentEmail", student2.getStudentEmail());
	    multiValueMap2.add("studentCourse", student2.getStudentCourse());
	    
	    MultiValueMap<String, String> multiValueMap3 = new LinkedMultiValueMap<>();
	    multiValueMap3.add("studentsId", Long.toString(student3.getStudentsId()));
	    multiValueMap3.add("firstName", student3.getFirstName());
	    multiValueMap3.add("lastName", student3.getLastName());
	    multiValueMap3.add("courseProfessor", student3.getCourseProfessor());
	    multiValueMap3.add("studentEmail", student3.getStudentEmail());
	    multiValueMap3.add("studentCourse", student3.getStudentCourse());
	    
		mockMvc.perform(
				post("/process_addStudent/{courseId}",(long) 5)
				.params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/browseStd/"+(long) 5));
		
		mockMvc.perform(
				post("/process_addStudent/{courseId}",(long) 3)
				.params(multiValueMap2))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/add_Student/"+(long) 3));
		
		mockMvc.perform(
				post("/process_addStudent/{courseId}",(long) 4)
				.params(multiValueMap3))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/add_Student/"+(long) 4));
	}
	
	@WithMockUser(value="Mamoulis@gmail.com")
	@Test
    @Transactional 
    @Rollback(true)
	void testEditStudentReturnsPage() throws Exception {      

		mockMvc.perform(
				post("/editStudent/{studentsId}",(long) 11))
				.andExpect(status().isOk())
				.andExpect(view().name("edit_student"));		
	}
	
	@WithMockUser(value="Mamoulis@gmail.com")
	@Test
    @Transactional 
    @Rollback(true)
	void testSaveStudentReturnsPage() throws Exception {      

	    Students student = new Students((long)6,"lola", "lola", "Mamoulis@gmail.com","lola@gmail.com","Java");
	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("studentsId", Long.toString(student.getStudentsId()));
	    multiValueMap.add("firstName", student.getFirstName());
	    multiValueMap.add("lastName", student.getLastName());
	    multiValueMap.add("courseProfessor", student.getCourseProfessor());
	    multiValueMap.add("studentEmail", student.getStudentEmail());
	    multiValueMap.add("studentCourse", student.getStudentCourse());
	    
		mockMvc.perform(
				post("/saveStudent")
				.params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/browseStd/"+(long) 3));		
	}
	
	@WithMockUser(value="Mamoulis@gmail.com")
	@Test
    @Transactional 
    @Rollback(true)
	void testGradesReturnsPage() throws Exception {      

		mockMvc.perform(
				post("/grades/{courseId}",(long) 5))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("GradesList","fullname"))
				.andExpect(view().name("grades"));		
	}
	
	@WithMockUser(value="Mamoulis@gmail.com")
	@Test
    @Transactional 
    @Rollback(true)
	void testEditGradesReturnsPage() throws Exception {      

		mockMvc.perform(
				post("/editGrades/{studentsId}",(long) 11))
				.andExpect(status().isOk())
				.andExpect(view().name("edit_grades"));		
	}
	
	@WithMockUser(value="Mamoulis@gmail.com")
	@Test
    @Transactional 
    @Rollback(true)
	void testSaveStudentGradeReturnsPage() throws Exception {      

	    Students student = new Students((long)6,"lola", "lola", "Mamoulis@gmail.com","lola@gmail.com","Java");
	    
	    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
	    multiValueMap.add("studentsId", Long.toString(student.getStudentsId()));
	    multiValueMap.add("firstName", student.getFirstName());
	    multiValueMap.add("lastName", student.getLastName());
	    multiValueMap.add("courseProfessor", student.getCourseProfessor());
	    multiValueMap.add("studentEmail", student.getStudentEmail());
	    multiValueMap.add("studentCourse", student.getStudentCourse());
	    
		mockMvc.perform(
				post("/saveStudentGrade")
				.params(multiValueMap))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/grades/"+(long) 3));		
	}
}
