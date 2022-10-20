package com.example.demo;

import java.util.List;
import java.lang.Math;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.course.Course;
import com.example.demo.course.CourseService;
import com.example.demo.course.CoursesRepository;
import com.example.demo.student.StudentService;
import com.example.demo.student.Students;
import com.example.demo.student.StudentsRepository;
import com.example.demo.user.CustomUserDetails;
import com.example.demo.user.CustomUserDetailsService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
 
@Controller
public class AppController {
 
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private CoursesRepository courseRepo;
    
    @Autowired
    private StudentsRepository studentsRepo;
    
    @Autowired
    private CustomUserDetailsService userService;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }
    
    ////REGISTER 
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
         
        return "signup_form";
    }
    @PostMapping("/process_register")
    public String showProcessRegister(User user,RedirectAttributes ra) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        String[] emails = userRepo.findUsersEmail();
        for(int i=0;i<emails.length;i++)
        {
        	if(emails[i].equals(user.getEmail()))
        	{
        		ra.addFlashAttribute("error_message", "This email exists."); 
        		return "redirect:/register";
        	}
        }
        List<User> users = userRepo.findAll();
        for(int i=0;i<users.size();i++)
        {
        	String fullname = userService.getFullName(users.get(i));
        	if(fullname.equals(userService.getFullName(user)))
        	{
        		ra.addFlashAttribute("error_message", "This full name exists."); 
        		return "redirect:/register";
        	}
        }
        userRepo.save(user);
        
        ra.addFlashAttribute("message", "You have signed up successfully!"); 
        return "redirect:/";
    }
    
    ////USER HOME PAGE
    @GetMapping("/users")
    public String viewUserHomePage(Model model) {
        List<Course> listCourses = courseRepo.findByProfessor(userService.getUserName());
        User user = userRepo.findByEmail(userService.getUserName());
        String fullname = userService.getFullName(user);
        model.addAttribute("listCourses", listCourses);
        model.addAttribute("fullname", fullname);
        
        return "users";
    }
    
    ////ADD A COURSE
    @GetMapping("/addCourse")
    public String showAddCourseForm(Model model) {
    	User user = userRepo.findByEmail(userService.getUserName());
        String fullname = userService.getFullName(user);
        model.addAttribute("fullname", fullname);
        model.addAttribute("course", new Course());
         
        return "addCourse_form";
    }
    @PostMapping("/process_addCourse")
    public String processAddCourse(Course course,RedirectAttributes ra) {
        String[] courses = courseRepo.findAllCoursesName();
        String[] prof_courses = courseRepo.findCoursesName(userService.getUserName());
        for(int i=0;i<prof_courses.length;i++)
        {
        	if(prof_courses[i].equals(course.getCourseName()))
        	{
                ra.addFlashAttribute("error_message", "This course is already in your list."); 
        		return "redirect:/addCourse";
        	}
        }
        for(int j=0;j<courses.length;j++)
        {
        	if(courses[j].equals(course.getCourseName()))
        	{
                ra.addFlashAttribute("error_message", "This course exist."); 
        		return "redirect:/addCourse";
        	}
        }
        course.setProfessorName(userService.getUserName());
        course.setPercentage((float) 0.5);
        courseRepo.save(course);
        ra.addFlashAttribute("message", "The course '"+course.getCourseName()+"' has been added successfully."); 
        
        return "redirect:/users";
    }
    
    /////BROWSE STUDENT FROM A COURSE
    @RequestMapping("/browseStd/{courseId}")
    public ModelAndView showStudentsListPage(@PathVariable(name = "courseId") Long courseId) {
        ModelAndView mav = new ModelAndView("browse_student");
        User user = userRepo.findByEmail(userService.getUserName());
        String fullname = userService.getFullName(user);
        Course course = courseService.get(courseId);
        String courseName = course.getCourseName();
        List<Students> listStudents = studentsRepo.findByProfessorCourse(userService.getUserName(),courseName);
        mav.addObject("listStudents", listStudents);
        mav.addObject("courseId", courseId);
        mav.addObject("fullname", fullname);
        return mav;
    }
    
    /////////////STATISTICS OF A COURSE
    @RequestMapping("/statistics/{courseId}")
    public ModelAndView showEditCourseStats(@PathVariable(name = "courseId") Long courseId) {
        ModelAndView mav = new ModelAndView("course_stats");
        User user = userRepo.findByEmail(userService.getUserName());
        String fullname = userService.getFullName(user);
        DescriptiveStatistics stats = new DescriptiveStatistics();
        Course course = courseService.get(courseId);
        String courseName = course.getCourseName();
        List<Students> students = studentsRepo.findByProfessorCourse(userService.getUserName(), courseName);
        if(students.isEmpty())
        {
        	return mav;
        }
        float student_max = studentsRepo.findMaxGrade(userService.getUserName(),courseName);
        float student_min = studentsRepo.findMinGrade(userService.getUserName(),courseName);
        float sum = studentsRepo.findSumOfGrades(userService.getUserName(),courseName);
        float count = studentsRepo.findCountGrades(userService.getUserName(),courseName);
        float mean = sum/count;
        mean = (float) (Math.round(mean*1000.0)/1000.0);
        float[] grades = studentsRepo.findAllGrades(userService.getUserName(),courseName);
        for(int i = 0; i<grades.length;i++)
        {
        	stats.addValue(grades[i]);
        }
        double standard_deviation = stats.getStandardDeviation();
        standard_deviation = (double) (Math.round(standard_deviation*1000.0)/1000.0);
        double median = stats.getPercentile(50);
        median = (double) (Math.round(median*1000.0)/1000.0);
        double variance = stats.getVariance();
        variance = (double) (Math.round(variance*1000.0)/1000.0);
        double percentiles = stats.getPercentile(50);
        percentiles = (double) (Math.round(percentiles*1000.0)/1000.0);
        double skewness = stats.getSkewness();
        skewness = (double) (Math.round(skewness*1000.0)/1000.0);
        double kurtosis = stats.getKurtosis();
        kurtosis = (double) (Math.round(kurtosis*1000.0)/1000.0);
        mav.addObject("student_max", student_max);
        mav.addObject("student_min", student_min);
        mav.addObject("mean", mean);
        mav.addObject("standard_deviation", standard_deviation);
        mav.addObject("variance", variance);
        mav.addObject("percentiles", percentiles);
        mav.addObject("skewness", skewness);
        mav.addObject("kurtosis", kurtosis);
        mav.addObject("median", median);
        mav.addObject("fullname", fullname);
         
        return mav;
    }
    
    ///////////////////////////COURSE///////////////////////////////////////
    //DELETE
    @RequestMapping("/delete/{courseId}")
    public String deleteCourse(@PathVariable(name = "courseId") long courseId,RedirectAttributes ra) {
    	Course course = courseService.get(courseId);
    	ra.addFlashAttribute("message", "The course '"+course.getCourseName()+"' has been deleted successfully.");
    	studentService.deleteStudentsByCourse(course.getCourseName());
    	courseService.delete(courseId);
    	return "redirect:/users";       
    }
    //EDIT
    @RequestMapping(value ="/save", method = RequestMethod.POST)
    public String saveCourse(@ModelAttribute("course") Course course,RedirectAttributes ra) {
    	courseRepo.save(course);
    	ra.addFlashAttribute("message", "The course has been saved successfully.");
        return "redirect:/users";       
    }
    @RequestMapping("/edit/{courseId}")
    public ModelAndView showEditCoursePage(@PathVariable(name = "courseId") Long courseId) {
        ModelAndView mav = new ModelAndView("edit_course");
        User user = userRepo.findByEmail(userService.getUserName());
        String fullname = userService.getFullName(user);
        Course course = courseService.get(courseId);
        mav.addObject("course", course);
        mav.addObject("fullname", fullname);
         
        return mav;
    }
    
    
    
    //////////////////STUDENT//////////////////////
    //DELETE
    @RequestMapping("/deleteStudent/{studentsId}")
    public String deleteStudent(@PathVariable(name = "studentsId") Long studentsId,RedirectAttributes ra) {
    	Students student = studentsRepo.findByStudentId(studentsId);
    	Course course = courseRepo.findByCourseName(student.getStudentCourse());
    	Long courseId = course.getCourseId();
    	String student_name = studentService.getFullName(student);
    	studentService.delete(studentsId);
        ra.addFlashAttribute("message", "The student '"+student_name+"' has been deleted successfully."); 

        return "redirect:/browseStd/"+courseId;       
    }
    //ADD A STUDENT 
    @RequestMapping("/add_Student/{courseId}")
    public ModelAndView showAddStudentPage2(@PathVariable(name = "courseId") Long courseId) {
    	ModelAndView mav = new ModelAndView("add_student");
        User user = userRepo.findByEmail(userService.getUserName());
        String fullname = userService.getFullName(user);
        Students student = new Students();
        mav.addObject("student", student);
        mav.addObject("fullname", fullname);
        
        return mav;
    }
    
    @PostMapping("/process_addStudent/{courseId}")
    public String processAddStudent(@PathVariable(name = "courseId") Long courseId,Students student,RedirectAttributes ra) {
        Course course1 = courseService.get(courseId);
        String courseName = course1.getCourseName();
        //check for duplicate student full name or email
        Course course = courseRepo.findByCourse(courseId);
        List<Students> students = studentsRepo.findByProfessorCourse(userService.getUserName(), course.getCourseName());
        for(int i=0;i<students.size();i++)
        {
        	String fullname = studentService.getFullName(students.get(i));
        	if(fullname.equals(studentService.getFullName(student)))
        	{
                ra.addFlashAttribute("error_message", "This full name exists."); 
        		return "redirect:/add_Student/"+courseId;
        	}
        	String email = students.get(i).getStudentEmail();
        	if(email.equals(student.getStudentEmail()))
        	{
                ra.addFlashAttribute("error_message", "This email exists."); 
        		return "redirect:/add_Student/"+courseId;
        	}
        }
        student.setCourseProfessor(userService.getUserName());
        student.setStudentCourse(courseName);
        studentsRepo.save(student);
        String student_name= studentService.getFullName(student);
        ra.addFlashAttribute("message", "The student '"+student_name+"' has been added successfully."); 


        return "redirect:/browseStd/"+courseId;   
    }
   
    //EDIT 
    @RequestMapping(value ="/saveStudent", method = RequestMethod.POST)
    public String saveStudent(@ModelAttribute("student") Students student,RedirectAttributes ra) {
    	studentsRepo.save(student);
    	Course course = courseRepo.findByCourseName(student.getStudentCourse());
    	Long courseId = course.getCourseId();
        ra.addFlashAttribute("message", "The student has been saved successfully."); 

        return "redirect:/browseStd/"+courseId;       
    }
    @RequestMapping("/editStudent/{studentsId}")
    public ModelAndView showEditStudentPage(@PathVariable(name = "studentsId") Long studentId) {
        ModelAndView mav = new ModelAndView("edit_student");
        User user = userRepo.findByEmail(userService.getUserName());
        String fullname = userService.getFullName(user);
        Students student = studentService.get(studentId);
    	Course course = courseRepo.findByCourseName(student.getStudentCourse());
    	Long courseId = course.getCourseId();
        mav.addObject("student", student);
        mav.addObject("courseId", courseId);
        mav.addObject("fullname", fullname);
        
        return mav;
    }
    
    
    /////////GRADES
    @RequestMapping("/grades/{courseId}")
    public String showGrades(Model model,@PathVariable(name = "courseId") long courseId) {
        User user = userRepo.findByEmail(userService.getUserName());
        String fullname = userService.getFullName(user);
        Course course = courseRepo.findByCourse(courseId);
        float project = course.getPercentage();
        float exams = (float) 1-project;
        List<Students> GradesList = studentsRepo.findByProfessorCourse(userService.getUserName(),course.getCourseName());
        for(int i=0;i<GradesList.size();i++)
        {
        	GradesList.get(i).setCourseGrade(project,exams);
        	studentsRepo.save(GradesList.get(i));
        	
        }
        model.addAttribute("GradesList", GradesList);
        model.addAttribute("fullname", fullname);

        return "grades";
    }
   @RequestMapping("/editGrades/{studentsId}")
   public ModelAndView showEditGradesPage(@PathVariable(name = "studentsId") long studentsId) {
       ModelAndView mav = new ModelAndView("edit_grades");
       User user = userRepo.findByEmail(userService.getUserName());
       String fullname = userService.getFullName(user);
       Students student= studentService.get(studentsId);
       Course course = courseRepo.findByCourseName(student.getStudentCourse());
   	   Long courseId = course.getCourseId(); 
       mav.addObject("student", student);
       mav.addObject("courseId", courseId);
       mav.addObject("fullname", fullname);

       return mav;
   }
   @RequestMapping(value ="/saveStudentGrade", method = RequestMethod.POST)
   public String saveStudentGrade(@ModelAttribute("student") Students student,RedirectAttributes ra) {
	   studentsRepo.save(student);
	   Course course = courseRepo.findByCourseName(student.getStudentCourse());
	   Long courseId = course.getCourseId();
	   ra.addFlashAttribute("message", "Grades has been updated successfully."); 
		
	   return "redirect:/grades/"+courseId;       
   }

}