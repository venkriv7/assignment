package com.rates.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rates.gateway.domain.JwtTokenDO;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtTokenDO,String> {
}
