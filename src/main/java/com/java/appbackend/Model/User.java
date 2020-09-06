package com.java.appbackend.Model;

import javax.persistence.*;

import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="username")
    private String username; 
    
    @Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "password")
	@Transient
	private String password;
	
	@Column(name = "passwordConfirm")
	@Transient
    private String passwordConfirm;

    @ManyToMany
    private Set<Role> roles;  
    
}
