package com.rates.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rates.gateway.domain.TblUserDO;

@Repository
public interface UserRepository extends JpaRepository<TblUserDO,Long> {
	
	public TblUserDO findByUserId(String userId);
}
