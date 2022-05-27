package src.main.model.account;

import java.text.DecimalFormat;

public abstract class Account {
    private String id;
    private String name;
    private double balance;

    protected static final double OVERDRAFT_FEE = 5.50; //for chequing
    protected static final double OVERDRAFT_LIMIT = 200; //for chequing
    protected static final double WITHDRAWAL_FEE = 5.00; //for savings
    protected static final double WITHDRAWAL_INTEREST = 0.02; //for loan
    protected static final double MAX_DEBT = 10000; //for loan

    public Account(String id, String name, double balance) {
        if(id.isBlank() || id == null || name.isBlank() || name == null){
            throw new IllegalArgumentException("The parameters are incorrect!");
        }
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public Account(Account a){
        this.id = a.id;
        this.name = a.name;
        this.balance = a.balance;
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        if(id==null || id.isBlank()){
            throw new IllegalArgumentException("invalid id!");
        }
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if(name==null || name.isBlank()){
            throw new IllegalArgumentException("invalid name!");
        }
        this.name = name;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public abstract void deposit(double amount);
    public abstract boolean withdraw(double amount);
    public abstract Account clone();

    protected double round(double amount) {
        DecimalFormat formatter = new DecimalFormat("#.##");
        return Double.parseDouble(formatter.format(amount));
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "    " +
              "\t" + id + "" +
              "\t" + name + "" +
              "\t$" + balance + "";
    }



}
