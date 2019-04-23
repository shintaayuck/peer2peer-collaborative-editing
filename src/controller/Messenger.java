package controller;

import model.Character;
import model.Version;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class Messenger extends Server{
    private static Set<String> serverAddressList = new HashSet<>();

    private static Map<String, Client> clientNodes = new HashMap<>();

    private static String serverNodeAddress;

    public Messenger(){
        super(new InetSocketAddress("localhost", new Random().nextInt(10000)+40000));

        serverNodeAddress = this.getWebSocketAddress();
    }

    public static String getServerNodeAddress() {
        return serverNodeAddress;
    }

    public static Map<String, Client> getClientNodes() {
        return clientNodes;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake clientHandshake) {
        super.onOpen(conn, clientHandshake);

        for (String s : serverAddressList) {
            conn.send(s);
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        super.onMessage(conn, message);
        try {
            ConnectToNode(message);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void ConnectToNode(String address) throws URISyntaxException {
        if (!clientNodes.containsKey(address) && !address.equals(serverNodeAddress)) {
            System.out.println("Connecting to " + address);
            Client newClient = new Client(new URI(address), address);
            newClient.connect();
            clientNodes.put(address, newClient);
            serverAddressList.add(address);
        }
    }

    public void BroadcastObject(Object object) throws IOException {
        System.out.println("Broadcasting Object...");
        byte[] data = Converter.getByteArray(object);
        broadcast(data);
    }

    public static void main(String[] args) throws URISyntaxException {
        Messenger messenger = new Messenger();
        ConnectToNode("ws://localhost:44087");
        try
        {
            Thread.sleep(5000);
            messenger.BroadcastObject(new Character('a', 1.5f, new Version(0, 0)));
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
