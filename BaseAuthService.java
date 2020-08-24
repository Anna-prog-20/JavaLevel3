package com;

import com.mysql.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService{
    private List<Entry> entries;

    public BaseAuthService() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException var7) {
            throw new RuntimeException("Driver not found");
        }

        Connection connection;
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CHAT", "root", "");
        } catch (SQLException var6) {
            var6.printStackTrace();
            throw new RuntimeException("Driver Registration error");
        }

        try {
            entries=new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT login, password, nickname FROM USERS");
            while(true) {
                if (!resultSet.next()) {
                    System.out.println(entries);
                    break;
                }
                entries.add(new Entry(resultSet.getString("login"), resultSet.getString("password"), resultSet.getString("nickname")));
            }
        } catch (SQLException var8) {
            var8.printStackTrace();
            throw new RuntimeException("Statement error");
        }
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
        for (Entry entry : entries) {
            if (entry.getLogin().equals(login) && entry.getPassword().equals(password)) {
                return entry.getNickname();
            }
        }
        return null;
    }

    private class Entry {
        private String login;
        private String password;
        private String nickname;

        public Entry(String login, String password, String nickname) {
            this.login = login;
            this.password = password;
            this.nickname = nickname;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        public String getNickname() {
            return nickname;
        }
    }
}
