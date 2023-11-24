package at.wirthi.learningspring.util;

public class Session {
    public static String findSession(String data) {
        int idxSessionStart = data.indexOf("sessionID") + 11; //including sessionID="
        int idxSessionEnd = data.indexOf("\"", idxSessionStart);
        System.out.println("Session index: " + idxSessionStart + " " + idxSessionEnd);
        if (idxSessionStart <= 0 || idxSessionEnd <= 0) {
            Util.errorAndTerminate("session not found", data);
        }
        String sessionID = data.substring(idxSessionStart, idxSessionEnd);
        return sessionID;
    }
}
