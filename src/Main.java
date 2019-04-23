import model.CRDT;
import model.Character;
import model.Version;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

public class Main{
    public static void main(String[] args) {
        //TODO : Initialize versions based on nodes involved
        testIdx();
    }
    
    private static void testIdx() {
        HashMap<String, Version> versions = new HashMap<>();
        versions.put("1", new Version("1",0));
        versions.put("2", new Version("2",0));
        versions.put("3", new Version("3",0));
        versions.put("4", new Version("4",0));
        CRDT crdt = new CRDT("1", versions);
        
        crdt.localInsert(crdt.getPositions(0), 'a');
        crdt.localInsert(crdt.getPositions(Integer.MAX_VALUE), 'b');
        crdt.localInsert(crdt.getPositions(Integer.MAX_VALUE), 'c');
        crdt.localInsert(crdt.getPositions(Integer.MAX_VALUE), 'd');
        crdt.localInsert(crdt.getPositions(Integer.MAX_VALUE), 'e');
        crdt.localInsert(crdt.getPositions(Integer.MAX_VALUE), 'f');
        
        crdt.localInsert(crdt.getPositions(0), '-');
        crdt.localInsert(crdt.getPositions(0), ')');
        crdt.localInsert(crdt.getPositions(0), '(');
        
        crdt.localInsert(crdt.getPositions(5), '?');
        crdt.localInsert(crdt.getPositions(5), '!');
        
        
    }
    
    private static void testCRDT() {
    
        HashMap<String, Version> versions = new HashMap<>();
        versions.put("1", new Version("1",0));
        versions.put("2", new Version("2",0));
        versions.put("3", new Version("3",0));
        versions.put("4", new Version("4",0));
        CRDT crdt = new CRDT("1", versions);
    
        CRDT crdt2 = new CRDT("2", versions);
        
        crdt.localInsert(0f, 'a');
        crdt2.localInsert(0f, 'b');
        crdt.localInsert(Float.MAX_VALUE, 'c');
        crdt2.localInsert(Float.MAX_VALUE, 'd');
        crdt.localInsert(-1f, 'e');
        crdt2.localInsert(1f,'f');
        crdt.localInsert(1f,'g');
        crdt2.localInsert(1f,'h');
        crdt.localInsert(0.875f,'i');
        crdt2.localInsert(1f,'j');
        crdt.localInsert(-1f,'k');
        crdt.localInsert(Float.MAX_VALUE, '0');

        crdt.localDelete(1f);
        crdt2.localDelete(-2f);
        crdt.localDelete(-1f);
    
        System.out.println(crdt.positions);

//        SortedMap<Float, Character> chars = new TreeMap<>();
//
//        chars.put(-1.4f, 'q');
//        chars.put(1.45f, 's');
//        chars.put(1.451f, 'k');
//        chars.put(1.351f, 'x');
////
//        System.out.println(chars);
//
//
//        System.out.println(chars.lastKey());
//
////        System.out.println(((TreeMap<Float, Character>) chars).higherEntry(1.35f));
//
//        System.out.println(35%1);
//        System.out.println((35 %1 == 0 ? 35+1 : (float) ceil(35)));
//
//        ArrayList<Integer> ints = new ArrayList<>();
//        ints.add(10);
//        ints.add(11);
//        ints.add(9);
//        System.out.println(ints);
//        ints.set(1, ints.get(1)+10);
//        System.out.println(ints);
    
    }
}
