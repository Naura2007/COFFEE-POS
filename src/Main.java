import com.formdev.flatlaf.FlatLightLaf;
import gui.LoginGUI;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        new LoginGUI().setVisible(true);
    }
}
