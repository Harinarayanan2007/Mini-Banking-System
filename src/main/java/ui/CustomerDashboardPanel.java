package ui;

import dao.AccountDAO;
import dao.TransactionDAO;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import model.Account;
import model.Transaction;
import model.User;
import service.AccountService;

public class CustomerDashboardPanel extends JPanel {
    private MainFrame mainFrame;
    private User currentUser;
    private AccountDAO accountDAO = new AccountDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();
    private AccountService accountService = new AccountService();

    private JLabel balanceLabel;
    private JTable txTable;
    private DefaultTableModel txModel;
    private Account currentAccount;

    private JPanel cardPanel;
    private CardLayout cardLayout;

    private JLabel accNameVal, accEmailVal, accNoVal, accBalVal, accStatusVal, accDateVal;

    public CustomerDashboardPanel(MainFrame mainFrame, User user) {
        this.mainFrame = mainFrame;
        this.currentUser = user;
        setLayout(new BorderLayout());
        setBackground(Color.decode("#0F172A"));

        initData();
        initComponents();
        refreshTransactions();
    }

    private void initData() {
        try {
            currentAccount = accountDAO.findByUserId(currentUser.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        // TOP PANEL
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.decode("#0F172A"));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel topLeft = new JPanel();
        topLeft.setLayout(new BoxLayout(topLeft, BoxLayout.Y_AXIS));
        topLeft.setBackground(Color.decode("#0F172A"));

        String accNoStr = currentAccount != null ? currentAccount.getAccountNo() : "N/A";
        JLabel accLabel = new JLabel("Account: " + accNoStr);
        accLabel.setFont(new Font("Inter", Font.PLAIN, 12));
        accLabel.setForeground(Color.decode("#94A3B8"));
        topLeft.add(accLabel);

        balanceLabel = new JLabel("\u20B9 " + (currentAccount != null ? currentAccount.getBalance() : "0.00"));
        balanceLabel.setFont(new Font("Inter", Font.BOLD, 28));
        balanceLabel.setForeground(Color.decode("#34D399"));
        topLeft.add(balanceLabel);

        topPanel.add(topLeft, BorderLayout.WEST);

        JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        topRight.setBackground(Color.decode("#0F172A"));

        JLabel userLabel = new JLabel("\uD83D\uDC4B " + currentUser.getFullName()); // wave emoji
        userLabel.setFont(new Font("Inter", Font.BOLD, 14));
        userLabel.setForeground(Color.decode("#FBBF24"));
        topRight.add(userLabel);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(Color.decode("#334155"));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.addActionListener(e -> {
            mainFrame.showLogin();
        });
        topRight.add(logoutBtn);

        topPanel.add(topRight, BorderLayout.EAST);

        // NAV BAR
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        navBar.setBackground(Color.decode("#0F172A"));
        navBar.setBorder(new MatteBorder(0, 0, 1, 0, Color.decode("#334155")));

        String[] tabs = {"Account", "Deposit", "Withdraw", "Transfer", "History"};
        ButtonGroup navGroup = new ButtonGroup();
        for (String tab : tabs) {
            JToggleButton btn = new JToggleButton(tab);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setForeground(Color.decode("#94A3B8"));
            btn.setFont(new Font("Inter", Font.BOLD, 14));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.addActionListener(e -> cardLayout.show(cardPanel, tab));
            btn.addItemListener(e -> {
                if (btn.isSelected()) {
                    btn.setForeground(Color.WHITE);
                    btn.setBorderPainted(true);
                    btn.setBorder(new MatteBorder(0, 0, 3, 0, Color.decode("#38BDF8")));
                } else {
                    btn.setForeground(Color.decode("#94A3B8"));
                    btn.setBorderPainted(false);
                    btn.setBorder(null);
                }
            });
            navGroup.add(btn);
            navBar.add(btn);
            if(tab.equals("Account")) btn.setSelected(true);
        }

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(topPanel, BorderLayout.NORTH);
        headerPanel.add(navBar, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

        // Center Cards
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.decode("#0F172A"));

        cardPanel.add(createAccountCard(), "Account");
        cardPanel.add(createDepositCard(), "Deposit");
        cardPanel.add(createWithdrawCard(), "Withdraw");
        cardPanel.add(createTransferCard(), "Transfer");
        cardPanel.add(createHistoryCard(), "History");

        add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createAccountCard() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.decode("#0F172A"));
        
        JPanel grid = new JPanel(new GridLayout(6, 2, 40, 15));
        grid.setBackground(Color.decode("#0F172A"));
        
        Font labelFont = new Font("Inter", Font.BOLD, 14);
        Font valFont = new Font("Inter", Font.PLAIN, 14);
        Color labelColor = Color.decode("#94A3B8");
        Color valColor = Color.WHITE;

        String[] labels = {"Name:", "Email:", "Account No:", "Balance:", "Status:", "Member Since:"};
        JLabel[] valLabels = new JLabel[6];

        for (int i=0; i<6; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(labelFont);
            lbl.setForeground(labelColor);
            grid.add(lbl);

            valLabels[i] = new JLabel("-");
            valLabels[i].setFont(valFont);
            valLabels[i].setForeground(valColor);
            grid.add(valLabels[i]);
        }

        accNameVal = valLabels[0];
        accEmailVal = valLabels[1];
        accNoVal = valLabels[2];
        accBalVal = valLabels[3];
        accStatusVal = valLabels[4];
        accDateVal = valLabels[5];

        wrapper.add(grid);
        return wrapper;
    }

