package com.company.server.connection.request_read;

import com.company.server.controllers.command_control.CommandType;
import com.company.server.controllers.command_control.ParamBox;

import java.io.Serializable;
import java.net.InetAddress;

public class CommandERequest extends Request{
    private static final long serialVersionUID = 1L;

    private final CommandType commandType;
    private final ParamBox params;

    private String login;
    private String password;

    public CommandERequest(CommandType commandType, ParamBox paramBox){
        this.commandType = commandType;
        this.params = paramBox;
    }

    public void setLoginAndPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public ParamBox getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "CommandERequest{" +
                "commandType=" + commandType +
                ", params=" + params +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
