package src.main.model.account;

public class Savings extends Account {


    public Savings(String id, String name, double balance) {
        super(id, name, balance);
    }

    public Savings(Savings s){
        super(s);
    }

    @Override
    public Account clone() {
        return new Savings(this);
    }

    @Override
    public void deposit(double amount) {
        super.setBalance(super.round(super.getBalance()+amount));
    }

    @Override
    public boolean withdraw(double amount) {
        /*if(amount<super.getBalance()){
            super.setBalance(super.round(super.getBalance()-amount-WITHDRAWAL_FEE));
            return true;
        }
        else{
            System.out.println("The amount exceeds the one in savings account");
            return false;
        } */
        super.setBalance(super.round(super.getBalance()-amount-WITHDRAWAL_FEE));
        return true;
        
    }

}
