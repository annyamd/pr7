package com.company.server.connection.request_read;

import com.company.server.connection.server_connection_response.Response;
import com.company.server.connection.server_connection_response.ServerResponseWriter;
import com.company.server.controllers.command_control.CommandManager;
import com.company.server.controllers.command_control.CommandType;
import com.company.server.controllers.command_control.ParamBox;
import com.company.server.db.MusicBandsCRUD;
import com.company.server.exceptions.NoAccessException;

public class ServerRequestProcess {
    public static Response process(Request request, CommandManager commandManager) throws NoAccessException {
        boolean isSuccess = false;
        String message = null;
        Response response = null;

        if (request instanceof CommandERequest) {
            CommandERequest commandERequest = (CommandERequest) request;
            isSuccess = MusicBandsCRUD.signInUser(commandERequest.getLogin(),
                    commandERequest.getPassword());
            if (isSuccess) {
                message = "User successfully signed in.";
                CommandType commandType = commandERequest.getCommandType();
                ParamBox params = commandERequest.getParams();
                ParamBox res = commandManager.execute(commandType, params, commandERequest.getLogin());
                response = new Response<>(res, isSuccess, message);
            } else {
                message = "Access denied.";
                response = new Response<>(null, isSuccess, message);
            }

        } else if (request instanceof AuthRequest) {
            AuthRequest authRequest = (AuthRequest) request;
            switch (authRequest.getAction()) {
                case AuthRequest.ACTION_SIGN_IN:
                    isSuccess = MusicBandsCRUD.signInUser(authRequest.getLogin(),
                            authRequest.getPassword());
                    if (isSuccess) message = "User successfully signed in.";
                    else message = "Access denied.";
                    break;
                case AuthRequest.ACTION_SIGN_UP:
                    isSuccess = MusicBandsCRUD.signUpUser(authRequest.getLogin(),
                            authRequest.getPassword());
                    if (isSuccess) message = "User successfully registered.";
                    else message = "Login already exist.";
            }
            response = new Response(null, isSuccess, message);
        }
        return response;
    }
}
