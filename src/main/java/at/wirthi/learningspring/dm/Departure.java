package at.wirthi.learningspring.dm;

public class Departure {
    private int countdown;
    private int lineNumber;
    private String direction;

    public Departure(int countdown, int lineNumber, String direction) {
        this.countdown = countdown;
        this.lineNumber = lineNumber;
        this.direction = direction;
    }

    public int getCountdown() {
        return countdown;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getDirection() {
        return direction;
    }
}
