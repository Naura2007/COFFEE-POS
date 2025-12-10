package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.*;
import util.JsonDB;
import service.MenuService;
import service.ToppingService;

public class AdminGUI extends JFrame {

    private List<MenuItemModel> menuItems;
    private List<Topping> toppingItems;

    private DefaultTableModel menuTableModel;
    private DefaultTableModel toppingTableModel;

    public AdminGUI() {
        setTitle("Admin Panel - Coffee POS");
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        menuItems = MenuService.loadAll();
        toppingItems = ToppingService.loadAll();

        buildUI();
    }

    private void buildUI() {
        JTabbedPane tab = new JTabbedPane();

        // ===== MENU PANEL =====
        JPanel menuPanel = new JPanel(new BorderLayout());

        menuTableModel = new DefaultTableModel(new Object[]{"ID","Nama","Harga"},0){
            public boolean isCellEditable(int r,int c){return false;}
        };
        JTable menuTable = new JTable(menuTableModel);
        menuPanel.add(new JScrollPane(menuTable), BorderLayout.CENTER);

        JPanel menuButtons = new JPanel();
        JButton btnAddMenu = new JButton("Tambah Menu");
        JButton btnEditMenu = new JButton("Edit Menu");
        JButton btnRemoveMenu = new JButton("Hapus Menu");
        menuButtons.add(btnAddMenu);
        menuButtons.add(btnEditMenu);
        menuButtons.add(btnRemoveMenu);
        menuPanel.add(menuButtons, BorderLayout.SOUTH);

        btnAddMenu.addActionListener(e -> addMenu());
        btnEditMenu.addActionListener(e -> {
            int r = menuTable.getSelectedRow();
            if(r>=0) editMenu(r);
        });
        btnRemoveMenu.addActionListener(e -> {
            int r = menuTable.getSelectedRow();
            if(r>=0){
                menuItems.remove(r);
                JsonDB.save("data/menu.json", menuItems);
                refreshMenuTable();
            }
        });

        tab.add("Menu", menuPanel);

        // ===== TOPPING PANEL =====
        JPanel toppingPanel = new JPanel(new BorderLayout());

        toppingTableModel = new DefaultTableModel(new Object[]{"ID","Nama","Harga"},0){
            public boolean isCellEditable(int r,int c){return false;}
        };
        JTable toppingTable = new JTable(toppingTableModel);
        toppingPanel.add(new JScrollPane(toppingTable), BorderLayout.CENTER);

        JPanel toppingButtons = new JPanel();
        JButton btnAddTopping = new JButton("Tambah Topping");
        JButton btnEditTopping = new JButton("Edit Topping");
        JButton btnRemoveTopping = new JButton("Hapus Topping");
        toppingButtons.add(btnAddTopping);
        toppingButtons.add(btnEditTopping);
        toppingButtons.add(btnRemoveTopping);
        toppingPanel.add(toppingButtons, BorderLayout.SOUTH);

        btnAddTopping.addActionListener(e -> addTopping());
        btnEditTopping.addActionListener(e -> {
            int r = toppingTable.getSelectedRow();
            if(r>=0) editTopping(r);
        });
        btnRemoveTopping.addActionListener(e -> {
            int r = toppingTable.getSelectedRow();
            if(r>=0){
                toppingItems.remove(r);
                JsonDB.save("data/toppings.json", toppingItems);
                refreshToppingTable();
            }
        });

        tab.add("Topping", toppingPanel);

        add(tab, BorderLayout.CENTER);

        refreshMenuTable();
        refreshToppingTable();
    }

    // ===== MENU METHODS =====
    private void addMenu() {
        String name = JOptionPane.showInputDialog(this, "Nama Menu:");
        if(name==null || name.trim().isEmpty()) return;

        String priceStr = JOptionPane.showInputDialog(this, "Harga Menu:");
        int price;
        try { price = Integer.parseInt(priceStr.trim()); }
        catch(Exception e){ JOptionPane.showMessageDialog(this,"Harga tidak valid!"); return; }

        // ===== Generate ID otomatis berdasarkan menu terakhir =====
        int newId = 1;
        if(!menuItems.isEmpty()){
            MenuItemModel last = menuItems.get(menuItems.size()-1);
            try { newId = Integer.parseInt(last.getId()) + 1; }
            catch(Exception ignored){}
        }

        MenuItemModel m = new MenuItemModel(String.valueOf(newId), name, price);
        menuItems.add(m);
        JsonDB.save("data/menu.json", menuItems);
        refreshMenuTable();
    }

    private void editMenu(int index) {
        MenuItemModel m = menuItems.get(index);
        String name = JOptionPane.showInputDialog(this, "Nama Menu:", m.getName());
        if(name==null || name.trim().isEmpty()) return;

        String priceStr = JOptionPane.showInputDialog(this, "Harga Menu:", m.getPrice());
        int price;
        try { price = Integer.parseInt(priceStr.trim()); }
        catch(Exception e){ JOptionPane.showMessageDialog(this,"Harga tidak valid!"); return; }

        menuItems.set(index, new MenuItemModel(m.getId(), name, price));
        JsonDB.save("data/menu.json", menuItems);
        refreshMenuTable();
    }

    private void refreshMenuTable() {
        menuTableModel.setRowCount(0);
        for(MenuItemModel m : menuItems){
            menuTableModel.addRow(new Object[]{m.getId(), m.getName(), m.getPrice()});
        }
    }

    // ===== TOPPING METHODS =====
    private void addTopping() {
        String name = JOptionPane.showInputDialog(this, "Nama Topping:");
        if(name==null || name.trim().isEmpty()) return;

        String priceStr = JOptionPane.showInputDialog(this, "Harga Topping:");
        int price;
        try { price = Integer.parseInt(priceStr.trim()); }
        catch(Exception e){ JOptionPane.showMessageDialog(this,"Harga tidak valid!"); return; }

        int newId = 1;
        if(!toppingItems.isEmpty()){
            Topping last = toppingItems.get(toppingItems.size()-1);
            try { newId = Integer.parseInt(last.getId()) + 1; }
            catch(Exception ignored){}
        }

        Topping t = new Topping(String.valueOf(newId), name, price);
        toppingItems.add(t);
        JsonDB.save("data/toppings.json", toppingItems);
        refreshToppingTable();
    }

    private void editTopping(int index) {
        Topping t = toppingItems.get(index);
        String name = JOptionPane.showInputDialog(this, "Nama Topping:", t.getName());
        if(name==null || name.trim().isEmpty()) return;

        String priceStr = JOptionPane.showInputDialog(this, "Harga Topping:", t.getPrice());
        int price;
        try { price = Integer.parseInt(priceStr.trim()); }
        catch(Exception e){ JOptionPane.showMessageDialog(this,"Harga tidak valid!"); return; }

        toppingItems.set(index, new Topping(t.getId(), name, price));
        JsonDB.save("data/toppings.json", toppingItems);
        refreshToppingTable();
    }

    private void refreshToppingTable() {
        toppingTableModel.setRowCount(0);
        for(Topping t : toppingItems){
            toppingTableModel.addRow(new Object[]{t.getId(), t.getName(), t.getPrice()});
        }
    }
}
