package com.rates.demo.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.rates.demo.domain.TblCurrencyRatesDO;
import com.rates.demo.dto.CurrencyRatesDTO;

@Component("currencyRatesMapper")
public class CurrencyRatesMapperImpl implements CurrencyRatesMapper {

	@Override
	public List<CurrencyRatesDTO> convertTblCurrencyRatesDOToCurrencyRatesDTO(
			List<TblCurrencyRatesDO> tblCurrencyRatesDO) {
		List<CurrencyRatesDTO> response = new ArrayList<>();
		if(tblCurrencyRatesDO != null) {
			tblCurrencyRatesDO.forEach(t -> {
				CurrencyRatesDTO currencyRatesDTO = new CurrencyRatesDTO();
				BeanUtils.copyProperties(t, currencyRatesDTO);
				//currencyRatesDTO.setCurrencyCodeId(t.getTblCurrencyDO().getId());
				currencyRatesDTO.setCurrencyCode(t.getTblCurrencyDO().getCurrencyName());
				
				response.add(currencyRatesDTO);
			});
		}
		return response;
	}

}
