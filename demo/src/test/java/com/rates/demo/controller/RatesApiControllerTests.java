package com.rates.demo.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.rates.demo.service.RateApiCallService;
import com.rates.demo.service.RatesService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RatesApiController.class)
public class RatesApiControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RateApiCallService rateApiCallService;

	@MockBean
	private RatesService ratesService;

	@Test
	public void whenGetRequestToUsers_thenCorrectResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/getCurrentDateRates").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void whenFetchRatesFromSystem_thenCorrectResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/fetchRatesFromSystem").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void whenGetRatesForBetweenDates_thenCorrectResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/getRatesForBetweenDates/2020-03-03/2020-03-05").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void whenGetMissingDatesFromSystem_thenCorrectResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/getMissingDatesFromSystem/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void whenGetMissingDatesFromSystem_then4XXResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/getMissingDatesFromSystem/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Test
	public void whenGetRatesForMonths_thenCorrectResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/getRatesForMonths/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void whenGetRatesForMonths_then4XXResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/getRatesForMonths/").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
