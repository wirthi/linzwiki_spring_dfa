package at.wirthi.learningspring.stopsearch;

public class LinzAGStop {
    private final String name;
    private final String stopID;

    public LinzAGStop(String name, String stopID) {
        this.name = name;
        this.stopID = stopID;
    }

    public String getName() {
        return name;
    }

    public String getStopID() {
        return stopID;
    }
}
