package controller;

import model.CRDT;
import model.Version;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Controller {
    private static CRDT crdt;
    private static Messenger messenger;
    private static TextEditor textEditor;

    public Controller(String host, Integer port) throws UnknownHostException {
        String id = "ws://" + host + ":" + port;
        HashMap<String, Version> versions = new HashMap<>();
        versions.put(id, new Version(id, 0));
        crdt = new CRDT(id, versions);
        messenger = new Messenger(host, port);
        textEditor = new TextEditor();
    }

    public static TextEditor getTextEditor() { return textEditor; }

    public static CRDT getCrdt() { return crdt; }

    public static Messenger getMessenger(){
        return messenger;
    }

    public static void addNodeVersion(String id) {
        crdt.versions.put(id, new Version(id, 0));
    }

    public static void insert(Integer nextIdx, char value) throws IOException {
        getMessenger().BroadcastObject(crdt.localInsert(crdt.getPositions(nextIdx), value));
    }

    public static void delete(Integer idx) throws IOException {
        getMessenger().BroadcastObject(crdt.localDelete(crdt.getPositions(idx)));
    }

    public static void main(String[] args) throws URISyntaxException, InterruptedException, IOException {
        BasicConfigurator.configure();

        // NODE 1
//        Controller controller = new Controller(InetAddress.getLocalHost().getHostAddress(), 40000);
//        System.out.println(controller.getMessenger().getWebSocketAddress());
//         NODE 2
        Controller controller = new Controller(InetAddress.getLocalHost().getHostAddress(), 40001);
        Messenger.ConnectToNode("ws://10.5.25.161:40000");
//        Thread.sleep(10000);
//        controller.insert(0,'a');
//        controller.insert(0,'b');
//        controller.insert(0,'c');
//        controller.insert(0,'a');
//        controller.insert(Integer.MAX_VALUE,'b');
//        controller.insert(0,'c');
//
//        Thread.sleep(3000);
//        controller.delete(1);
////        controller.delete(-1.0f);
    }
}