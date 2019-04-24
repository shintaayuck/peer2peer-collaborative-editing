package controller;

import model.Character;
import model.Version;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.naming.ldap.Control;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

public class Client extends WebSocketClient {

    private String serverAddress;

    public Client(URI serverURI, String serverAddress) {
        super(serverURI);
        this.serverAddress = serverAddress;
    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {
        System.out.println("Connected to server");
        send(Messenger.getServerNodeAddress());
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);

        Messenger.getClientNodes().remove(serverAddress);
        Messenger.getServerAddressList().remove(serverAddress);
//        ControllerNode.getVersionVector().remove(serverAddress);
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        super.onMessage(bytes);
        try {
            Character c = (Character) Converter.getObject(bytes);
            if (c.getInsert()) {
                Controller.getCrdt().remoteInsert(c);
            } else {
                Controller.getCrdt().remoteDelete(c);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Client received message: " + message);
        try {
            Messenger.ConnectToNode(message);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }

}
