package ui;

import java.awt.*;
import javax.swing.*;
import model.User;
import service.AuthService;
import service.AccountService;

public class LoginPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField emailField;
    private JPasswordField passwordField;

    private JTextField regNameField, regEmailField;
    private JPasswordField regPassField, regConfirmPassField;
    private JComboBox<String> regTypeCombo;

    private AuthService authService = new AuthService();
    private AccountService accountService = new AccountService();

    private JPanel cardPanel;
    private CardLayout cardLayout;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(Color.decode("#0F172A"));

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.decode("#0F172A"));

        cardPanel.add(createLoginPanelInner(), "Login");
        cardPanel.add(createRegisterPanelInner(), "Register");

        add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createLoginPanelInner() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.decode("#0F172A"));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.decode("#0F172A"));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Icon
        JLabel iconLabel = new JLabel("\uD83C\uDFE6");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(iconLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Email
        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(new Font("Inter", Font.BOLD, 14));
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setMaximumSize(new Dimension(300, 20));
        formPanel.add(emailLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        emailField = new JTextField();
        emailField.putClientProperty("JTextField.placeholderText", "your@email.com");
        emailField.setPreferredSize(new Dimension(300, 40));
        emailField.setMaximumSize(new Dimension(300, 40));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(emailField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Password
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Inter", Font.BOLD, 14));
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setMaximumSize(new Dimension(300, 20));
        formPanel.add(passLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(300, 40));
        passwordField.setMaximumSize(new Dimension(300, 40));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(passwordField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Login Button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(Color.decode("#38BDF8"));
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFont(new Font("Inter", Font.BOLD, 18));
        loginBtn.setFocusPainted(false);
        loginBtn.setPreferredSize(new Dimension(300, 40));
        loginBtn.setMaximumSize(new Dimension(300, 40));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.addActionListener(e -> attemptLogin());
        formPanel.add(loginBtn);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Register Button
        JButton registerBtn = new JButton("Don't have an account? Register");
        registerBtn.setContentAreaFilled(false);
        registerBtn.setBorderPainted(false);
        registerBtn.setFocusPainted(false);
        registerBtn.setForeground(Color.decode("#94A3B8"));
        registerBtn.setFont(new Font("Inter", Font.PLAIN, 12));
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.addActionListener(e -> {
            mainFrame.setTitle("Mini Banking System \u2014 Register");
            cardLayout.show(cardPanel, "Register");
        });
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(registerBtn);

        wrapper.add(formPanel);
        return wrapper;
    }

    private JPanel createRegisterPanelInner() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.decode("#0F172A"));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.decode("#0F172A"));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel("Create Account");
        headerLabel.setFont(new Font("Inter", Font.BOLD, 24));
        headerLabel.setForeground(Color.decode("#38BDF8"));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(headerLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setFont(new Font("Inter", Font.BOLD, 14));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setMaximumSize(new Dimension(300, 20));
        formPanel.add(nameLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        regNameField = new JTextField();
        regNameField.putClientProperty("JTextField.placeholderText", "Jane Doe");
        regNameField.setPreferredSize(new Dimension(300, 40));
        regNameField.setMaximumSize(new Dimension(300, 40));
        regNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(regNameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(new Font("Inter", Font.BOLD, 14));
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setMaximumSize(new Dimension(300, 20));
        formPanel.add(emailLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        regEmailField = new JTextField();
        regEmailField.putClientProperty("JTextField.placeholderText", "jane@email.com");
        regEmailField.setPreferredSize(new Dimension(300, 40));
        regEmailField.setMaximumSize(new Dimension(300, 40));
        regEmailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(regEmailField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Inter", Font.BOLD, 14));
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setMaximumSize(new Dimension(300, 20));
        formPanel.add(passLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        regPassField = new JPasswordField();
        regPassField.putClientProperty("JTextField.placeholderText", "Min. 6 characters");
        regPassField.setPreferredSize(new Dimension(300, 40));
        regPassField.setMaximumSize(new Dimension(300, 40));
        regPassField.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(regPassField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel confirmPassLabel = new JLabel("Confirm Password");
        confirmPassLabel.setFont(new Font("Inter", Font.BOLD, 14));
        confirmPassLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPassLabel.setMaximumSize(new Dimension(300, 20));
        formPanel.add(confirmPassLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        regConfirmPassField = new JPasswordField();
        regConfirmPassField.putClientProperty("JTextField.placeholderText", "Re-enter password");
        regConfirmPassField.setPreferredSize(new Dimension(300, 40));
        regConfirmPassField.setMaximumSize(new Dimension(300, 40));
        regConfirmPassField.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(regConfirmPassField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel typeLabel = new JLabel("Account Type");
        typeLabel.setFont(new Font("Inter", Font.BOLD, 14));
        typeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        typeLabel.setMaximumSize(new Dimension(300, 20));
        formPanel.add(typeLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        regTypeCombo = new JComboBox<>(new String[]{"Savings Account", "Current Account", "Admin Account"});
        regTypeCombo.setPreferredSize(new Dimension(300, 40));
        regTypeCombo.setMaximumSize(new Dimension(300, 40));
        regTypeCombo.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(regTypeCombo);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton regBtn = new JButton("Create Account");
        regBtn.setBackground(Color.decode("#34D399"));
        regBtn.setForeground(Color.BLACK);
        regBtn.setFont(new Font("Inter", Font.BOLD, 16));
        regBtn.setFocusPainted(false);
        regBtn.setPreferredSize(new Dimension(300, 40));
        regBtn.setMaximumSize(new Dimension(300, 40));
        regBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        regBtn.addActionListener(e -> register());
        formPanel.add(regBtn);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton backBtn = new JButton("\u2190 Back to Login");
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setForeground(Color.decode("#94A3B8"));
        backBtn.setFont(new Font("Inter", Font.PLAIN, 12));
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> {
            mainFrame.setTitle("Mini Banking System \u2014 Login");
            cardLayout.show(cardPanel, "Login");
        });
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(backBtn);

        wrapper.add(formPanel);
        return wrapper;
    }

    private void attemptLogin() {
        String email = emailField.getText();
        String pwd = new String(passwordField.getPassword());
        
        try {
            User user = authService.login(email, pwd);
            if ("ADMIN".equals(user.getRole())) {
                mainFrame.showAdminDashboard(user);
            } else {
                mainFrame.showCustomerDashboard(user);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void register() {
        String name = regNameField.getText();
        String email = regEmailField.getText();
        String pass = new String(regPassField.getPassword());
        String confirmPass = new String(regConfirmPassField.getPassword());
        String type = (String) regTypeCombo.getSelectedItem();

        if (!pass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            accountService.register(name, email, pass, type);
            JOptionPane.showMessageDialog(this, "Registration submitted.");
            mainFrame.setTitle("Mini Banking System \u2014 Login");
            cardLayout.show(cardPanel, "Login");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
