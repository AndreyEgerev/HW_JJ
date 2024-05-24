package org.jj.seminar5.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jj.seminar5.logic.Message;
import org.jj.seminar5.server.ServerMain;

import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.IOException;



public class Client {
    private static volatile boolean run = true;
    public static void main(String[] args) throws IOException {

        Scanner console = new Scanner(System.in);
        System.out.println("Введите ваш логин: ");
        String login = console.nextLine();

        Socket server = new Socket("localhost", ServerMain.SERVER_PORT);
        System.out.println("Подключение к серверу успешно");
        Scanner in = new Scanner(server.getInputStream());
        PrintWriter out = new PrintWriter(server.getOutputStream(), true);
        out.println(login);

        // Поток на чтение
        new Thread(() -> {
            while (run) {
                ObjectMapper objectMapperMsg = new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .findAndRegisterModules();
                try {
                    String message = in.nextLine();
                    Message messageFromServer = objectMapperMsg.readValue(message, Message.class);
                    System.out.println(messageFromServer);
                } catch (JsonProcessingException e) {
                    System.out.println("Получено непонятное сообщение от сервера");;
                } catch (NoSuchElementException e) {
                    System.out.println("Нет сообщения от сервера");
                }

            }
        }).start();

        // Поток на запись
        new Thread(() -> {
            ObjectMapper objectMapperMsg = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .findAndRegisterModules();
            while (run) {
                String inputFromConsole = console.nextLine();
                Message msgClient;
                if (inputFromConsole.charAt(0) == '@') {
                    String[] split =inputFromConsole.split("\\s+");
                    msgClient = new Message(split[1], login, split[0].substring(1), LocalDateTime.now());
                } else {
                    msgClient = new Message(inputFromConsole, login, null, LocalDateTime.now());
                }
                try {
                    String msgToServer = objectMapperMsg.writeValueAsString(msgClient);
                    out.println(msgToServer);
                } catch (JsonProcessingException e) {
                    System.out.println("Невозможно подготовить сообщение к отправке");;
                }
                if (inputFromConsole.equals("exit")) {
                    run = false;

                }
            }
        }).start();

    }
}
