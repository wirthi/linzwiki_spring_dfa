package at.wirthi.learningspring.util;

public enum LogDetail {
    MINIMAL(1),
    NORMAL(2),
    DETAILED(3);

    public final int level;

    private LogDetail(int level) {
        this.level = level;
    }
}
