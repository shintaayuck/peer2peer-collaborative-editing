package controller;

import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.util.*;

public class Messenger {
    private Set<String> serverAddressList = new HashSet<>();

    private Map<String, Client> clientNodes = new HashMap<>();

    private static String serverNodeAddress;
    private static Server serverNode;

    public Messenger(){
        Random rand = new Random();

        serverNode = new Server(new InetSocketAddress("localhost", rand.nextInt(10000)+40000));
        serverNodeAddress = serverNode.getWebSocketAddress();
        serverNode.start();
    }

    public static void main(String[] args) throws URISyntaxException {
        Messenger messenger = new Messenger();
    }
}
