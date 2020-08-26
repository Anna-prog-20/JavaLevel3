package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseAuthService implements AuthService{
    private Connection connection;

    public BaseAuthService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен.");
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен.");
    }

    @Override
    public String getNickByLoginAndPass(String login, String password) {
        try {
            ResultSet clients = questClient(login,password);
            if(clients.next())
                return clients.getString("nickname");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private ResultSet questClient(String login, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE login=? and password=?");
        preparedStatement.setString(1,login);
        preparedStatement.setString(2,password);
        return preparedStatement.executeQuery();
    }
}
