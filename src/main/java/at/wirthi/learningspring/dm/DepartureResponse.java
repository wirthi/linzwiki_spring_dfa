package at.wirthi.learningspring.dm;

import java.util.List;

public class DepartureResponse {
    private final boolean stopIdentified;
    private final List<Departure> list;
    private final String responseStr;

    public DepartureResponse(List<Departure> list, boolean stopIdentified, String responseStr) {
        this.list = list;
        this.stopIdentified = stopIdentified;
        this.responseStr = responseStr;
    }

    public List<Departure> getList() {
        return list;
    }

    public boolean isStopIdentified() {
        return stopIdentified;
    }

    public String getResponseStr() {
        return responseStr;
    }
}
