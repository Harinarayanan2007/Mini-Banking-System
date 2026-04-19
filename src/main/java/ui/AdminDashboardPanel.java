package ui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.User;
import service.AdminService;

public class AdminDashboardPanel extends JPanel {
    private MainFrame mainFrame;
    private User currentUser;
    private AdminService adminService = new AdminService();
    private JTable pendingTable;
    private DefaultTableModel tableModel;

    public AdminDashboardPanel(MainFrame mainFrame, User user) {
        this.mainFrame = mainFrame;
        this.currentUser = user;
        setLayout(new BorderLayout());

        initComponents();
        refreshTable();
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(new String[]{"User ID", "Full Name", "Email", "Account Type", "Status"}, 0);
        pendingTable = new JTable(tableModel);
        add(new JScrollPane(pendingTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton approveBtn = new JButton("Approve Selected");
        approveBtn.addActionListener(e -> approveSelected());
        bottomPanel.add(approveBtn);

        JButton rejectBtn = new JButton("Reject Selected");
        rejectBtn.addActionListener(e -> rejectSelected());
        bottomPanel.add(rejectBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            mainFrame.showLogin();
        });
        bottomPanel.add(logoutBtn);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<User> list = adminService.getPendingAccounts();
            for (User u : list) {
                tableModel.addRow(new Object[]{u.getId(), u.getFullName(), u.getEmail(), u.getAccountType(), u.getStatus()});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void approveSelected() {
        int selectedRow = pendingTable.getSelectedRow();
        if (selectedRow >= 0) {
            int userId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                adminService.approveAccount(userId);
                JOptionPane.showMessageDialog(this, "Account Approved successfully!");
                refreshTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row first.");
        }
    }

    private void rejectSelected() {
        int selectedRow = pendingTable.getSelectedRow();
        if (selectedRow >= 0) {
            int userId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                adminService.rejectAccount(userId);
                JOptionPane.showMessageDialog(this, "Account Rejected successfully!");
                refreshTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row first.");
        }
    }
}
