package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseAuthService implements AuthService{
    private Connection connection;

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен.");
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен.");
    }

    @Override
    public User getNickByLoginAndPass(String login, String password) {
        connection = DBService.getConnection();
        try {
            ResultSet clients = questClient(login,password);
            if(clients.next())
                return new User(
                        clients.getInt("id"),
                        clients.getString("nickname"),
                        clients.getString("login"),
                        clients.getString("password")
                );
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
