package com.mysideproj.sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


// exclude = DataSourceAutoConfiguration.class : 현재 DB연결없어서 관련설정 exclude
//exclude = DataSourceAutoConfiguration.class
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnsApplication.class, args);
	}

}
