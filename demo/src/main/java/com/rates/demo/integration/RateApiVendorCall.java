package com.rates.demo.integration;

import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rates.demo.exceptions.ClientException;
import com.rates.demo.exceptions.NotFoundException;
import com.rates.demo.exceptions.ServerException;
import com.rates.demo.util.ApplicationConstants;

@Service
public class RateApiVendorCall {

	@Autowired
	RestTemplate restTemplate;

	/**
	 * Method is used to fetch current day Rates against to EURO. This is configured with retry mechanism in case if any failure has happened
	 * while getting the response from rates api from external system.
	 * @return list of Currency Rates DTO
	 */
	@SuppressWarnings("unchecked")
	@Retryable(value = { UnknownHostException.class, ClientException.class, ServerException.class,
			NotFoundException.class }, maxAttempts = 5, backoff = @Backoff(delay = 1000))
	public LinkedHashMap<String, Object> fetchTodayRatesApiCall() {
		return restTemplate.getForObject(
						ApplicationConstants.VENDOR_URL + ApplicationConstants.TODAY_RATE, LinkedHashMap.class);
	}
	
	/**
	 * Method is used to fetch current day Rates against to EURO. This is configured with retry mechanism in case if any failure has happened
	 * while getting the response from rates api from external system.
	 * @return list of Currency Rates DTO
	 */
	@SuppressWarnings("unchecked")
	@Retryable(value = { UnknownHostException.class, ClientException.class, ServerException.class,
			NotFoundException.class }, maxAttempts = 5, backoff = @Backoff(delay = 1000))
	public LinkedHashMap<String, Object> fetchSpecifcRatesApiCall(LocalDate date) {
		return restTemplate.getForObject(
						ApplicationConstants.VENDOR_URL + date, LinkedHashMap.class);
	}
}
