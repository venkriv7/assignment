package com.rates.demo.mapper;

import java.util.List;

import com.rates.demo.domain.TblCurrencyRatesDO;
import com.rates.demo.dto.CurrencyRatesDTO;

public interface CurrencyRatesMapper {
	
	public List<CurrencyRatesDTO> convertTblCurrencyRatesDOToCurrencyRatesDTO(List<TblCurrencyRatesDO> tblCurrencyRatesDO);

}
