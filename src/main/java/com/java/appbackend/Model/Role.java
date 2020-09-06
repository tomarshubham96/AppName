package com.java.appbackend.Model;

import javax.persistence.*;

import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="roleDesc")
    private String roleDesc;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;    
}
