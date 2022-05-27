package src.main.model.account;

import src.main.model.account.impl.Taxable;

public class Chequing extends Account implements Taxable{

    private static final int TAXABLE = 3000;
    private static final double TAX = 0.15;

    public Chequing(String id, String name, double balance) {
        super(id, name, balance);
    } 

    public Chequing(Chequing c){
        super(c);
    }

    @Override
    public Account clone() {
        return new Chequing(this);
    }

    @Override
    public void deposit(double amount) {
           super.setBalance(super.round(super.getBalance()+amount));
    }

    @Override
    public boolean withdraw(double amount) {
        if(amount<super.getBalance()){
            super.setBalance(super.round(super.getBalance()-amount));
            return true;
        }
        else if(amount - super.getBalance() < OVERDRAFT_LIMIT){
            super.setBalance(super.round(super.getBalance()-amount-OVERDRAFT_FEE));
            return true;
        }
        else if(amount - super.getBalance() > OVERDRAFT_LIMIT){
            //System.out.println("The overdraft limit is $200");
            return false;
        }
        return true;
    }

    @Override
    public void tax(double income) {
       double tax = Math.max(0, income-TAXABLE)*TAX;
       super.setBalance(super.round(super.getBalance()-tax));
    }

}
