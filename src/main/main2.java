package src.main;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.FileNotFoundException;


import src.main.model.Bank;
import src.main.model.Transaction;
import src.main.model.account.Account;
import src.main.model.account.Chequing;
import src.main.model.account.Loan;
import src.main.model.account.Savings;

public class main2 {
    static String ACCOUNTS_FILE = "C:/Users/Aditya-Shirish-PC/Desktop/udemy java/most recent (28 Nov)/Java-Bootcamp-Resources-main/Module 2 - Object Oriented Programming/10. Inheritance/challenge/bank-management/src/main/data/accounts.txt";
    static String TRANSACTIONS_FILE = "C:/Users/Aditya-Shirish-PC/Desktop/udemy java/most recent (28 Nov)/Java-Bootcamp-Resources-main/Module 2 - Object Oriented Programming/10. Inheritance/challenge/bank-management/src/main/data/transactions.txt";
    static Bank bank;
    public static void main(String[] args) {
        bank = new Bank();
        try {
            ArrayList<Account> acc = returnAccounts();
            /*for (int i = 0; i < acc.size(); i++) {
                System.out.print(i+1+".");
                System.out.println("\t"+acc.get(i));
            }*/
            loadAccounts(acc);
            ArrayList<Transaction> t = returnTransactions();
            //Arrays.sort(t.toArray());
            runTransactions(t);
            //System.out.println();
            bank.deductTaxes();
            for (Account account : acc) {
                System.out.println("\n\t\t\t\t\t ACCOUNT\n\n\t"+account+"\n\n");
                transactionHistory(account.getId());
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Account createObject(String[] values){
        switch(values[0]){
            case "Chequing":
                return new Chequing(values[1], values[2], Double.valueOf(values[3]));
            case "Savings":
                return new Savings(values[1], values[2], Double.valueOf(values[3]));
            case "Loan":
                return new Loan(values[1], values[2], Double.valueOf(values[3]));
            default:
                return null;
        }
    }

    /*The above function "createObject" can also be written like:
    public static Account createAccount(String[] values) throws Exception{
         return (Account)Class.forName("src.main.model.account." + values[0])
               .getConstructor(String.class,String.class,double.class)
               .newInstance(values[1],values[2],Double.valueOf(values[3]));
    }*/

    public static ArrayList<Account> returnAccounts() throws FileNotFoundException{
        ArrayList<Account> acc_list = new ArrayList<Account>();
        FileInputStream fis = new FileInputStream(ACCOUNTS_FILE);
        Scanner scan = new Scanner(fis);
        while(scan.hasNextLine()){
            String s1 = scan.nextLine();
            String[] s2 = s1.split(",");
            Account a = createObject(s2);
            acc_list.add(a);
        }
        scan.close();
        return acc_list;
    }

    public static void loadAccounts(ArrayList<Account> acc_list){
        for (int i = 0; i < acc_list.size(); i++) {
            bank.addAccount(acc_list.get(i));
        }
    }

    public static ArrayList<Transaction> returnTransactions() throws FileNotFoundException{
        ArrayList<Transaction> t_list = new ArrayList<Transaction>();
        FileInputStream fis = new FileInputStream(TRANSACTIONS_FILE);
        Scanner scan = new Scanner(fis);
        while(scan.hasNextLine()){
            String s1 = scan.nextLine();
            String[] s2 = s1.split(",");
            Transaction t = new Transaction(Long.valueOf(s2[0]), Transaction.Type.valueOf(s2[1]), s2[2], Double.valueOf(s2[3]));
            t_list.add(t);
        }
        scan.close();
        Collections.sort(t_list);
        return t_list;
    }

    public static void runTransactions(ArrayList<Transaction> tr){
        for (int i = 0; i < tr.size(); i++) {
            bank.executeTransaction(tr.get(i));
        }
    }

    public static void wait(int milliseconds) {
        try {
           TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void transactionHistory(String id){
        
        Transaction[] trs = new Transaction[bank.getTransactions(id).length];
        System.out.println("\t\t\t\t   TRANSACTION HISTORY\n\t");
        trs = bank.getTransactions(id);
        Arrays.stream(trs).forEach((t) -> {
                        System.out.println("\t"+t+"\n");
                        wait(300);
        });
        
        System.out.println("\n\t\t\t\t\tAFTER TAX\n");
        System.out.println("\t" + bank.getAccount(id) +"\n\n\n\n");  
    }


}
