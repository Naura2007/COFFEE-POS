package gui;

import javax.swing.*;
import java.awt.*;
import model.User;
import service.UserService;

public class LoginGUI extends JFrame {
    private JTextField tfUser;
    private JPasswordField pfPass;

    public LoginGUI() {
        setTitle("Login - Coffee POS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(360,220);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        panel.add(new JLabel("Username:"));
        tfUser = new JTextField();
        panel.add(tfUser);

        panel.add(new JLabel("Password:"));
        pfPass = new JPasswordField();
        panel.add(pfPass);

        JButton btn = new JButton("Login");
        panel.add(new JLabel());
        panel.add(btn);

        btn.addActionListener(e -> {
            String u = tfUser.getText().trim();
            String pw = new String(pfPass.getPassword());
            User user = UserService.authenticate(u, pw);

            if (user == null) {
                JOptionPane.showMessageDialog(this, "Login gagal!");
                return;
            }

            // panggil AdminGUI atau POSGUI sesuai role
            if ("admin".equalsIgnoreCase(user.getRole())) {
                new AdminGUI().setVisible(true); // tidak perlu parameter
            } else {
                new POSGUI().setVisible(true);
            }
            dispose();
        });

        add(panel);
    }
}
