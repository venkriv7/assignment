package com.rates.demo.service;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.rates.demo.domain.TblCurrencyDO;
import com.rates.demo.domain.TblCurrencyRatesDO;
import com.rates.demo.integration.RateApiVendorCall;
import com.rates.demo.mapper.CurrencyRatesMapper;
import com.rates.demo.repositories.TblCurrencyRatesRepository;
import com.rates.demo.repositories.TblCurrencyRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RateApiCallServiceImpl.class})
public class RateApiCallServiceTests {
	
	@MockBean
	TblCurrencyRatesRepository tblCurrencyRatesRepository;

	@MockBean
	TblCurrencyRepository tblCurrencyRepository;

	@MockBean
	RestTemplate restTemplate;
	
	@MockBean
	JdbcTemplate jdbcTemplate;

	@MockBean
	RateApiVendorCall rateApiVendorCall;
	
	@Autowired
	RateApiCallService rateApiCallServiceImpl;
	
	@MockBean
	CurrencyRatesMapper currencyMapper;
	
	@Before
	public void beforeTest() {
		List<TblCurrencyDO> dos = new ArrayList<>();
		TblCurrencyDO cdo = new TblCurrencyDO();
		cdo.setCurrencyName("GBP");
		cdo.setId(1L);
		TblCurrencyDO cdo1 = new TblCurrencyDO();
		cdo.setCurrencyName("USD");
		cdo.setId(1L);
		dos.add(cdo);
		dos.add(cdo1);
		
		List<TblCurrencyRatesDO> doRates = new ArrayList<>();
		TblCurrencyRatesDO cdoRate = new TblCurrencyRatesDO();
		cdo.setCurrencyName("GBP");
		cdoRate.setId(1L);
		cdoRate.setRate(1.000d);
		cdoRate.setRateDate(LocalDate.now());
		cdoRate.setTblCurrencyDO(cdo);
		TblCurrencyRatesDO cdoRate1 = new TblCurrencyRatesDO();
		cdoRate.setId(2L);
		cdoRate.setRate(2.000d);
		cdoRate.setRateDate(LocalDate.now());
		cdoRate.setTblCurrencyDO(cdo1);
		doRates.add(cdoRate);
		doRates.add(cdoRate1);
		Mockito.when(tblCurrencyRepository.findAll()).thenReturn(dos);
		Mockito.when(tblCurrencyRatesRepository.findByRateDate(Mockito.any())).thenReturn(doRates);
		
		LinkedHashMap<String, Object> obj = new LinkedHashMap<>();
		obj.put("date", LocalDate.now());
		LinkedHashMap<String, Double> objIn = new LinkedHashMap<>();
		objIn.put("GBP", 1.0d);
		objIn.put("USD", 2.0d);
		objIn.put("HKD", 2.05d);
		
		obj.put("rates", objIn);
		Mockito.when(rateApiVendorCall.fetchTodayRatesApiCall()).thenReturn(obj);
		Mockito.when(tblCurrencyRatesRepository.saveAll(Mockito.any())).thenReturn(doRates);
		List<Map<String,Object>> list = new ArrayList<>();
		Mockito.when(jdbcTemplate.queryForList(Mockito.any())).thenReturn(list);
	}
	
	@Test
	public void testFetchTodayRates() throws Exception {
		assertNotNull(rateApiCallServiceImpl.fetchTodayRates());
	}
	
	@Test
	public void testFetchCurrencyRates() throws Exception {
		List<LocalDate> list = new ArrayList<>();
		list.add(LocalDate.now());
		assertNotNull(rateApiCallServiceImpl.fetchCurrencyRates(list));
	}
	
	@Test
	public void testFetchLastNthMonthCurrencyRates() throws Exception {
		assertNotNull(rateApiCallServiceImpl.fetchLastNthMonthCurrencyRates(1));
	}

}
