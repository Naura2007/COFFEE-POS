package model;

import java.util.List;

public class OrderItem {
    private int menuId;
    private String name;
    private int basePrice;
    private int qty;
    private List<Topping> toppings;

    public OrderItem(int menuId, String name, int basePrice, int qty, List<Topping> toppings) {
        this.menuId = menuId;
        this.name = name;
        this.basePrice = basePrice;
        this.qty = qty;
        this.toppings = toppings;
    }

    // ====== Getters you need (these MUST exist) ======
    public int getMenuId() {
        return menuId;
    }

    public String getName() {
        return name;   // <-- YOU DO HAVE THIS
    }

    public int getQty() {
        return qty;    // <-- YOU DO HAVE THIS
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    // ====== Price calculation ======
    public int getPrice() {
        int toppingSum = 0;
        for (Topping t : toppings) {
            toppingSum += t.getPrice();
        }
        return basePrice + toppingSum;
    }

    public int subtotal() {
        return getPrice() * qty;
    }

    public String toppingNames() {
        if (toppings == null || toppings.isEmpty()) {
            return "-";
        }

        StringBuilder sb = new StringBuilder();
        for (Topping t : toppings) {
            sb.append(t.getName()).append(", ");
        }
        sb.setLength(sb.length() - 2); // remove last ", "

        return sb.toString();
    }
}
