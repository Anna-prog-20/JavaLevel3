package com;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ClientHandler{
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;

    private long a;
    private boolean timeIsOver=false;

    private ExecutorService executorServiceStart;
    private ExecutorService executorServiceTime;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            executorServiceStart = Executors.newSingleThreadExecutor();
            executorServiceStart.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!timeIsOver) {
                            doAuth();
                            readMessage();
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                    finally {
                        closeConnection();
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Произошла ошибка во время инициализации клиента");
        }
    }


    public String getName() {
        return name;
    }

    public void doAuth() throws IOException {
        a=System.currentTimeMillis()/1000L;
        executorServiceTime = Executors.newSingleThreadExecutor();
        executorServiceTime.execute(new Runnable() {
            @Override
            public void run() {
                while (!timeIsOver)
                    if (System.currentTimeMillis()/1000L-a>120) {
                        timeIsOver = true;
                    }
            }
        });

        String str;
        while (!timeIsOver) {
            str = in.readUTF();
            if (str.startsWith("/auth")) {
                String[] parts = str.split("\\s");
                if(parts.length==3){
                    if (server.getAuthService().getNickByLoginAndPass(parts[1], parts[2]) != null) {
                        String nickname = server.getAuthService().getNickByLoginAndPass(parts[1], parts[2]).getNickname();
                        if (server.isNickFree(nickname)) {
                            sendMessage("/authok " + nickname);
                            name = nickname;
                            server.broadcastMessage(name + " зашел в чат.");
                            server.subscribe(this);
                            break;
                        } else {
                            sendMessage(String.format("Клиент с ником [%s] уже используется", nickname));
                        }
                    } else {
                        sendMessage("Неверный логин и/или пароль.");
                    }
                } else {
                    sendMessage("Заполните, пожалуйста, все поля.");
                }

            }
        }
        if (timeIsOver) {
            JDialog dialog=new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, "Время ожидания вышло!");
           closeConnection();
        }
    }

    // Отправка сообщений обратно клиенту
    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessage() throws IOException {
        while (true) {
           String strFromClient = in.readUTF();
            if (strFromClient.startsWith("/")) {
                if (strFromClient.equals("/end")) {
                    break;
                }
                if (strFromClient.startsWith("/w ")) {
                    String[] tokens = strFromClient.split("\\s");
                    String nick = tokens[1];
                    String msg = strFromClient.substring(4 + nick.length());
                    server.sendMessageToClient(this, nick, msg);
                }
                continue;
            }
            server.broadcastMessage(String.format("%s: %s", name, strFromClient));
        }
    }

    public void closeConnection() {
        server.unsubscribe(this);
        if (name.length()!=0){
            server.broadcastMessage(name + " покинул чат");
        }
        try {
            in.close();
            out.close();
            socket.close();
            System.out.println(String.format("Клиент [%s] отключен...",name));
            executorServiceStart.shutdown();
            executorServiceTime.shutdown();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }


}
