package com.rates.demo.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Entity
@Table(name = "TBL_CURRENCY_RATES")
@Setter
@Getter
public class TblCurrencyRatesDO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name = "currency_code_id")
	private TblCurrencyDO tblCurrencyDO;
	
	@Column(name="rate_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate rateDate;
	
	@Column(name="rate")
	private Double rate;
	

}
