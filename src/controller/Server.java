package controller;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

public class Server extends WebSocketServer {

    private String webSocketAddress;

    public Server(InetSocketAddress address) {
        super(address);
        this.webSocketAddress = "ws://" + address.getHostName() + ":" + address.getPort();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake clientHandshake) {
        System.out.println("New connection to " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received message from "	+ conn.getRemoteSocketAddress() + ": " + message);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        super.onMessage(conn, message);
        byte[] b = new byte[message.remaining()];
        message.get(b);
        System.out.println(new String(b));
        System.out.println("ASU");
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("An error occurred on connection " + conn.getRemoteSocketAddress()  + ":" + ex);
    }

    @Override
    public void onStart() {
        System.out.println("Node started");
    }

    public String getWebSocketAddress() {
        return webSocketAddress;
    }

    public static void main(String[] args) throws URISyntaxException {
        String host = "localhost";

        Server server = new Server(new InetSocketAddress(host, 8888));

        server.start();
    }
}