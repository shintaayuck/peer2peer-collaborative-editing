import model.CRDT;
import model.Version;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

public class Main{
    public static void main(String[] args) {
        testCRDT();
    }
    
    public static void testCRDT() {
    
        HashMap<Integer, Version> versions = new HashMap<>();
        CRDT crdt = new CRDT(1, versions);
        crdt.localInsert(0f, 'q');
        crdt.localInsert(0f, 'a');
        crdt.localInsert(Float.MAX_VALUE, 'u');
        crdt.localInsert(Float.MAX_VALUE, 'e');
        crdt.localInsert(-1f, '!');
        crdt.localInsert(1f,'@');
        crdt.localInsert(1f,'$');
        crdt.localInsert(1f,'$');
        crdt.localInsert(0.875f,'x');
        crdt.localInsert(1f,'p');
    
        crdt.localDelete(1f);


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
