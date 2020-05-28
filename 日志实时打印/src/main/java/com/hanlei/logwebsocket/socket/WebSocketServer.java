package com.hanlei.logwebsocket.socket;

import org.glassfish.tyrus.server.Server;

import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @ClassName WebSocketServer
 * @Description TODO
 * @Author hanlei
 * @Date 2020/5/25 2:15 下午
 * @Version 1.0
 */
public class WebSocketServer {

    public static void main(String[] args) {
        runServer();
    }

    public static void runServer() {
        Server server = new Server("localhost", 8888, "/logserver", LogServerEndpoint.class);

        try {
            server.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Please press a key to stop the server.");
            reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }
}
