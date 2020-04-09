package com.rates.gateway.service.serviceimpl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rates.gateway.domain.JwtTokenDO;
import com.rates.gateway.domain.TblUserDO;
import com.rates.gateway.exception.CustomException;
import com.rates.gateway.repository.JwtTokenRepository;
import com.rates.gateway.repository.UserRepository;
import com.rates.gateway.security.JwtTokenProvider;
import com.rates.gateway.service.ILoginService;

@Service
public class LoginService implements ILoginService
{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Override
    public String login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
                    password));
            TblUserDO user = userRepository.findByUserId(username);
            if (user == null ) {
                throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
            }
            //NOTE: normally we dont need to add "ROLE_" prefix. Spring does automatically for us.
            //Since we are using custom token using JWT we should add ROLE_ prefix
            String token =  jwtTokenProvider.createToken(username, Arrays.asList("ROLE_ADMIN"));
            return token;

        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public TblUserDO saveUser(TblUserDO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()) );
        return userRepository.save(user);
    }

    @Override
    public boolean logout(String token) {
         jwtTokenRepository.delete(new JwtTokenDO(token));
         return true;
    }

    @Override
    public Boolean isValidToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @Override
    public String createNewToken(String token) {
        String username = jwtTokenProvider.getUsername(token);
        List<String> roleList = Arrays.asList("ROLE_ADMIN");
        String newToken =  jwtTokenProvider.createToken(username,roleList);
        return newToken;
    }
}
