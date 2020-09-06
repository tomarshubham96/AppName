package com.java.appbackend.Repository;

import com.java.appbackend.Model.User;

public interface UserServiceRepository {

	void save(User user);

    User findByUsername(String username);
    
    User findByEmail(String email);
    
    User findByToken(String token);
    
    String forgotPassword(String email);
    
    String resetPassword(String token, String password);
}
