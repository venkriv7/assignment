package com.rates.demo.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.rates.demo.domain.TblCurrencyRatesDO;
import com.rates.demo.mapper.CurrencyRatesMapper;
import com.rates.demo.mapper.CurrencyRatesMapperImpl;
import com.rates.demo.repositories.TblCurrencyRatesRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RatesServiceImpl.class,CurrencyRatesMapperImpl.class})
public class RatesServiceTests {
	
	@Autowired
	RatesService ratesServiceImpl;
	
	@MockBean
	TblCurrencyRatesRepository tblCurrencyRatesRepository;
	
	@Mock
	CurrencyRatesMapper currencyRatesMapper;
	
	@Test
	public void testFetchAllDetails() throws Exception {
		List<TblCurrencyRatesDO> response = new ArrayList<>();
		Mockito.when(tblCurrencyRatesRepository.findAll()).thenReturn(response);
		assertNotNull(ratesServiceImpl.fetchAllDetails());
	}

	@Test
	public void testFetchAllSpecificDateRange() throws Exception {
		List<TblCurrencyRatesDO> response = new ArrayList<>();
		Mockito.when(tblCurrencyRatesRepository.findByRateDateBetween(Mockito.any(), Mockito.any())).thenReturn(response);
		assertNotNull(ratesServiceImpl.fetchAllSpecificDateRange("2020-02-01", "2020-03-09"));
	}
}
