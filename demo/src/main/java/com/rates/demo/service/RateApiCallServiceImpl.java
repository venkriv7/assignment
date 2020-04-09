package com.rates.demo.service;

import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rates.demo.domain.TblCurrencyDO;
import com.rates.demo.domain.TblCurrencyRatesDO;
import com.rates.demo.dto.CurrencyRatesDTO;
import com.rates.demo.exceptions.ClientException;
import com.rates.demo.exceptions.NotFoundException;
import com.rates.demo.exceptions.ServerException;
import com.rates.demo.integration.RateApiVendorCall;
import com.rates.demo.mapper.CurrencyRatesMapper;
import com.rates.demo.repositories.TblCurrencyRatesRepository;
import com.rates.demo.repositories.TblCurrencyRepository;
import com.rates.demo.util.DateUtil;

@Service
public class RateApiCallServiceImpl implements RateApiCallService{

	@Autowired
	TblCurrencyRatesRepository tblCurrencyRatesRepository;

	@Autowired
	TblCurrencyRepository tblCurrencyRepository;

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	RateApiVendorCall rateApiVendorCall;
	
	@Autowired
	CurrencyRatesMapper currencyRatesMapper;


	/**
	 * Method is used to fetch current day Rates against to EURO. This is configured with retry mechanism in case if any failure has happened
	 * while getting the response from rates api from external system.
	 * @return list of Currency Rates DTO
	 */
	public List<CurrencyRatesDTO> fetchTodayRates() {
		try {
			List<TblCurrencyDO> dos = tblCurrencyRepository.findAll();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate today = LocalDate.now();
			LinkedHashMap<String, Object> obj = rateApiVendorCall.fetchTodayRatesApiCall();
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Double> rates = (LinkedHashMap<String, Double>) obj.get("rates");
			String apiDate = (String) obj.get("date");
			List<TblCurrencyRatesDO> ratesDos = tblCurrencyRatesRepository.findByRateDate(LocalDate.parse(apiDate));
			List<TblCurrencyRatesDO> request = new ArrayList<>();
			if (ratesDos.size() != dos.size()) {
				
				if (!apiDate.equalsIgnoreCase(formatter.format(today))) {
					List<Long> curencyIds = ratesDos.parallelStream().map(t -> t.getId()).collect(Collectors.toList());
					dos.stream().filter(t -> !curencyIds.contains(t.getId())).forEach(t -> {
						TblCurrencyRatesDO object = new TblCurrencyRatesDO();
						object.setRate(rates.get(t.getCurrencyName()));
						object.setTblCurrencyDO(t);
							object.setRateDate(LocalDate.parse(apiDate));
						
						request.add(object);
					});
				}
			};
			if(!request.isEmpty())
			ratesDos.addAll(tblCurrencyRatesRepository.saveAll(request));
			return currencyRatesMapper.convertTblCurrencyRatesDOToCurrencyRatesDTO(ratesDos);
		} catch (ClientException | ServerException | NotFoundException e) { // (1)
			throw e;
		} 
	}
	
	/**
	 * Method is used to fetch current day Rates against to EURO by using input parameter dates. The input parameters are fetched from current h2 database
	 * who's rates are missed in the system. This is configured with retry mechanism in case if any failure has happened
	 * while getting the response from rates api from external system.
	 * @return list of Currency Rates DTO
	 */
	@Retryable(value = { UnknownHostException.class, ClientException.class, ServerException.class,
			NotFoundException.class }, maxAttempts = 5, backoff = @Backoff(delay = 1000))
	public List<CurrencyRatesDTO> fetchCurrencyRates(List<LocalDate> dates){
		List<TblCurrencyRatesDO> response = tblCurrencyRatesRepository.findAll();
		if(!dates.isEmpty()) {
			List<TblCurrencyRatesDO> newObjects = new ArrayList<>();
			Map<LocalDate, List<TblCurrencyDO>> groupByDateMap = 
					response.stream().collect(Collectors.groupingBy(TblCurrencyRatesDO::getRateDate,Collectors.mapping(TblCurrencyRatesDO::getTblCurrencyDO, Collectors.toList())));
			List<TblCurrencyDO> dos = tblCurrencyRepository.findAll();
			dates.parallelStream().forEach(date -> {
			LinkedHashMap<String, Object> obj = rateApiVendorCall.fetchSpecifcRatesApiCall(date);
			LocalDate dateApi = LocalDate.parse(String.valueOf(obj.get("date")));
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Double> rates = (LinkedHashMap<String, Double>) obj.get("rates");
			if(dates.contains(dateApi))
				if(!groupByDateMap.isEmpty() && groupByDateMap.containsKey(date) && groupByDateMap.get(date).size() < dos.size()) {
				dos.stream().filter(t -> !groupByDateMap.get(date).parallelStream().map(i -> i.getId()).collect(Collectors.toList()).contains(t.getId())).forEach(t -> {
					TblCurrencyRatesDO object = new TblCurrencyRatesDO();
					object.setRate(rates.get(t.getCurrencyName()));
					object.setTblCurrencyDO(t);
						object.setRateDate(dateApi);
					newObjects.add(object);
				});
				}else {
					
					dos.stream().forEach(t->{
					TblCurrencyRatesDO object = new TblCurrencyRatesDO();
					object.setRate(rates.get(t.getCurrencyName()));
					object.setTblCurrencyDO(t);
						object.setRateDate(dateApi);
					newObjects.add(object);
					});
				}
			});
		
			if(!newObjects.isEmpty()) {
				response.addAll(tblCurrencyRatesRepository.saveAll(newObjects));
			}
		}
		return currencyRatesMapper.convertTblCurrencyRatesDOToCurrencyRatesDTO(response);
	}
	
	/**
	 * This method is used to fetch dates not exist in the database by using input parameter dates. The input parameters are fetched from current h2 database
	 * who's rates are missed in the system. 
	 * @return list of Dates
	 */
	public List<LocalDate> fetchLastNthMonthCurrencyRates(int months) {
			List<TblCurrencyDO> dos = tblCurrencyRepository.findAll();
			String sqlNativeQuery = "select count(id) as count,rate_date from TBL_CURRENCY_RATES where rate_date >'"+DateUtil.getNthLastDateAsString(months)+"' group by rate_date";// having count <"+dos.size();
			jdbcTemplate.setFetchSize(50);
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sqlNativeQuery);
			List<LocalDate> datesToGenerate = DateUtil.getDatesBetween(LocalDate.now().minusMonths(months),LocalDate.now());
			datesToGenerate.removeAll(list.parallelStream().filter(t-> Integer.parseInt(String.valueOf(t.get("count")))>= dos.size()).map(t -> LocalDate.parse(String.valueOf(t.get("rate_date")))).collect(Collectors.toList()));
			return datesToGenerate;
	}
	
}
