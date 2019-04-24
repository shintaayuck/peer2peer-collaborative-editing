package controller;

import model.CRDT;
import model.Version;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class Controller {
    private static CRDT crdt;
    private Messenger messenger;

    public Controller(String host, Integer port) {
        String id = "ws://" + host + ":" + port;
        HashMap<String, Version> versions = new HashMap<>();
        versions.put(id, new Version(id, 0));
        crdt = new CRDT(id, versions);
        messenger = new Messenger(host, port);
    }

    public static CRDT getCrdt() { return crdt; }

    public Messenger getMessenger(){
        return messenger;
    }

    public static void addNodeVersion(String id) {
        crdt.versions.put(id, new Version(id, 0));
    }

    public void insert(Float nextIdx, char value) throws IOException {
        getMessenger().BroadcastObject(crdt.localInsert(nextIdx, value));
    }

    public void delete(Float idx) throws IOException {
        getMessenger().BroadcastObject(crdt.localDelete(idx));
    }

    public static void main(String[] args) throws URISyntaxException, InterruptedException, IOException {
        BasicConfigurator.configure();

        // NODE 1
//        Controller controller = new Controller("localhost", 40001);

//         NODE 2
        Controller controller = new Controller("localhost", 40002);
        Messenger.ConnectToNode("ws://localhost:40001");
        Thread.sleep(5000);
        controller.insert(0f,'a');
        controller.insert(0f,'b');
        controller.insert(0f,'c');
        controller.delete(-1.0f);
    }
}