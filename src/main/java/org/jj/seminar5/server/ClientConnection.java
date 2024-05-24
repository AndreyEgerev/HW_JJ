package org.jj.seminar5.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jj.seminar5.logic.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import java.util.Objects;
import java.util.Scanner;


/**
 * Инкапсуляция для клиента на сервере
 */
public class ClientConnection implements Runnable {

    private final Server server;
    private final Socket socket;
    private final Scanner input;
    private final PrintWriter output;
    private final String login;
    private Runnable onCloseHandler;

    public ClientConnection(Socket socket, Server server) throws IOException {
        this.server = server;
        this.socket = socket;
        this.input = new Scanner(socket.getInputStream());
        this.output = new PrintWriter(socket.getOutputStream(), true);

        this.login = input.nextLine();
    }

    public void sendMessage(String message) {
        output.println(message);
    }

    public String getLogin() {
        return login;
    }

    public void setOnCloseHandler(Runnable onCloseHandler) {
        this.onCloseHandler = onCloseHandler;
    }

    @Override
    public void run() {
        try {
            ObjectMapper objectMapperMsg = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .findAndRegisterModules();
            while (true) {
                String msgFromClient = input.nextLine();
                Message messageFromClient = objectMapperMsg.readValue(msgFromClient, Message.class);
                if (Objects.equals("exit", messageFromClient.getMsg())) {
                    System.out.println("Клиент отключился");
                    break;
                }

                if (messageFromClient.getTo() != null) {

                    server.sendMessageToClient(messageFromClient.getTo(), messageFromClient);
                } else {
                    server.sendMessageToAll(messageFromClient);
                }
            }

            try {
                close();
            } catch (IOException e) {
                System.err.println("Произошла ошибка во время закрытия сокета: " + e.getMessage());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } finally {
            if (onCloseHandler != null) {
                onCloseHandler.run();
            }
        }
    }

    public void close() throws IOException {
        socket.close();
    }

}
