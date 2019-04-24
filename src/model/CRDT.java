package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

public class CRDT {
    public static SortedMap<Float, Character> document = new TreeMap<>();
    public static int counter = 0;
    public String idNode;
    public HashMap<String, Version> versions;
    public static ArrayList<Float> positions = new ArrayList<>();
    private HashMap<Float, Character> deletionBuffers;

    public CRDT(String idNode, HashMap<String, Version> versions) {
        this.idNode = idNode;
        this.versions = versions;
        this.deletionBuffers = new HashMap<>();
    }
    
    public String getIdNode() {
        return idNode;
    }
    
    public Float getPositions(Integer idx) {
        if (positions.size() == 0 ) {
            return 0f;
        } else if (idx == Integer.MAX_VALUE || idx == positions.size()) {
            return Float.MAX_VALUE;
        } else {
            return positions.get(idx);
        }
    }

    public Character localInsert(Float nextIdx, char value) {
        Float position = setCharPosition(nextIdx);
        Character c = new Character(value, position, new Version(this.getIdNode(), counter++));
        c.setNextIdx(nextIdx);
        document.put(position, c);
        broadcastChar(c);
        if (deletionBuffers.containsKey(c.getPosition())) {
        
        }
        return(c);
    }

    private Float setCharPosition(Float nextIdx) {
        if (document.size() == 0) {
            positions.add(nextIdx);
            return nextIdx;
        }
        else if (nextIdx == Float.MAX_VALUE) {
            Float sol = (float) ceil(document.lastKey()+1);
            positions.add(sol);
            return sol;
        } else if (nextIdx.equals(document.firstKey())) {
            Float sol = (float) floor(document.firstKey()-1);
            positions.add(0, sol);
            return sol;
        } else {
            Float low = ((TreeMap<Float, Character>) document).lowerKey(nextIdx) != null ? ((TreeMap<Float, Character>) document).lowerKey(nextIdx) : 0f;
            Float sol = (low + nextIdx) / 2;
            positions.add(positions.indexOf(nextIdx), sol);
            return sol;
        }
    }
    
    public Character localDelete(Float idx) {
        Character c = document.remove(idx);
        Version version = new Version(idNode, counter++);
        c.delete(version);
        broadcastChar(c);
        return(c);
    }
    
    private void broadcastChar(Character c) {
        Character[] cs = new Character[0];
        cs = document.values().toArray(cs);
        for (Character c1:
             cs) {
            System.out.print(c1.getValue());
        }
        System.out.println("");
        for (Character c1:
            cs) {
            System.out.print(c1.getPosition() + " ");
        }
        System.out.println("");
        
        if (!c.getInsert()) {
            System.out.println("DELETE " + c.getDeleteVersion().getCounter());
        }
    }
    
    public void remoteInsert(Character c) {
        document.put(c.getPosition(),c);
        if (positions.size() == 0) {
            positions.add(c.getPosition());
        } else if (c.getNextIdx() == Float.MAX_VALUE) {
            positions.add(c.getPosition());
        } else if (c.getNextIdx().equals(document.firstKey())) {
            positions.add(0, c.getPosition());
        } else {
            positions.add(positions.indexOf(c.getNextIdx()), c.getPosition());
        }
        Version version = versions.get(c.getInsertVersion().getIdNode());
        version.setCounter(
            c.getInsertVersion().getCounter() < version.getCounter() ?
                version.getCounter() : c.getInsertVersion().getCounter());
        broadcastChar(c);
    }
    
    public void remoteDelete(Character c) {
        
        if (c.getInsertVersion().getIdNode() != this.idNode &&
            versions.get(c.getInsertVersion().getIdNode()).getCounter() < c.getInsertVersion().getCounter())  {
                deletionBuffers.put(c.getPosition(), c);
        } else {
            document.remove(c.getPosition());
            Version version = versions.get(c.getDeleteVersion().getIdNode());
            version.setCounter(
                version.getCounter() < c.getDeleteVersion().getCounter() ?
                    c.getDeleteVersion().getCounter() : version.getCounter()
            );
        }
        broadcastChar(c);
    }
}