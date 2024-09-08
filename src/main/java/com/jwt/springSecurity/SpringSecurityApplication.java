package com.jwt.springSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jwt.springSecurity.entities.Role;
import com.jwt.springSecurity.entities.User;
import com.jwt.springSecurity.repository.UserRepository;

@SpringBootApplication
public class SpringSecurityApplication implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository; 
	
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
       User adminAccount = userRepository.findByRole(Role.ADMIN);
       if(null == adminAccount) {
    	   User user = new User();
    	   user.setFirstName("admin");
    	   user.setLastName("admin");
    	   user.setEmail("admin@gmail.com");
    	   user.setRole(Role.ADMIN);
    	   user.setPassword(new BCryptPasswordEncoder().encode("admin"));
    	   userRepository.save(user);
       }
	}
}
