package com;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final int port;
    private AuthService authService;
    private List<ClientHandler> clients;

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
        start();
    }

    public AuthService getAuthService() {
        return authService;
    }

    private void start() {
        try (ServerSocket server = new ServerSocket(this.port)) {
            authService = new BaseAuthService();
            authService.start();

            while(true) {
                System.out.println("Сервер запущен, порт: " + port);
                System.out.println("Ожидаю подключения клиента...");
                Socket socket = server.accept();
                System.out.println(String.format("Клиент подключился: %s", socket.toString()));
                ClientHandler clientHandler=new ClientHandler(this, socket);
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
    public synchronized void broadcastClientsList() {
        StringBuilder sb = new StringBuilder("/clients ");
        for (ClientHandler clientHandler : clients) {
            sb.append(clientHandler.getName() + " ");
        }
        broadcastMessage(sb.toString());
    }

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