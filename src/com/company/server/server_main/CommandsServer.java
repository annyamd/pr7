package com.company.server.server_main;

import com.company.server.connection.request_read.*;
import com.company.server.db.MusicBandsCRUD;
import com.company.server.controllers.command_control.CommandManager;
import com.company.server.collection.MusicBandHashSet;
import com.company.server.connection.server_connection_response.Response;
import com.company.server.connection.server_connection_response.ServerResponseWriter;
import com.company.server.connection.server_connection_set.ClientConnection;
import com.company.server.verifiers.Convertions;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandsServer {
    private static MusicBandHashSet musicBands;
    private static Scanner scanner;
    private static final ExecutorService requestReadExecutor = Executors.newCachedThreadPool();
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    private static CommandManager commandManager;

    public static void main(String[] args) {

        int port = Integer.parseInt(args[0]);
        try {
            musicBands = new MusicBandHashSet(MusicBandsCRUD.getAll());
            scanner = new Scanner(System.in);
            commandManager = new CommandManager(musicBands);

            ClientConnection connection = new ClientConnection();
            connection.setOnConnection(port);

            new Thread(() -> {
                while (true) {
                    checkTerminal();
                }
            }).start();

            while (true) {
                try {
                    System.out.println("--------------------------------------------------");
                    Callable<Request> read = () -> {
                        System.out.println("***************************************************************");
                        Request request = ServerRequestReader.readRequest(connection.getDatagramSocket());
                        if (request == null) throw new IOException();
                        onRequestRecieved(request, connection);
                        return request;
                    };
                    requestReadExecutor.submit(read).get();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            save();
        }

}

    public static void onRequestRecieved(Request request, ClientConnection connection) {
        Callable<Response> execute = () -> {
            Response response = ServerRequestProcess.process(request, commandManager);
            onResponseRecieved(response, request, connection);
            return null;
        };
        executor.submit(execute);
    }

    public static void onResponseRecieved(Response response, Request request, ClientConnection connection) {
        Runnable sendResp = () -> {
            ServerResponseWriter.sendResponse(connection.getDatagramSocket(),
                    request.getAddress(), request.getPort(), response);
        };
        executor.execute(sendResp);
    }

    public static void checkTerminal() {
        System.out.println("Save current state? yes/no or exit");

        if (scanner.hasNext()) {
            String ans = Convertions.convertNullableStrToNull(scanner.next());
            switch (ans.toLowerCase()) {
                case "yes":
                    save();
                    break;
                case "exit":
                    exit();
                    break;
                default:
                    System.out.println("Did not save.");
            }
        }
    }

    public static void save() {
        if (musicBands != null)
            System.out.println("Saved.");
    }

    public static void exit() {
        save();
        scanner.close();
        System.out.println("End of session.");
        System.exit(0);
    }

}