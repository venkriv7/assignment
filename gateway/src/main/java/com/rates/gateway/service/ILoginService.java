package com.rates.gateway.service;

import com.rates.gateway.domain.TblUserDO;

public interface ILoginService {
    String login(String username, String password);
    TblUserDO saveUser(TblUserDO user);

    boolean logout(String token);

    Boolean isValidToken(String token);

    String createNewToken(String token);
}
