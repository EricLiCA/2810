public class Arc implements Comparable<Arc> {
    private int distance;
    private String firstNode;
    private String secondNode;

    public Arc(int distance, String firstNode, String secondNode) {
        this.distance = distance;
        this.firstNode = firstNode;
        this.secondNode = secondNode;
    }

    public int getDistance() {
        return distance;
    }

    public String getFirstNode() {
        return firstNode;
    }

    public String getSecondNode() {
        return secondNode;
    }

    @Override
    public int compareTo(Arc otherArc) {
        return (this.distance < otherArc.getDistance()) ? -1 : (this.distance > otherArc.getDistance()) ? 1 : 0;
    }
}