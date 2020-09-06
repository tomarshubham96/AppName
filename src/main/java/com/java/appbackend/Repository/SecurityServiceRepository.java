package com.java.appbackend.Repository;

public interface SecurityServiceRepository {
	
	String findLoggedInUsername();

    void autoLogin(String username, String password);
}
