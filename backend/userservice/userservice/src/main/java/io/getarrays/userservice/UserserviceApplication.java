package io.getarrays.userservice;

import io.getarrays.userservice.domain.AppUser;
import io.getarrays.userservice.domain.Role;
import io.getarrays.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;


@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	CommandLineRunner run(UserService userService){
//		return args -> {
//			userService.getUsersByPage(1);
////			userService.saveRole(new Role(null, "ROLE_USER"));
////			userService.saveRole(new Role(null, "ROLE_ADMIN"));
//////			userService.saveRole(new Role(null, "SUPER_ADMIN"));
//////
//////			userService.saveUser(new AppUser(null, "Joni9", "Joni", "Nihu", "1234", "balc_horia@yahoo.com", new ArrayList<>()));
//////			userService.saveUser(new AppUser(null, "Jonica9", "Jonica", "Nine", "1234", "balc_horia@yahoo.com", new ArrayList<>()));
//////			userService.saveUser(new AppUser(null, "Jon9", "Jon", "Nines", "1234", "robertbakk@yahoo.com", new ArrayList<>()));
//////
//////			userService.addRoleToUser("Jon9", "ROLE_USER");
//////			userService.addRoleToUser("Jon9", "ROLE_ADMIN");
//////			userService.addRoleToUser("Joni9", "ROLE_MANAGER");
//////			userService.addRoleToUser("Jonica9", "ROLE_USER");
//////
//////
//////
//	};
//
//	}

}
