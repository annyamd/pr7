package com.company.server.connection.request_read;

import java.io.Serializable;
import java.net.InetAddress;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    int port;
    InetAddress address;

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public void setAddressAndPort(InetAddress address, int port){
        this.address = address;
        this.port = port;
    }

}
