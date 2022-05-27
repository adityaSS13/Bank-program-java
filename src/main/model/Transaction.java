package src.main.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Transaction implements Comparable<Transaction> {

  public enum Type{
      WITHDRAW,DEPOSIT;
  }

  private Type type;
  private long timestamp;
  private String id;
  private double amount;

  private static final int MULTIPLIER = 1000;


    public Transaction(long timestamp, Type type, String id, double amount) {
        if(id==null||id.isBlank()||amount<0){
            throw new IllegalArgumentException("invalid params!");
        }
        this.type = type;
        this.timestamp = timestamp;
        this.id = id;
        this.amount = amount;
    }

    public Transaction(Transaction t){
        this.type = t.type;
        this.timestamp = t.timestamp;
        this.id = t.id;
        this.amount = t.amount;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        if(id==null||id.isBlank()){
            throw new IllegalArgumentException("invalid id!");
        }
        this.id = id;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        if(amount<0){
            throw new IllegalArgumentException("invalid amount!");
        }
        this.amount = amount;
    }

    public String returnDate(){
        Date d = new Date(this.timestamp*MULTIPLIER);
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        return f.format(d);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj==null){
            return false;
        }
        if(!(obj instanceof Transaction)){
            return false;
        }
        Transaction t = (Transaction)obj;
        return this.type.equals(t.type) &&
               this.timestamp == t.timestamp &&
               this.id.equals(t.id) &&
               this.amount == t.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type,this.timestamp,this.id,this.amount);
    }

    @Override
    public int compareTo(Transaction o) {
        return Double.compare(this.timestamp, o.timestamp);
    }

    @Override
    public String toString() {
        return this.type + "    " +
        "\t" + "date: "+ this.returnDate() + "" +
        "\t" + this.id + "" +
        "\t$" + this.amount + "";
    }


}
