package com.java.appbackend.Model;

import javax.persistence.*;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="username", unique = true)
    private String username; 
    
    @Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "passwordConfirm")
	@Transient
    private String passwordConfirm;
	
	private String token;
	
	@Column(columnDefinition = "TIMESTAMP")	
	private LocalDateTime tokenCreationDate;

    @ManyToMany
    private Set<Role> roles;  
    
}
