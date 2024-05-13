package com.toy.filterloginpjt;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.filterloginpjt.dto.SignUpRequestDTO;
import com.toy.filterloginpjt.dto.SignUpResponseDTO;
import com.toy.filterloginpjt.entity.User;
import com.toy.filterloginpjt.redis.RedisDao;
import com.toy.filterloginpjt.repository.UserRepository;
import com.toy.filterloginpjt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class FilterloginpjtApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RedisDao redisDao;

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

		User user = userRepository.findByEmail("test@example.com");
		userRepository.delete(user);
	}

	@Test
	public void testSignIn() throws Exception {
		// given
		String SignUpRequest = "{\"email\": \"test@example.com\", " +
				"\"name\": \"testName\", " +
				"\"password\": \"testPassword\"}";

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signup")
						.contentType(MediaType.APPLICATION_JSON)
						.content(SignUpRequest))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		// when
		String SignInRequest = "{\"email\": \"test@example.com\", " +
				"\"password\": \"testPassword\"}";
		MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signin")
						.contentType(MediaType.APPLICATION_JSON)
						.content(SignInRequest))
				.andExpect(MockMvcResultMatchers.status().isAccepted())
				.andReturn()
				.getResponse();

		System.out.println(redisDao.getValues("test@example.com"));
		System.out.println("================================================");
		// then
		assertThat(redisDao.getValues("test@example.com")).isNotEmpty();

	}
}
