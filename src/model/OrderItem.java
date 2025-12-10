package model;

public class OrderItem {
    private int menuId;
    private String name;
    private int price;
    private int qty;

    public OrderItem(int id, String name, int price, int qty) {
        this.menuId = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public int getMenuId() { return menuId; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getQty() { return qty; }

    public void setQty(int q) { this.qty = q; }

    public int subtotal() { return price * qty; }
}
