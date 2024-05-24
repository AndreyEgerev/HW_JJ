package org.jj.seminar5.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jj.seminar5.logic.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {

    private volatile Map<String, ClientConnection> clients = new HashMap<>();
    private static volatile boolean run = true;

    public void start(int port) {
        new Thread(() -> {
            Scanner console = new Scanner(System.in);
            while (run) {
                String inputFromConsole = console.nextLine();
                if (inputFromConsole.equals("exit")) {
                    run = false;
                } else {
                    System.out.println("Введена неизвестная команда ");;
                }
            }
            for (ClientConnection client : clients.values()) {
                try {
                    client.close();
                } catch (IOException e) {
                    System.out.println("Ошибка при отключении сервера");

                }
            }
            System.exit(0);
        }).start();

        try (ServerSocket server = new ServerSocket(port)) {
            while (run) {

                Socket clientSocket = server.accept();
                System.out.println("Подключился новый клиент: " + clientSocket);
                ClientConnection client = new ClientConnection(clientSocket, this);

                String clientLogin = client.getLogin();

                if (clients.containsKey(clientLogin)) {
                    client.sendMessage("Пользователь с таким логином уже подключен");
                    client.close();
                    continue;
                }

                clients.put(clientLogin, client);
                sendMessageToAll(new Message("Подключился новый клиент: " + clientLogin, "", "", LocalDateTime.now()));

                client.setOnCloseHandler(() -> {
                    clients.remove(clientLogin);
                    sendMessageToAll(new Message("Клиент " + clientLogin + " отключился","",null, LocalDateTime.now()));
                });

                new Thread(client).start();

            }

            for (ClientConnection client : clients.values()) {
                try {
                    client.close();
                } catch (IOException e) {
                    System.out.println("Ошибка при отключении сервера");

                }
            }
        } catch (IOException e) {
            System.err.println("Произошла ошибка во время прослушивания порта " + port + ": " + e.getMessage());
        }
    }

    public void sendMessageToClient(String login, Message message) {
        ObjectMapper objectMapperMsg = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .findAndRegisterModules();
        try {
            String msgFromServer = objectMapperMsg.writeValueAsString(message);
            ClientConnection clientConnection = clients.get(login);
            clientConnection.sendMessage(msgFromServer);
        } catch (JsonProcessingException e) {
            System.out.println("Невозможно подготовить сообщение к отправке");
        }
    }

    public void sendMessageToAll(Message message) {
        ObjectMapper objectMapperMsg = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .findAndRegisterModules();
        try {
            String msgFromServer = objectMapperMsg.writeValueAsString(message);
            for (ClientConnection client : clients.values()) {
                if (client.getLogin().equals(message.getFrom())) continue;
                client.sendMessage(msgFromServer);
            }
        } catch (JsonProcessingException e) {
            System.out.println("Невозможно подготовить сообщение к отправке");
        }
    }

}
