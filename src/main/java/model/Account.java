package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Account {
    private int id;
    private int userId;
    private String accountNo;
    private BigDecimal balance;
    private Timestamp openedAt;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getAccountNo() { return accountNo; }
    public void setAccountNo(String accountNo) { this.accountNo = accountNo; }
    
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    
    public Timestamp getOpenedAt() { return openedAt; }
    public void setOpenedAt(Timestamp openedAt) { this.openedAt = openedAt; }
}
