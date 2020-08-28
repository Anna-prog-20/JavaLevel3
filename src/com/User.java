package com;

public class User {
    private int id;
    private String nickname;
    private String login;
    private String password;

    public User(int id, String nickname, String login, String password) {
        this.id = id;
        this.nickname = nickname;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
