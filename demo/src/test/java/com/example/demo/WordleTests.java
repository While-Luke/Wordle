package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class WordleTests {

	@Autowired
	private MockMvc mvc;

	ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	public void setup(){
		//set word
	}

	@Test
	void assertBadRequest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/?guess=bad").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void allCorrectGuess() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/?guess=hello").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		String[] expectedResponse = new String[]{"G", "G", "G", "G", "G"};
		String[] response = mapper.readValue(mvcResult.getResponse().getContentAsString(), String[].class);

		assertArrayEquals(expectedResponse, response);
	}

	@Test
	void allNoneGuess() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/?guess=aaaaa").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		String[] expectedResponse = new String[]{"N", "N", "N", "N", "N"};
		String[] response = mapper.readValue(mvcResult.getResponse().getContentAsString(), String[].class);

		assertArrayEquals(expectedResponse, response);
	}

	@Test
	void allYellowGuess() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/?guess=llohe").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		String[] expectedResponse = new String[]{"Y", "Y", "Y", "Y", "Y"};
		String[] response = mapper.readValue(mvcResult.getResponse().getContentAsString(), String[].class);

		assertArrayEquals(expectedResponse, response);
	}

	@Test
	void oneYellowOneGreenGuess() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/?guess=aalal").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		String[] expectedResponse = new String[]{"N", "N", "G", "N", "Y"};
		String[] response = mapper.readValue(mvcResult.getResponse().getContentAsString(), String[].class);

		assertArrayEquals(expectedResponse, response);
	}

	@Test
	void oneGreenOneWrongGuess() throws Exception {
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/?guess=haaah").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		String[] expectedResponse = new String[]{"G", "N", "N", "N", "N"};
		String[] response = mapper.readValue(mvcResult.getResponse().getContentAsString(), String[].class);

		assertArrayEquals(expectedResponse, response);
	}
}
