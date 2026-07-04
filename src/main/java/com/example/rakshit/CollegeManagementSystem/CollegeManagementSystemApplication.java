package com.example.rakshit.CollegeManagementSystem;

import com.example.rakshit.CollegeManagementSystem.ProfileDemo.ProfileServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
public class CollegeManagementSystemApplication implements CommandLineRunner {

//	private final ProfileServiceImp profileService;

//	@Value("${my.name}")
//	private String myName;

	public static void main(String[] args) {
		SpringApplication.run(CollegeManagementSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		System.out.println(profileService.showProfile());
//		System.out.println(myName);
	}

}
