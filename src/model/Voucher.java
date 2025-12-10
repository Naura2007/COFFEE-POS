package model;

public class Voucher {
    private String code;
    private int amount;

    public Voucher(String code, int amount) {
        this.code = code; this.amount = amount;
    }

    public String getCode() { return code; }
    public int getAmount() { return amount; }
}
