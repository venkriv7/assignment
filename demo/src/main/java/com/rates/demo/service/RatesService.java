package com.rates.demo.service;

import java.util.List;

import com.rates.demo.dto.CurrencyRatesDTO;


public interface RatesService {
	public List<CurrencyRatesDTO> fetchAllDetails();
	public List<CurrencyRatesDTO> fetchAllSpecificDateRange(String date1,String date2);

}
