package com.java.appbackend.Repository;

import com.java.appbackend.Model.User;

public interface UserServiceRepository {

	void save(User user);

    User findByUsername(String username);
}
