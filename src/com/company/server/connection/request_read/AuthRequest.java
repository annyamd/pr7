package com.company.server.connection.request_read;

import java.io.Serializable;
import java.net.InetAddress;

public class AuthRequest extends Request {
    private static final long serialVersionUID = 1L;
    public final static int ACTION_SIGN_IN = 1;
    public final static int ACTION_SIGN_UP = 2;

    private String login;
    private String password;
    private int action;

    private int port;
    private InetAddress address;

    public AuthRequest(int action, String login, String password) {
        this.login = login;
        this.password = password;
        this.action = action;
    }

    public AuthRequest(){

    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", action=" + action +
                ", port=" + port +
                ", address=" + address +
                '}';
    }
}
