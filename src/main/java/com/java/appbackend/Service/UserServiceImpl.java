package com.java.appbackend.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.appbackend.Model.User;
import com.java.appbackend.Repository.RoleRepository;
import com.java.appbackend.Repository.UserRepository;
import com.java.appbackend.Repository.UserServiceRepository;

@Service
public class UserServiceImpl implements UserServiceRepository {
	
	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

	@Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

	@Override
	public User findByToken(String token) {
		return userRepository.findByToken(token);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public String forgotPassword(String email) {
		Optional<User> userOptional = Optional
				.ofNullable(userRepository.findByEmail(email));

		if (!userOptional.isPresent()) {
			return "Invalid email id.";
		}

		User user = userOptional.get();
		user.setToken(generateToken());
		user.setTokenCreationDate(LocalDateTime.now());

		user = userRepository.save(user);

		return user.getToken();
	}

	@Override
	public String resetPassword(String token, String password) {

		Optional<User> userOptional = Optional
				.ofNullable(userRepository.findByToken(token));

		if (!userOptional.isPresent()) {
			return "Invalid token.";
		}

		LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

		if (isTokenExpired(tokenCreationDate)) {
			return "Invalid Token.Token seems to be expired.";

		}

		User user = userOptional.get();

		user.setPassword(bCryptPasswordEncoder.encode(password));
		user.setToken(null);
		user.setTokenCreationDate(null);

		userRepository.save(user);

		return "Your password successfully updated.";
	}
	
	private String generateToken() {
		StringBuilder token = new StringBuilder();

		return token.append(UUID.randomUUID().toString())
				.append(UUID.randomUUID().toString()).toString();
	}
	
	private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(tokenCreationDate, now);

		return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
	}
}