    private JPanel createDepositCard() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.decode("#0F172A"));
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.decode("#0F172A"));
        
        JLabel title = new JLabel("Deposit Funds");
        title.setFont(new Font("Inter", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel lbl = new JLabel("Amount (\u20B9)");
        lbl.setFont(new Font("Inter", Font.BOLD, 12));
        lbl.setForeground(Color.decode("#94A3B8"));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setMaximumSize(new Dimension(300, 20));
        panel.add(lbl);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        JTextField amountField = new JTextField();
        amountField.putClientProperty("JTextField.placeholderText", "Amount in \u20B9");
        amountField.setPreferredSize(new Dimension(300, 40));
        amountField.setMaximumSize(new Dimension(300, 40));
        amountField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(amountField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton btn = new JButton("Deposit");
        btn.setBackground(Color.decode("#34D399"));
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Inter", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(300, 40));
        btn.setMaximumSize(new Dimension(300, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(e -> {
            try {
                BigDecimal amt = new BigDecimal(amountField.getText().trim());
                accountService.deposit(currentAccount.getId(), amt);
                amountField.setText("");
                refreshTransactions();
                JOptionPane.showMessageDialog(this, "Deposit successful!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        panel.add(btn);

        wrapper.add(panel);
        return wrapper;
    }

    private JPanel createWithdrawCard() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.decode("#0F172A"));
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.decode("#0F172A"));
        
        JLabel title = new JLabel("Withdraw Funds");
        title.setFont(new Font("Inter", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel lbl = new JLabel("Amount (\u20B9)");
        lbl.setFont(new Font("Inter", Font.BOLD, 12));
        lbl.setForeground(Color.decode("#94A3B8"));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setMaximumSize(new Dimension(300, 20));
        panel.add(lbl);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        JTextField amountField = new JTextField();
        amountField.putClientProperty("JTextField.placeholderText", "Amount in \u20B9");
        amountField.setPreferredSize(new Dimension(300, 40));
        amountField.setMaximumSize(new Dimension(300, 40));
        amountField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(amountField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton btn = new JButton("Withdraw");
        btn.setBackground(Color.decode("#FBBF24"));
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Inter", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(300, 40));
        btn.setMaximumSize(new Dimension(300, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(e -> {
            try {
                BigDecimal amt = new BigDecimal(amountField.getText().trim());
                accountService.withdraw(currentAccount.getId(), amt);
                amountField.setText("");
                refreshTransactions();
                JOptionPane.showMessageDialog(this, "Withdraw successful!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        panel.add(btn);

        wrapper.add(panel);
        return wrapper;
    }

    private JPanel createTransferCard() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.decode("#0F172A"));
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.decode("#0F172A"));
        
        JLabel title = new JLabel("Transfer Funds");
        title.setFont(new Font("Inter", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel destLbl = new JLabel("Destination Account No");
        destLbl.setFont(new Font("Inter", Font.BOLD, 12));
        destLbl.setForeground(Color.decode("#94A3B8"));
        destLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        destLbl.setMaximumSize(new Dimension(300, 20));
        panel.add(destLbl);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        JTextField destField = new JTextField();
        destField.setPreferredSize(new Dimension(300, 40));
        destField.setMaximumSize(new Dimension(300, 40));
        destField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(destField);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel amtLbl = new JLabel("Amount (\u20B9)");
        amtLbl.setFont(new Font("Inter", Font.BOLD, 12));
        amtLbl.setForeground(Color.decode("#94A3B8"));
        amtLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        amtLbl.setMaximumSize(new Dimension(300, 20));
        panel.add(amtLbl);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        JTextField amtField = new JTextField();
        amtField.setPreferredSize(new Dimension(300, 40));
        amtField.setMaximumSize(new Dimension(300, 40));
        amtField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(amtField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton btn = new JButton("Transfer");
        btn.setBackground(Color.decode("#38BDF8"));
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Inter", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(300, 40));
        btn.setMaximumSize(new Dimension(300, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(e -> {
            try {
                String toAcc = destField.getText().trim();
                BigDecimal amt = new BigDecimal(amtField.getText().trim());
                accountService.transfer(currentAccount.getId(), toAcc, amt);
                destField.setText("");
                amtField.setText("");
                refreshTransactions();
                JOptionPane.showMessageDialog(this, "Transfer successful!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        panel.add(btn);

        wrapper.add(panel);
        return wrapper;
    }

    private JPanel createHistoryCard() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.decode("#0F172A"));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JPanel topHistoryPanel = new JPanel(new BorderLayout());
        topHistoryPanel.setBackground(Color.decode("#0F172A"));
        
        JLabel title = new JLabel("Transaction History");
        title.setFont(new Font("Inter", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        topHistoryPanel.add(title, BorderLayout.WEST);
        
        JButton refreshBtn = new JButton("\u21BB Refresh"); // circular arrow icon
        refreshBtn.setBackground(Color.decode("#334155"));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFont(new Font("Inter", Font.PLAIN, 12));
        refreshBtn.setFocusPainted(false);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshBtn.addActionListener(e -> refreshTransactions());
        topHistoryPanel.add(refreshBtn, BorderLayout.EAST);
        
        panel.add(topHistoryPanel, BorderLayout.NORTH);
        
        txModel = new DefaultTableModel(new String[]{"#", "Type", "Amount (\u20B9)", "Description", "Date & Time"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        txTable = new JTable(txModel);
        txTable.setFillsViewportHeight(true);
        txTable.setRowHeight(30);
        txTable.setBackground(Color.decode("#1E293B"));
        txTable.setForeground(Color.WHITE);
        txTable.getTableHeader().setBackground(Color.decode("#334155"));
        txTable.getTableHeader().setForeground(Color.WHITE);
        txTable.getTableHeader().setFont(new Font("Inter", Font.BOLD, 12));
        txTable.setShowGrid(true);
        txTable.setGridColor(Color.decode("#334155"));
        
        JScrollPane scroll = new JScrollPane(txTable);
        scroll.setBorder(BorderFactory.createLineBorder(Color.decode("#334155")));
        scroll.getViewport().setBackground(Color.decode("#1E293B"));
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private void refreshTransactions() {
        if (currentAccount == null) return;
        try {
            currentAccount = accountDAO.findById(currentAccount.getId());
            balanceLabel.setText("\u20B9 " + currentAccount.getBalance());
            
            accNameVal.setText(currentUser.getFullName());
            accEmailVal.setText(currentUser.getEmail());
            accNoVal.setText(currentAccount.getAccountNo());
            accBalVal.setText("\u20B9 " + currentAccount.getBalance());
            accStatusVal.setText(currentUser.getStatus() != null ? currentUser.getStatus() : "ACTIVE");
            
            String dateStr = "N/A";
            if (currentUser.getCreatedAt() != null) {
                dateStr = currentUser.getCreatedAt().toString().split(" ")[0];
            }
            accDateVal.setText(dateStr);

            if (txModel != null) {
                txModel.setRowCount(0);
                List<Transaction> txs = transactionDAO.findByAccountId(currentAccount.getId());
                for (Transaction t : txs) {
                    txModel.addRow(new Object[]{t.getId(), t.getType(), t.getAmount(), t.getDescription(), t.getTxnTime()});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
