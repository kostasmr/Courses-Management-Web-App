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

import com.example.demo.user.CustomUserDetailsService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
 
@SpringBootTest
public class UserServiceTests {
     
    @Autowired
    private CustomUserDetailsService userService;
    
    @Autowired
    private UserRepository repo;
    
    @Test
    public void testGetFullName(){
    	//before
    	String email ="Mamoulis@gmail.com";
    	User user =repo.findByEmail(email);
    	
    	//then
    	String name = userService.getFullName(user);
    	
    	//after
    	assertThat(name).isNotNull();
    	assertEquals(name,"Nikolaos Mamoulis");
    	
    }
}
