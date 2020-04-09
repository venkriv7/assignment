package com.rates.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rates.demo.dto.CurrencyRatesDTO;
import com.rates.demo.mapper.CurrencyRatesMapper;
import com.rates.demo.repositories.TblCurrencyRatesRepository;

@Service("ratesService")
public class RatesServiceImpl implements RatesService{
	
	@Autowired
	TblCurrencyRatesRepository tblCurrencyRatesRepository;
	
	@Autowired
	CurrencyRatesMapper currencyRatesMapper;
	
	@Override
	public List<CurrencyRatesDTO> fetchAllDetails(){
		return currencyRatesMapper.convertTblCurrencyRatesDOToCurrencyRatesDTO(tblCurrencyRatesRepository.findAll());
	}
	
	@Override
	public List<CurrencyRatesDTO> fetchAllSpecificDateRange(String date1,String date2){
			return currencyRatesMapper.convertTblCurrencyRatesDOToCurrencyRatesDTO(tblCurrencyRatesRepository.findByRateDateBetween(LocalDate.parse(date1),LocalDate.parse(date2)));
	}

}
