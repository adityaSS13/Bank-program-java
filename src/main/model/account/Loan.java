package src.main.model.account;

public class Loan extends Account {

    public Loan(String id, String name, double balance) {
        super(id, name, balance);
    }

    public Loan(Loan l){
        super(l);
    }

    @Override
    public Account clone() {
        return new Loan(this);
    }

    @Override
    public void deposit(double amount) {
        super.setBalance(super.round(super.getBalance()-amount));
    }

    @Override
    public boolean withdraw(double amount) {
        if(super.getBalance()+amount>MAX_DEBT){
            throw new IllegalStateException("A withdrawal can't made as the debt exceeds $10,000");
        }
        else{
            super.setBalance(super.round(super.getBalance()+amount+amount*WITHDRAWAL_INTEREST));
            return true;
        }
    }
        
    

}
