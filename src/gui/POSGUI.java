package gui;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;
import service.MenuService;
import service.ToppingService;

public class POSGUI extends JFrame {

    private List<MenuItemModel> menus;
    private List<Topping> toppings;
    private List<OrderItem> orderItems = new ArrayList<>();

    private JComboBox<MenuItemModel> cbMenu;
    private JPanel panelToppings; // Bisa null / "Tidak Ada" ataupun multi
    private List<JCheckBox> toppingCheckboxes = new ArrayList<>();
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

        // Create JComboBox with custom renderer to show price
        cbMenu = new JComboBox<>(menus.toArray(new MenuItemModel[0]));
        
        // Set custom renderer to display name and price
        cbMenu.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                if (value instanceof MenuItemModel) {
                    MenuItemModel menu = (MenuItemModel) value;
                    setText(menu.getName() + " - Rp" + menu.getPrice());
                }
                return this;
            }
        });
        
        // === DROPDOWN PANEL FOR TOPPINGS ===
        JPanel dropdownWrapper = new JPanel(new BorderLayout());
        JButton btnDropdown = new JButton("Pilih Topping ▼");

        panelToppings = new JPanel();
        panelToppings.setLayout(new BoxLayout(panelToppings, BoxLayout.Y_AXIS));

        for (Topping t : toppings) {
            JCheckBox cb = new JCheckBox(t.getName() + " (+" + t.getPrice() + ")");
            toppingCheckboxes.add(cb);
            panelToppings.add(cb);
            panelToppings.add(Box.createRigidArea(new Dimension(0, 2)));
        }

        JScrollPane spToppings = new JScrollPane(panelToppings);
        spToppings.setPreferredSize(new Dimension(200, 150));
        spToppings.setVisible(false);

        btnDropdown.addActionListener(e -> {
            boolean visible = !spToppings.isVisible();
            spToppings.setVisible(visible);
            btnDropdown.setText(visible ? "Pilih Topping ▲" : "Pilih Topping ▼");
            
            dropdownWrapper.revalidate();
            dropdownWrapper.repaint();
        });

        dropdownWrapper.add(btnDropdown, BorderLayout.NORTH);
        dropdownWrapper.add(spToppings, BorderLayout.CENTER);

        dropdownWrapper.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        spQty = new JSpinner(new SpinnerNumberModel(1,1,100,1));
        JButton btnAdd = new JButton("Tambah ke Order");

        panelTop.add(new JLabel("Menu:")); panelTop.add(cbMenu);
        panelTop.add(new JLabel("Topping(s):")); panelTop.add(dropdownWrapper);
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
        List<Topping> selectedToppings = new ArrayList<>();
        for (int i = 0; i < toppingCheckboxes.size(); i++) {
            if (toppingCheckboxes.get(i).isSelected()) {
                selectedToppings.add(toppings.get(i));
            }
        }

        int qty = (int) spQty.getValue();
        if(mi == null) return;

        int menuId = Integer.parseInt(mi.getId().replaceAll("\\D",""));
        OrderItem oi = new OrderItem(
            menuId,
            mi.getName(),
            mi.getPrice(),
            qty,
            new ArrayList<>(selectedToppings)
        );

        orderItems.add(oi);
        for (JCheckBox cb : toppingCheckboxes) cb.setSelected(false);
        refreshOrderTable();
        updateTotal();
    }

    private void refreshOrderTable() {
        orderTableModel.setRowCount(0);
        
        for (OrderItem oi : orderItems) {
            // Calculate menu base subtotal (without toppings)
            int menuBaseSubtotal = oi.getPrice() * oi.getQty();
            
            // Add main menu item row
            orderTableModel.addRow(new Object[]{
                oi.getName() + " x" + oi.getQty(),
                "", // Empty for topping column
                oi.getQty(),
                oi.getPrice(),
                menuBaseSubtotal  // Only menu price * qty
            });
            
            // Add topping rows as child rows
            if (oi.getToppings() != null && !oi.getToppings().isEmpty()) {
                for (Topping topping : oi.getToppings()) {
                    orderTableModel.addRow(new Object[]{
                        "    └─ " + topping.getName()
                    });
                }
            }
            
            orderTableModel.addRow(new Object[]{"", "", "", "", ""});
        }
        
        if (orderTableModel.getRowCount() > 0 && 
            orderTableModel.getValueAt(orderTableModel.getRowCount() - 1, 0) == "") {
            orderTableModel.removeRow(orderTableModel.getRowCount() - 1);
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
        receipt.append("======================\n");
        receipt.append("        STRUK COFFEE POS        \n");
        receipt.append("======================\n");
        receipt.append("Tanggal: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())).append("\n");
        receipt.append("Metode Bayar: ").append(method).append("\n");
        receipt.append("---------------------------\n");
        
        int grandTotal = 0;
        
        for(OrderItem oi : orderItems){          
            receipt.append(String.format("%s x%d\n", oi.getName(), oi.getQty()));
            
            if(oi.getToppings() != null && !oi.getToppings().isEmpty()) {
                for(Topping topping : oi.getToppings()) {
                    receipt.append(String.format("    • %-16s\n", 
                        topping.getName()));
                }
            }

            receipt.append(String.format("  %-20s Rp %,d\n", 
                "Subtotal:", oi.subtotal()));
            receipt.append("\n");
            
            grandTotal += oi.subtotal();
        }
        
        receipt.append("---------------------------\n");
        receipt.append(String.format("%-20s Rp %,d\n", "TOTAL:", grandTotal));
        receipt.append("---------------------------\n");
        receipt.append("        TERIMA KASIH            \n");
        receipt.append("======================\n");

        JOptionPane.showMessageDialog(this, receipt.toString(), "Struk", JOptionPane.INFORMATION_MESSAGE);

        orderItems.clear();
        refreshOrderTable();
        updateTotal();
    }
}
