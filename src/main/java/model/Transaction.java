package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    private int id;
    private int accountId;
    private String type;
    private BigDecimal amount;
    private String description;
    private Timestamp txnTime;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Timestamp getTxnTime() { return txnTime; }
    public void setTxnTime(Timestamp txnTime) { this.txnTime = txnTime; }
}
