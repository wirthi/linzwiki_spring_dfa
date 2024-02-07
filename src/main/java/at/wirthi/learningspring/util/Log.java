package at.wirthi.learningspring.util;

public class Log {
    public static void warn(String msg) {
        System.out.println("WARNING: "+msg);
    }

    public static void log(String msg, LogDetail level) {
        if (Config.logDetail.level >= level.level) {
            System.out.println(msg);
        }
    }

}
