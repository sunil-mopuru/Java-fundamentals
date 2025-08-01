/**
 * BankAccount Class - Demonstrating Encapsulation and Best Practices
 * This class shows how to properly encapsulate data and provide controlled access
 * through public methods while keeping internal state private.
 */
public class BankAccount {
    // Constants
    private static final double MINIMUM_BALANCE = 100.0;
    private static final double OVERDRAFT_FEE = 25.0;
    private static final double DAILY_WITHDRAWAL_LIMIT = 1000.0;
    
    // Private instance variables (encapsulation)
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private double dailyWithdrawalTotal;
    private String lastTransactionDate;
    
    // Static variable to track total accounts
    private static int totalAccounts = 0;
    
    /**
     * Constructor - Initializes a new bank account
     * @param accountNumber Unique account identifier
     * @param accountHolder Name of account holder
     * @param initialBalance Starting balance
     */
    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        validateAccountNumber(accountNumber);
        validateAccountHolder(accountHolder);
        validateInitialBalance(initialBalance);
        
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.dailyWithdrawalTotal = 0.0;
        this.lastTransactionDate = getCurrentDate();
        
        totalAccounts++;
        
        System.out.println("Account created successfully for " + accountHolder);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Initial Balance: $" + String.format("%.2f", initialBalance));
    }
    
    /**
     * Default constructor with minimal parameters
     */
    public BankAccount(String accountHolder, double initialBalance) {
        this(generateAccountNumber(), accountHolder, initialBalance);
    }
    
    /**
     * Copy constructor
     */
    public BankAccount(BankAccount other) {
        this(other.accountNumber, other.accountHolder, other.balance);
        this.dailyWithdrawalTotal = other.dailyWithdrawalTotal;
        this.lastTransactionDate = other.lastTransactionDate;
    }
    
    // Public methods for account operations
    
    /**
     * Deposits money into the account
     * @param amount Amount to deposit
     * @return true if successful, false otherwise
     */
    public boolean deposit(double amount) {
        if (validateAmount(amount)) {
            balance += amount;
            logTransaction("DEPOSIT", amount);
            return true;
        }
        return false;
    }
    
    /**
     * Withdraws money from the account
     * @param amount Amount to withdraw
     * @return true if successful, false otherwise
     */
    public boolean withdraw(double amount) {
        if (!validateAmount(amount)) {
            return false;
        }
        
        if (!checkDailyWithdrawalLimit(amount)) {
            System.out.println("Error: Daily withdrawal limit exceeded");
            return false;
        }
        
        if (canWithdraw(amount)) {
            balance -= amount;
            dailyWithdrawalTotal += amount;
            logTransaction("WITHDRAWAL", amount);
            return true;
        } else {
            System.out.println("Error: Insufficient funds");
            return false;
        }
    }
    
    /**
     * Transfers money to another account
     * @param recipient Account to transfer to
     * @param amount Amount to transfer
     * @return true if successful, false otherwise
     */
    public boolean transfer(BankAccount recipient, double amount) {
        if (recipient == null) {
            System.out.println("Error: Invalid recipient account");
            return false;
        }
        
        if (withdraw(amount)) {
            if (recipient.deposit(amount)) {
                logTransaction("TRANSFER TO " + recipient.getAccountNumber(), amount);
                return true;
            } else {
                // Rollback the withdrawal if deposit fails
                balance += amount;
                dailyWithdrawalTotal -= amount;
                System.out.println("Error: Transfer failed, withdrawal rolled back");
                return false;
            }
        }
        return false;
    }
    
    /**
     * Applies interest to the account
     * @param rate Interest rate (as decimal, e.g., 0.05 for 5%)
     */
    public void applyInterest(double rate) {
        if (rate > 0 && rate <= 1.0) {
            double interest = balance * rate;
            balance += interest;
            logTransaction("INTEREST", interest);
        } else {
            System.out.println("Error: Invalid interest rate");
        }
    }
    
    // Getter methods (controlled access to private data)
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getAccountHolder() {
        return accountHolder;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public double getDailyWithdrawalTotal() {
        return dailyWithdrawalTotal;
    }
    
    public String getLastTransactionDate() {
        return lastTransactionDate;
    }
    
    // Static getter
    public static int getTotalAccounts() {
        return totalAccounts;
    }
    
    /**
     * Displays account information
     */
    public void displayAccountInfo() {
        System.out.println("\n=== Account Information ===");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Current Balance: $" + String.format("%.2f", balance));
        System.out.println("Daily Withdrawal Total: $" + String.format("%.2f", dailyWithdrawalTotal));
        System.out.println("Last Transaction: " + lastTransactionDate);
        System.out.println("===========================");
    }
    
    /**
     * Resets daily withdrawal counter (typically called at midnight)
     */
    public void resetDailyWithdrawal() {
        dailyWithdrawalTotal = 0.0;
        System.out.println("Daily withdrawal counter reset");
    }
    
    // Private helper methods (implementation details)
    
    private boolean validateAmount(double amount) {
        if (amount <= 0) {
            System.out.println("Error: Amount must be positive");
            return false;
        }
        return true;
    }
    
    private void validateAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }
        if (accountNumber.length() < 8) {
            throw new IllegalArgumentException("Account number must be at least 8 characters");
        }
    }
    
    private void validateAccountHolder(String accountHolder) {
        if (accountHolder == null || accountHolder.trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be null or empty");
        }
    }
    
    private void validateInitialBalance(double initialBalance) {
        if (initialBalance < MINIMUM_BALANCE) {
            throw new IllegalArgumentException(
                "Initial balance must be at least $" + MINIMUM_BALANCE);
        }
    }
    
    private boolean canWithdraw(double amount) {
        return balance - amount >= MINIMUM_BALANCE;
    }
    
    private boolean checkDailyWithdrawalLimit(double amount) {
        return dailyWithdrawalTotal + amount <= DAILY_WITHDRAWAL_LIMIT;
    }
    
    private void logTransaction(String type, double amount) {
        lastTransactionDate = getCurrentDate();
        System.out.println(type + ": $" + String.format("%.2f", amount) + 
                          " - New balance: $" + String.format("%.2f", balance));
    }
    
    private String getCurrentDate() {
        return java.time.LocalDate.now().toString();
    }
    
    private static String generateAccountNumber() {
        return "ACC" + String.format("%06d", (int)(Math.random() * 1000000));
    }
    
    // Override toString method for better object representation
    @Override
    public String toString() {
        return "BankAccount{" +
               "accountNumber='" + accountNumber + '\'' +
               ", accountHolder='" + accountHolder + '\'' +
               ", balance=" + String.format("%.2f", balance) +
               '}';
    }
    
    // Override equals method for proper comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        BankAccount that = (BankAccount) obj;
        return accountNumber.equals(that.accountNumber);
    }
    
    // Override hashCode method (should be consistent with equals)
    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }
} 