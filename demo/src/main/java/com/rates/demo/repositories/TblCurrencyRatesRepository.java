package com.rates.demo.repositories;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rates.demo.domain.TblCurrencyRatesDO;

public interface TblCurrencyRatesRepository extends JpaRepository<TblCurrencyRatesDO, Long> {

	public List<TblCurrencyRatesDO> findByRateDate(LocalDate today);
	public List<TblCurrencyRatesDO> findByRateDateBetween(LocalDate startDate,LocalDate endDate);
}
