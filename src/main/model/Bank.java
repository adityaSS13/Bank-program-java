package src.main.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import src.main.model.Transaction.Type;
import src.main.model.account.Account;
import src.main.model.account.Chequing;
import src.main.model.account.impl.Taxable;

public class Bank {
    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;

    public Bank(){
        this.accounts = new ArrayList<Account>();
        this.transactions = new ArrayList<Transaction>();
    }

    public void addAccount(Account account){
        this.accounts.add(account.clone());
    }

    private void addTransaction(Transaction t){
        this.transactions.add(new Transaction(t));
    }

    public Transaction[] getTransactions(String accountId){
        List<Transaction> list = this.transactions.stream()
                   .filter((tran) -> tran.getId().equals(accountId))
                   .collect(Collectors.toList());
        return list.toArray(new Transaction[list.size()]);
    }

    //another way to write the above function:
    /*
    public Transaction[] getTransactions(String accountId){
        List<Transaction> list = this.transactions.stream()
                   .filter((tran) -> tran.getId().equals(accountId))
                   .collect(Collectors.toList());
        return list.toArray(new Transaction[list.size()]);
    }
    */

    public Account getAccount(String transactionId){
        return this.accounts.stream()
                  .filter((acc) -> acc.getId().equals(transactionId))
                  .findFirst()
                  .orElse(null);
    }

    private void withdrawTransaction(Transaction t){
        if(this.getAccount(t.getId()).withdraw(t.getAmount())){
            this.addTransaction(t);
        }
    }

    private void depositTransaction(Transaction t){
        this.getAccount(t.getId()).deposit(t.getAmount());
        this.addTransaction(t);
    }
  
    public void executeTransaction(Transaction t){
        Type ch = t.getType();
        switch(ch){
            case WITHDRAW:
                withdrawTransaction(t);
                break;
            case DEPOSIT:
                depositTransaction(t);
                break;
        }
    }

    private double getIncome(Taxable a){
        Transaction[] tr = getTransactions(((Chequing)a).getId());
        double income = Arrays.stream(tr)
                           .mapToDouble(t -> {
                               switch(t.getType()){
                                   case WITHDRAW: return -t.getAmount(); 
                                   case DEPOSIT: return t.getAmount(); 
                                   default: return 0;
                               }
                           })
                           .sum();
        return income;
    }

    public void deductTaxes(){
        for (int i = 0; i < accounts.size(); i++) {
            //check if an object is 'Taxable':
            if(Taxable.class.isAssignableFrom(accounts.get(i).getClass())){
                 Taxable taxable = (Taxable)accounts.get(i);
                 taxable.tax(getIncome(taxable));
            }
        }
    }
}
