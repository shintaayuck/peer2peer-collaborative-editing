package controller;

import model.Character;
import model.Version;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import javax.naming.ldap.Control;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.*;

public class Messenger extends Server{
    private static Set<String> serverAddressList = new HashSet<>();

    private static Map<String, Client> clientNodes = new HashMap<>();

    private static String serverNodeAddress;

    public Messenger(String hostname, int port){
        super(new InetSocketAddress(hostname, port));

        serverNodeAddress = this.getWebSocketAddress();
        System.out.println(serverNodeAddress);
    }

    public static String getServerNodeAddress() {
        return serverNodeAddress;
    }

    public static Map<String, Client> getClientNodes() {
        return clientNodes;
    }

    public static Set<String> getServerAddressList() { return serverAddressList; }

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
            Controller.addNodeVersion(message);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        super.onMessage(conn, message);
//        try {
//            Character c = (Character) Converter.getObject(message.array());
//            Controller.getCrdt().remoteInsert(c);
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
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
        Messenger messenger = new Messenger("localhost", 42313);
//        Messenger messenger = new Messenger();
//        ConnectToNode("ws://localhost:44087");
//        try
//        {
//            Thread.sleep(5000);
//            messenger.BroadcastObject(new Character('a', 1.5f, new Version(0, 0)));
//        }
//        catch(InterruptedException ex)
//        {
//            Thread.currentThread().interrupt();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
