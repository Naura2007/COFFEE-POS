package gui;

import javax.swing.*;
import java.awt.*;

public class ReceiptWindow extends JFrame {

    public ReceiptWindow(String receipt) {
        setTitle("Receipt");
        setSize(400,500);
        setLocationRelativeTo(null);

        JTextArea ta = new JTextArea(receipt);
        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(ta));
    }
}
