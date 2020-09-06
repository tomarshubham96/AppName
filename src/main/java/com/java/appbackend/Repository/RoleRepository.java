package com.java.appbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.appbackend.Model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
