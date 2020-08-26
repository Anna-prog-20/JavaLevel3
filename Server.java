package com;

import com.mysql.jdbc.Driver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final int port;
    private AuthService authService;
    private List<ClientHandler> clients;
    private Connection connection;

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
        connectionBD();
        start();
    }

    public AuthService getAuthService() {
        return authService;
    }

    private void start() {

        try (ServerSocket server = new ServerSocket(this.port)) {
            authService = new BaseAuthService(connection);
            authService.start();

            while(true) {
                System.out.println("Сервер запущен, порт: " + port);
                System.out.println("Ожидаю подключения клиента...");
                Socket socket = server.accept();
                System.out.println(String.format("Клиент подключился: %s", socket.toString()));
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при запуске сервера.");
        }
        finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }

    private void connectionBD(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException var7) {
            throw new RuntimeException("Driver not found");
        }

        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CHAT", "root", "");
        } catch (SQLException var6) {
            var6.printStackTrace();
            throw new RuntimeException("Driver Registration error");
        }
    }

    public synchronized boolean isNickBusy(String nickname) {
        for (ClientHandler clientHandler : clients) {
            if (clientHandler.getName().equals(nickname)) {
                return true;
            }
        }
        return false;
    }
    public synchronized boolean isNickFree(String nickname) {
        return !isNickBusy(nickname);
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(message);
        }
    }
//    public synchronized void broadcastClientsList() {
//        StringBuilder sb = new StringBuilder("/clients ");
//        while (true) {
//            try {
//                if (!clientsOnline.next()) break;
//                sb.append(clientHandler.getName() + " ");
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
////            for (ClientHandler clientHandler : clients) {
////                sb.append(clientHandler.getName() + " ");
////            }
//        }
//        broadcastMessage(sb.toString());
//    }

    public synchronized void sendMessageToClient(ClientHandler from, String nickTo, String msg) {
       for (ClientHandler clientHandler : clients) {
           if (clientHandler.getName().equals(nickTo)) {
            clientHandler.sendMessage("от " + from.getName() + ": " + msg);
            from.sendMessage("клиенту " + nickTo + ": " + msg);
            return;
           }
        }
        from.sendMessage("Участника с ником " + nickTo + " нет в чате");
    }


    public synchronized void subscribe(ClientHandler clientHandler) {
       clients.add(clientHandler);
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}