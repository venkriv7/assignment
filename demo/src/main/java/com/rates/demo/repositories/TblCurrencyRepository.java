package com.rates.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rates.demo.domain.TblCurrencyDO;

public interface TblCurrencyRepository extends JpaRepository<TblCurrencyDO, Long> {

}
