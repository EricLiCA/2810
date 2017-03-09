import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {
    

    public static Map<String, Sommet> getEndroits(String file) throws IOException {
        Map<String, Sommet> endroits = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        String[] parts = line.split(";");
        for (String item: parts) {
            String[] infos = item.split(",");
            Sommet endroit = new Sommet(infos[0], infos[1], Integer.parseInt(infos[2]));
            endroits.putIfAbsent(infos[0], endroit);
        }
        return endroits;
    }

    public static Vector<Arc> getArcs(String file) throws IOException {
        Vector<Arc> arcs = new Vector<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        line = br.readLine();
        String[] parts = line.split(";");
        for (String item: parts) {
            String[] infos = item.split(",");
            Arc arc = new Arc(Integer.parseInt(infos[2]), infos[0], infos[1]);
            arcs.addElement(arc);
        }
        return arcs;
    }
}
