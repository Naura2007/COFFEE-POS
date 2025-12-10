package model;

public class Membership {
    private String username;
    private int points;

    public Membership(String username, int points) {
        this.username = username;
        this.points = points;
    }

    public String getUsername() { return username; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}
