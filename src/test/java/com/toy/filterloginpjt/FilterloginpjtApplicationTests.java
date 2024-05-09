package com.toy.filterloginpjt;

import com.toy.filterloginpjt.dto.SignUpRequestDTO;
import com.toy.filterloginpjt.dto.SignUpResponseDTO;
import com.toy.filterloginpjt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class FilterloginpjtApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	public void testSignUp() throws Exception {
		// given
		String jsonRequest = "{\"email\": \"test@example.com\", " +
				"\"name\": \"testName\", " +
				"\"password\": \"testPassword\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

}
