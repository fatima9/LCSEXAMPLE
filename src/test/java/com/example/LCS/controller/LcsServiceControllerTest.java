package com.example.LCS.controller;

import static com.example.LCS.util.LcsServiceConstants.LCS_SERVICE_URI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.LCS.exception.LcsServiceException;
import com.example.LCS.exception.LcsServiceValidationException;
import com.example.LCS.model.LcsRequest;
import com.example.LCS.model.LcsResponse;
import com.example.LCS.model.SetOfString;
import com.example.LCS.service.LcsService;
import com.example.LCS.util.LcsTestData;
import com.example.LCS.validator.LcsServiceValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LcsServiceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LcsService lcsService;

	@MockBean
	private LcsServiceValidator lcsServiceValidator;

	private final ObjectMapper mapper = new ObjectMapper();

	private LcsRequest lcsRequest;

	private LcsResponse lcsResponse;

	@Autowired
	private LcsTestData lcsTestData;

	@BeforeEach
	public void setUp() {
		lcsRequest = lcsTestData.getLcsRequest();
		lcsResponse = lcsTestData.getLcsResponse();
	}

	@Test
	@DisplayName("Generate LCS Success Scenario")
	public void generateLCSSOnSuccessTest() throws JsonProcessingException, Exception {
		doNothing().when(lcsServiceValidator).validateLcsRequest(any(LcsRequest.class));
		doReturn(lcsResponse).when(lcsService).generateLCS(any(LcsRequest.class));
		MockHttpServletResponse response = mockMvc.perform(post(LCS_SERVICE_URI)
				.content(mapper.writeValueAsString(lcsRequest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse();
		assertEquals(200, response.getStatus());
	}

	@Test
	@DisplayName("Generate LCS Bad Request On Null Body")
	public void generateLCSBadRequesOnNullBodyTest() throws JsonProcessingException, Exception {
		lcsRequest.setSetOfStrings(null);
		MockHttpServletResponse response = mockMvc
				.perform(post(LCS_SERVICE_URI).content(mapper.writeValueAsString(lcsRequest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse();
		assertEquals(400, response.getStatus());
	}

	@Test
	@DisplayName("Generate LCS Bad Request On Empty Body")
	public void generateLCSBadRequesOnEmptyBodyTest() throws JsonProcessingException, Exception {
		List<SetOfString> setOfStrings = new ArrayList<SetOfString>();
		lcsRequest.setSetOfStrings(setOfStrings);
		MockHttpServletResponse response = mockMvc
				.perform(post(LCS_SERVICE_URI).content(mapper.writeValueAsString(lcsRequest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse();
		assertEquals(400, response.getStatus());
	}

	@Test
	@DisplayName("Generate LCS Bad Request On No Body")
	public void generateLCSBadRequesOnNoBodyTest() throws JsonProcessingException, Exception {
		MockHttpServletResponse response = mockMvc.perform(post(LCS_SERVICE_URI)).andExpect(status().isBadRequest())
				.andReturn().getResponse();
		assertEquals(400, response.getStatus());
	}

	@Test
	@DisplayName("Generate LCS Bad Request On Wrong Body")
	public void generateLCSBadRequesOnWrongBodyTest() throws JsonProcessingException, Exception {
		MockHttpServletResponse response = mockMvc
				.perform(post(LCS_SERVICE_URI).content(mapper.writeValueAsString("test"))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse();
		assertEquals(400, response.getStatus());
	}

	@Test
	@DisplayName("Generate LCS Bad Request On Null Value")
	public void generateLCSBadRequesOnNullValueTest() throws JsonProcessingException, Exception {
		lcsRequest.getSetOfStrings().get(0).setValue(null);
		MockHttpServletResponse response = mockMvc
				.perform(post(LCS_SERVICE_URI).content(mapper.writeValueAsString(lcsRequest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse();
		assertEquals(400, response.getStatus());
	}

	@Test
	@DisplayName("Generate LCS Bad Request On Empty Value")
	public void generateLCSBadRequesOnEmptyValueTest() throws JsonProcessingException, Exception {
		lcsRequest.getSetOfStrings().get(0).setValue(" ");
		MockHttpServletResponse response = mockMvc
				.perform(post(LCS_SERVICE_URI).content(mapper.writeValueAsString(lcsRequest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse();
		assertEquals(400, response.getStatus());
	}

	@Test
	@DisplayName("Generate LCS Validation Exception")
	public void generateLCSValidationExceptionTest() throws JsonProcessingException, Exception {
		doThrow(LcsServiceValidationException.class).when(lcsServiceValidator)
				.validateLcsRequest(any(LcsRequest.class));
		MockHttpServletResponse response = mockMvc
				.perform(post(LCS_SERVICE_URI).content(mapper.writeValueAsString(lcsRequest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn().getResponse();
		assertEquals(400, response.getStatus());
	}

	@Test
	@DisplayName("Generate LCS Service Exception")
	public void generateLCSServiceExceptionTest() throws JsonProcessingException, Exception {
		doNothing().when(lcsServiceValidator).validateLcsRequest(any(LcsRequest.class));
		doThrow(LcsServiceException.class).when(lcsService).generateLCS(any(LcsRequest.class));
		MockHttpServletResponse response = mockMvc
				.perform(post(LCS_SERVICE_URI).content(mapper.writeValueAsString(lcsRequest))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError()).andReturn().getResponse();
		assertEquals(500, response.getStatus());
	}

	@Test
	@DisplayName("Generate LCS On Unimplemented method")
	public void generateLCSUnImplementedMethodTest() throws JsonProcessingException, Exception {
		MockHttpServletResponse response = mockMvc.perform(get(LCS_SERVICE_URI))
				.andExpect(status().isMethodNotAllowed()).andReturn().getResponse();
		assertEquals(405, response.getStatus());
	}

}
