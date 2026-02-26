package api.models;

public class ToggleRequest {
    private String date;

    public ToggleRequest() {}

    public ToggleRequest(String date) {
        this.date = date;
    }

    public String getDate() { return date; }
}
