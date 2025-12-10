package model;

public class Discount {
    private String code;
    private int percent;

    public Discount(String code, int percent) {
        this.code = code;
        this.percent = percent;
    }

    public String getCode() { return code; }
    public int getPercent() { return percent; }
}
