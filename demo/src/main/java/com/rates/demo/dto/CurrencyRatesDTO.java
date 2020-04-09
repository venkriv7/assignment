package com.rates.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CurrencyRatesDTO {
	
	private Long id;
	
	private String currencyCode;
	
	private LocalDate rateDate;
	
	private Double rate;
	

}
