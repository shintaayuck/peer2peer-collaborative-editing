package controller;

import model.Character;
import model.Version;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

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
        System.out.println("New PEER connection opened");
        Character character = new Character('s', 1.5f, new Version(3,3));
        byte[] data;
        try {
            data = Converter.getByteArray(character);
            send(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);

//        ControllerNode.getClientPeerNodes().remove(serverAddress);
//        ControllerNode.getVersionVector().remove(serverAddress);
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        super.onMessage(bytes);
    }



    @Override
    public void onMessage(String message) {
        System.out.println("received message: " + message);

//        CRDT crdt = parseCRDT(message);
//        updateVersionVector(crdt);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }


    public static void main(String[] args) throws URISyntaxException {
        String signalServerAddress = "ws://localhost:8888";

        Client client = new Client(new URI(signalServerAddress), signalServerAddress);
        client.connect();
    }
}
