package model;

import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

public class CRDT {
    public static SortedMap<Float, Character> document = new TreeMap<>();
    public static int counter = 0;
    public int idNode;
    public HashMap<Integer, Version> versions;
    
    public CRDT(int idNode, HashMap<Integer, Version> versions) {
        this.idNode = idNode;
        this.versions = versions;
    }
    
    public int getIdNode() {
        return idNode;
    }
    
    public void localInsert(Float nextIdx, char value) {
        Float position = setCharPosition(nextIdx);
        Character c = new Character(value, position, new Version(this.getIdNode(), counter++));
        document.put(position, c);
        broadcastChar(c);
        
    }
    
    private Float setCharPosition(Float nextIdx) {
        if (document.size() == 0) {
            return nextIdx;
        }
        else if (nextIdx == Float.MAX_VALUE) {
            return (float) ceil(document.lastKey()+1);
        } else if (nextIdx.equals(document.firstKey())) {
            return (float) floor(document.firstKey()-1);
//            return ((document.firstKey() % 1 == 0) ? document.firstKey()-1 : (float) floor(document.firstKey()));
        } else {
          Float low = ((TreeMap<Float,Character>)document).lowerKey(nextIdx) != null ?
              ((TreeMap<Float,Character>)document).lowerKey(nextIdx) : 0f;
          return (low + nextIdx) / 2;
        }

    }
    
    public void localDelete(Float idx) {
        Character c = document.remove(idx);
        Version version = new Version(idNode, counter++);
        c.delete(version);
        broadcastChar(c);
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
        for (Character c1:
            cs) {
            System.out.print(c1.getInsertVersion().getCounter() + " ");
        }
    
        System.out.println("");
        System.out.println("");
        
        if (!c.getInsert()) {
            System.out.println("DELETE " + c.getDeleteVersion().getCounter());
        }
    }
    
    public void remoteInsert(Character c) {
        document.put(c.getPosition(),c);
        Version version = versions.get(c.getInsertVersion());
        version.setCounter(
            c.getInsertVersion().getCounter() < version.getCounter() ?
                version.getCounter() : c.getInsertVersion().getCounter());
        broadcastChar(c);
    }
    
    public void remoteDelete(Character c) {
        
        if (c.getInsertVersion().getIdNode() != this.idNode &&
            versions.get(c.getInsertVersion().getIdNode()).getCounter() < c.getInsertVersion().getCounter())  {
                //Add Deletion Buffer
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