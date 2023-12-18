package com.mysideproj.sns;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc // 테스트용 목 MVC
class SnsApplicationTests {

	@Autowired
    private MockMvc mockMvc;
	@Test
	void signup() {
		String userName = "userName";
		String password = "password";


        mockMvc.perform(post("/api/v1/users/join"))
				.contentType()
	}

}
