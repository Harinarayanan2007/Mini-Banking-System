import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import ui.MainFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame.setDefaultLookAndFeelDecorated(true);
                UIManager.setLookAndFeel(new FlatDarkLaf());
                UIManager.put("Panel.background", Color.decode("#0F172A"));
                UIManager.put("Window.background", Color.decode("#0F172A"));
                UIManager.put("TitlePane.background", Color.decode("#0F172A"));
                UIManager.put("TitlePane.inactiveBackground", Color.decode("#0F172A"));
                UIManager.put("TitlePane.foreground", Color.decode("#FFFFFF"));
                UIManager.put("Button.arc", 8);
                UIManager.put("Component.arc", 8);
                UIManager.put("TextComponent.arc", 8);
                UIManager.put("TextField.background", Color.decode("#1E293B"));
                UIManager.put("PasswordField.background", Color.decode("#1E293B"));
                UIManager.put("ComboBox.background", Color.decode("#1E293B"));
                UIManager.put("Label.foreground", Color.decode("#94A3B8"));
                UIManager.put("Label.font", new Font("Inter", Font.PLAIN, 14));
            } catch (Exception ex) {
                System.err.println("Failed to initialize FlatDarkLaf");
            }
            new MainFrame().setVisible(true);
        });
    }
}
