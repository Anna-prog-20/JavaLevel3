package com;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler{
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;

    private long a;
    private boolean timeIsOver=false;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (timeIsOver==false) {
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
                }).start();


        } catch (IOException e) {
            throw new RuntimeException("Произошла ошибка во время инициализации клиента");
        }
    }


    public String getName() {
        return name;
    }

    public void doAuth() throws IOException {
        a=System.currentTimeMillis()/1000L;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!timeIsOver)
                if (System.currentTimeMillis()/1000L-a>120) {
                    timeIsOver = true;
                }
            }
        }).start();

        String str="";
        while (!timeIsOver) {
            str = in.readUTF();
            if (str.startsWith("/auth")) {
                String[] parts = str.split("\\s");
                String nickname = server.getAuthService().getNickByLoginAndPass(parts[1], parts[2]);
                if (nickname != null) {
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
            System.out.println("Клиент отключен...");
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }


}
