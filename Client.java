package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends JFrame {
    private static final String SERVER_ADDR = "localhost";
    private static final int SERVER_PORT = 8189;

    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;

    private JTextField msgInputField,loginField,passField;
    private JTextArea chatArea;

    private String incomeMessage="";
    private String myNick="";
    private String nameButton="Авторизоваться";
    private JButton auth;


    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Client client = new Client();
                client.start();
                client.prepareGUI();
            }
        }).start();
       new Thread(new Runnable() {
           @Override
           public void run() {
               Client client = new Client();
               client.start();
               client.prepareGUI();
           }
       }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Client client = new Client();
                client.start();
                client.prepareGUI();
            }
        }).start();

    }

    public void start() {
        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
            new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    doAuth();
                    readMessage();
                } catch (IOException e){
                    //e.printStackTrace();
                }
             }
        }).start();
    }

    public void doAuth() throws IOException{

        while (!socket.isClosed())  {
            incomeMessage = in.readUTF();
                if (incomeMessage.startsWith("/authok")) {
                    myNick =incomeMessage.split("\\s")[1];
                    setTitle("Клиент - "+myNick);
                    chatArea.append("Вы успешно авторизовались под ником: "+myNick);
                    chatArea.append("\n");
                    nameButton="Сменить пользователя";
                    auth.setText(nameButton);
                    break;
                }
                chatArea.append(incomeMessage);
                chatArea.append("\n");

        }
    }
    public void readMessage() throws IOException {
        while (!socket.isClosed()) {
                incomeMessage = in.readUTF();
                if (incomeMessage.equalsIgnoreCase("/end")) {
                    break;
                }
                chatArea.append(incomeMessage);
                chatArea.append("\n");
        }
    }

    public void send() {
        if (!msgInputField.getText().trim().isEmpty()) {
            try {
                out.writeUTF(msgInputField.getText());
                msgInputField.setText("");
                msgInputField.grabFocus();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения");
            }
        }
    }

    public void prepareGUI() {
        // Параметры окна
        setBounds(600, 200, 500, 400);
        setTitle("Клиент");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3,1));

        // Текстовое поле для вывода сообщений
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // Панель с полем для ввода сообщений и кнопкой отправки сообщений
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton btnSendMsg = new JButton("Отправить");
        bottomPanel.add(btnSendMsg, BorderLayout.EAST);
        msgInputField = new JTextField();
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(msgInputField, BorderLayout.CENTER);

        btnSendMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                send();
            }
        });
        msgInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                send();
            }
        });

        // панель авторизации
        JPanel authPanel = new JPanel(new BorderLayout());
        auth = new JButton(nameButton);
        authPanel.setLayout(new BoxLayout(authPanel,BoxLayout.Y_AXIS));

        loginField = new JTextField();
        passField = new JTextField();

        JLabel login=new JLabel("Логин: ");
        JLabel pas=new JLabel("Пароль: ");

        add(authPanel);
        authPanel.add(login);
        authPanel.add(loginField);
        authPanel.add(pas);
        authPanel.add(passField);
        authPanel.add(auth);
        auth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameButton.equals("Сменить пользователя")){
                    close();
                }
                onAuthClick();
                auth.setText(nameButton);
            }
        });
        loginField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameButton.equals("Сменить пользователя")){
                    close();
                }
                onAuthClick();
                auth.setText(nameButton);
            }
        });
        passField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameButton.equals("Сменить пользователя")){
                    close();
                }
                onAuthClick();
                auth.setText(nameButton);
            }
        });

        // Настраиваем действие на закрытие окна
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                close();
            }
        });

        setVisible(true);
    }

    public void onAuthClick() {
        if (socket == null || socket.isClosed()) {
            start();
        }
        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passField.getText());
            loginField.setText("");
            passField.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            chatArea.append(String.format("Вы [%s] отключились...",myNick));
            chatArea.append("\n");
            in.close();
            out.close();
            socket.close();
            System.out.println(String.format("Клиент [%s] отключен...",myNick));
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

}