package model;

public class MenuItemModel {
    private String id;
    private String name;
    private int price;

    public MenuItemModel(String id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }

    @Override
    public String toString() {
        return name; // tampil di JComboBox
    }
}
