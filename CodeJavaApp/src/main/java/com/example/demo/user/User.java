package com.example.demo.user;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "users")
public class User {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
    @Column(nullable = false, unique = true, length = 45)
    private String email;
     
    @Column(nullable = false, length = 64)
    private String password;
     
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;
     
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

	public User() {
	}
	
	public User(long id, String firstName, String lastName, String email, String password) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.firstName=firstName;
		this.lastName=lastName;
		this.email=email;
		this.password=password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id=id;
	}
	
	public String getEmail() {
		return email;
	}
	
    public void setEmail(String email) {
		this.email=email;
		
	}

	public void setPassword(String password) {
		this.password=password;
		
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
	public String getPassword() {
		return password;
	}
	public String getLastName() {
		return lastName;
	}
}