package com.it.securityexample;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.it.securityexample.employee.EmployeeEntity;
import com.it.securityexample.employee.EmployeeService;
import com.it.securityexample.user.UserService;

@SpringBootApplication
public class SecurityexampleApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SecurityexampleApplication.class, args);
	}

	@Autowired
	private EmployeeService service;

	@Autowired
	private UserService userService;

	@Override
	public void run(String... args) throws Exception {

		userService.createDefaultUser();

		for (EmployeeEntity e : List.of(
				new EmployeeEntity("Aarav Kumar", "aarav.kumar@example.com", "HR", "Tech Innovations Pvt Ltd"),
				new EmployeeEntity("Diya Sharma", "diya.sharma@example.com", "Marketing", "Creative Minds Ltd"),
				new EmployeeEntity("Rohan Gupta", "rohan.gupta@example.com", "Finance", "Financial Solutions Inc"),
				new EmployeeEntity("Isha Patel", "isha.patel@example.com", "IT", "Tech Solutions Pvt Ltd"),
				new EmployeeEntity("Aditya Singh", "aditya.singh@example.com", "Operations", "Manufacturing Corp"))) {
			service.save(e);
		}

	}

}
