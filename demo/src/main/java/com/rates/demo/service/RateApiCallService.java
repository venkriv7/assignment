package com.rates.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rates.demo.dto.CurrencyRatesDTO;

@Service
public interface RateApiCallService {


	/**
	 * Method is used to fetch current day Rates against to EURO. This is configured with retry mechanism in case if any failure has happened
	 * while getting the response from rates api from external system.
	 * @return list of Currency Rates DTO
	 */
	public List<CurrencyRatesDTO> fetchTodayRates();
	
	/**
	 * Method is used to fetch current day Rates against to EURO by using input parameter dates. The input parameters are fetched from current h2 database
	 * who's rates are missed in the system. This is configured with retry mechanism in case if any failure has happened
	 * while getting the response from rates api from external system.
	 * @return list of Currency Rates DTO
	 */
	public List<CurrencyRatesDTO> fetchCurrencyRates(List<LocalDate> dates);
	
	/**
	 * This method is used to fetch dates not exist in the database by using input parameter dates. The input parameters are fetched from current h2 database
	 * who's rates are missed in the system. 
	 * @return list of Dates
	 */
	public List<LocalDate> fetchLastNthMonthCurrencyRates(int months);
	


}
