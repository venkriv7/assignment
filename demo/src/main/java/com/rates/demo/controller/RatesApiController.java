package com.rates.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rates.demo.dto.CurrencyRatesDTO;
import com.rates.demo.service.RateApiCallService;
import com.rates.demo.service.RatesService;

import io.swagger.annotations.ApiOperation;

@RestController
public class RatesApiController {
	
	@Autowired
	RateApiCallService apiCallService;
	
	@Autowired
	RatesService ratesService;
	
	@ApiOperation(value = "Get the current Rates")
	@GetMapping(value = "/getCurrentDateRates",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CurrencyRatesDTO>> getRates() {
		return ResponseEntity.ok(apiCallService.fetchTodayRates());
	}
	
	@ApiOperation(value = "Get the Rates from the system")
	@GetMapping(value ="/fetchRatesFromSystem",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CurrencyRatesDTO>> fetchRatesFromSystem() {
		return ResponseEntity.ok(ratesService.fetchAllDetails());
	}
	
	@ApiOperation(value = "Get the rates between two dates")
	@GetMapping(value ="/getRatesForBetweenDates/{date1}/{date2}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CurrencyRatesDTO>> getRatesForBetweenDates(@PathVariable(name = "date1") String date1,@PathVariable(name = "date2") String date2) {
		return ResponseEntity.ok(ratesService.fetchAllSpecificDateRange(date1, date2));
	}
	
	@ApiOperation(value = "Fetch the missing dates from the system")
	@GetMapping(value ="/getMissingDatesFromSystem/{forMonths}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LocalDate>> getMissingDatesFromSystem(@PathVariable(name = "forMonths") Integer forMonths) {
		return ResponseEntity.ok(apiCallService.fetchLastNthMonthCurrencyRates(forMonths));
	}
	
	@ApiOperation(value = "Populate the rates for the specified months")
	@GetMapping(value ="/getRatesForMonths/{months}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CurrencyRatesDTO>> getRatesForMonths(@PathVariable(name = "months") Integer months) {
		return ResponseEntity.ok(apiCallService.fetchCurrencyRates(apiCallService.fetchLastNthMonthCurrencyRates(months)));
	}

}
