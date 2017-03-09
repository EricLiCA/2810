import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        String file = "data_pokemon.txt";
        Map<String, Sommet> endroits = getEndroits(file);
        Vector<Arc> arcs = getArcs(file);
        Map<String, Arc> arcsData

        Collections.sort(arcs);

        for (Arc arc: arcs) {
            System.out.println(arc.getDistance());
        }
    }
}