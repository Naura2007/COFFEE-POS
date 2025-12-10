package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import model.*;
import util.JsonDB;
import service.MenuService;
import service.ToppingService;

public class POSGUI extends JFrame {

    private List<MenuItemModel> menus;
    private List<Topping> toppings;
    private List<OrderItem> orderItems = new ArrayList<>();

    private JComboBox<MenuItemModel> cbMenu;
    private JComboBox<Object> cbTopping; // Bisa null / "Tidak Ada"
    private JSpinner spQty;
    private JLabel lblTotal;
    private DefaultTableModel orderTableModel;

    public POSGUI() {
        setTitle("Cashier - Coffee POS");
        setSize(850,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        menus = MenuService.loadAll();
        toppings = ToppingService.loadAll();

        buildUI();
    }

    private void buildUI() {
        JPanel panelTop = new JPanel(new FlowLayout());

        cbMenu = new JComboBox<>(menus.toArray(new MenuItemModel[0]));
        cbTopping = new JComboBox<>();
        cbTopping.addItem("Tidak Ada"); // Default
        for(Topping t : toppings) cbTopping.addItem(t);
        spQty = new JSpinner(new SpinnerNumberModel(1,1,100,1));
        JButton btnAdd = new JButton("Tambah ke Order");

        panelTop.add(new JLabel("Menu:")); panelTop.add(cbMenu);
        panelTop.add(new JLabel("Topping:")); panelTop.add(cbTopping);
        panelTop.add(new JLabel("Qty:")); panelTop.add(spQty);
        panelTop.add(btnAdd);

        add(panelTop, BorderLayout.NORTH);

        orderTableModel = new DefaultTableModel(new Object[]{"Nama","Topping","Qty","Harga","Subtotal"},0){
            public boolean isCellEditable(int r,int c){return false;}
        };
        JTable orderTable = new JTable(orderTableModel);
        add(new JScrollPane(orderTable), BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new FlowLayout());
        lblTotal = new JLabel("Total: 0");
        JButton btnPay = new JButton("Bayar");
        panelBottom.add(lblTotal);
        panelBottom.add(btnPay);

        add(panelBottom, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addOrderItem());
        btnPay.addActionListener(e -> payOrder());

        refreshOrderTable();
        updateTotal();
    }

    private void addOrderItem() {
        MenuItemModel mi = (MenuItemModel) cbMenu.getSelectedItem();
        Object toppingObj = cbTopping.getSelectedItem();
        Topping tp = (toppingObj instanceof Topping) ? (Topping) toppingObj : null;
        int qty = (int) spQty.getValue();
        if(mi == null) return;

        int menuId = Integer.parseInt(mi.getId().replaceAll("\\D",""));
        int price = mi.getPrice() + (tp != null ? tp.getPrice() : 0);

        OrderItem oi = new OrderItem(menuId, mi.getName(), price, qty);
        orderItems.add(oi);
        refreshOrderTable();
        updateTotal();
    }

    private void refreshOrderTable() {
        orderTableModel.setRowCount(0);
        for(OrderItem oi : orderItems){
            String toppingName = "-";
            for(Topping t : toppings){
                if(oi.getName().equals(t.getName())) toppingName = t.getName();
            }
            orderTableModel.addRow(new Object[]{
                oi.getName(),
                toppingName,
                oi.getQty(),
                oi.getPrice(),
                oi.subtotal()
            });
        }
    }

    private void updateTotal() {
        int total = 0;
        for(OrderItem oi : orderItems) total += oi.subtotal();
        lblTotal.setText("Total: " + total);
    }

    private void payOrder() {
        if(orderItems.isEmpty()) return;

        String[] options = {"Cash","Debit","QRIS"};
        String method = (String) JOptionPane.showInputDialog(
            this, "Pilih metode pembayaran:", "Bayar",
            JOptionPane.PLAIN_MESSAGE, null, options, options[0]
        );
        if(method == null) return;

        StringBuilder receipt = new StringBuilder();
        receipt.append("===== STRUK COFFEE POS =====\n");
        receipt.append("Tanggal: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())).append("\n");
        receipt.append("Metode Bayar: ").append(method).append("\n");
        receipt.append("------------------------------\n");
        for(OrderItem oi : orderItems){
            receipt.append(String.format("%s x%d = %d\n", oi.getName(), oi.getQty(), oi.subtotal()));
        }
        receipt.append("------------------------------\n");
        receipt.append(lblTotal.getText()).append("\n");
        receipt.append("===== TERIMA KASIH =====\n");

        JOptionPane.showMessageDialog(this, receipt.toString(), "Struk", JOptionPane.INFORMATION_MESSAGE);

        orderItems.clear();
        refreshOrderTable();
        updateTotal();
    }
}
