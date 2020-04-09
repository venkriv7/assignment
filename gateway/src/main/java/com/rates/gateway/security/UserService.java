package com.rates.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rates.gateway.bean.auth.RatesUserDetails;
import com.rates.gateway.domain.TblUserDO;
import com.rates.gateway.exception.CustomException;
import com.rates.gateway.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        TblUserDO user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
        }
        String[] roles = {"admin"};
        RatesUserDetails userDetails = new RatesUserDetails(user.getUserId(),user.getPassword(),1,
                false, false,true,roles);
        return userDetails;
    }



}
