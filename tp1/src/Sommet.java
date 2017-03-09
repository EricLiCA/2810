import java.util.*;

public class Sommet {
    private String identifiant;
    private String type;
    private int gain;
    public Map<Sommet, Integer> connections;

    public Sommet() {
        this.connections = new HashMap<>();
    }

    public Sommet(String identifiant, String type, int gain) {
        this();
        this.identifiant = identifiant;
        this.type = type;
        this.gain = gain;
    }

    public void addConnection(Sommet s, int distance) {
        this.connections.putIfAbsent(s, distance);
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getType() {
        return type;
    }

    public int getGain() {
        return gain;
    }
}