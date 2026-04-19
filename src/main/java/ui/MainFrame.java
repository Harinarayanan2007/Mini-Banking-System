package ui;

import java.awt.*;
import javax.swing.*;
import model.User;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("Mini Banking System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.decode("#0F172A"));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);

        showLogin();
    }

    public void showLogin() {
        setTitle("Mini Banking System \u2014 Login");
        mainPanel.removeAll();
        mainPanel.add(new LoginPanel(this), "Login");
        cardLayout.show(mainPanel, "Login");
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void showCustomerDashboard(User user) {
        setTitle("Customer Dashboard - " + user.getFullName());
        mainPanel.removeAll();
        mainPanel.add(new CustomerDashboardPanel(this, user), "Customer");
        cardLayout.show(mainPanel, "Customer");
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void showAdminDashboard(User user) {
        setTitle("Admin Dashboard - " + user.getFullName());
        mainPanel.removeAll();
        mainPanel.add(new AdminDashboardPanel(this, user), "Admin");
        cardLayout.show(mainPanel, "Admin");
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
