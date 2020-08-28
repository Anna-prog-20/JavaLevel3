package com;

public interface AuthService {
    void start();
    void stop();
    User getNickByLoginAndPass(String login, String password);
}
