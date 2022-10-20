package com.example.demo;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.user.CustomUserDetailsService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
 
    @Autowired
    private TestEntityManager entityManager;
     
    @Autowired
    private UserRepository repo;
    
     
    @Test
    @Transactional 
    @Rollback(true)
    public void testCreateUser() {
        User user = new User();
        user.setEmail("Liaskos@gmail.com");
        user.setPassword("123456");
        user.setFirstName("Xristos");
        user.setLastName("Liaskos");
         
        User savedUser = repo.save(user);
         
        User existUser = entityManager.find(User.class, savedUser.getId());
         
        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
         
    }
    
    @Test
    public void testFindUserByEmail(){
    	//before
    	String email ="Mamoulis@gmail.com";
    	
    	//then
    	User user =repo.findByEmail(email);
    	
    	//after
    	assertThat(user).isNotNull();
    	assertEquals(user.getFirstName(),"Nikolaos");
    	
    }
    
    @Test
    public void testFindUserEmail(){
        //before
    	String exist_email = "Anastasiadis@gmail.com";
    	String non_exist_email = "markou@gmail.com";
    	
    	//then
    	String[] emails =repo.findUsersEmail();
    	ArrayList<String> all_emails = new ArrayList<String>();
    	
    	for(int i=0;i<emails.length;i++)
    	{
    		all_emails.add(emails[i]);
    	}
    	
    	//then
    	assertThat(emails).isNotNull();
    	assertEquals(emails.length,3);
    	assertTrue(all_emails.contains(exist_email));
    	assertFalse(all_emails.contains(non_exist_email));
    }
    
}
