package controller;

import model.CRDT;
import model.Version;

import java.io.IOException;
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
}