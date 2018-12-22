package com.example.eerot.verkkopankki;

import java.util.ArrayList;

public abstract  class Account {
    protected String account_number = null;
    protected Double value;
    protected Double credit_value;
    bankCard bc = null;


    public Account(String t, Double s){

        account_number = t;
        value = s;

        System.out.println("Tili luotu.");
    }

    //Saves updated normal account info
    protected void updateAccountInfo(String nbr, Double val){
        this.account_number = nbr;
        this.value = val;
        System.out.println("11111");
    }

    //Saves new credit account info
    protected void updateAccountInfoC(String nbr, Double val, Double credi){
        this.account_number = nbr;
        this.value = val;
        this.credit_value = credi;
        System.out.println("11111222");
    }

    //returns the value that user can take from the account
    public double getWithrawlimit(){

        if (credit_value == null){
            return value;
        }
        double sum = value + credit_value;
        return sum;
    }


    public void addNewCard(String cardNumber, int withdwawLimit, boolean allowpayments) {
        if (bc == null) {
            bc = new bankCard(cardNumber, withdwawLimit, allowpayments);
            System.out.println("11numb: "+cardNumber+"Limit: "+withdwawLimit+"bool: "+allowpayments);
        }
        else {
            bc.setCardInfo(cardNumber, withdwawLimit, allowpayments);
            System.out.println("22numb: "+cardNumber+"Limit: "+withdwawLimit+"bool: "+allowpayments);
        }
    }

    public void deleteCard() {
       bc = null;
    }



    protected String getNumber() {
        return account_number;
    }


    protected void addMoney(int uusi){
        value = value + uusi;
    }

    protected void removeMoney(int uusi){

        if (value >= uusi) {
            value = value - uusi;
        }
        else {
            System.out.println("Liian vähän rahaa");
        }
    }

    //Returns the value of account
    protected Double getValue() {

        return value;
    }

}

class bankCard{
    protected String cardNumber;
    protected int withdrawLimit;
    protected boolean allowpayments;


    public bankCard(String cardNumber, int withdwawLimit, boolean allowpayments) {
        this.cardNumber = cardNumber;
        this.withdrawLimit = withdwawLimit;
        this.allowpayments = allowpayments;
        System.out.println("Luokasta: numb: "+cardNumber+"Limit: "+withdwawLimit+"bool: "+allowpayments);
    }


    public void setCardInfo(String cardNumber, int withdwawLimit, boolean allowpayments) {
        this.cardNumber = cardNumber;
        this.withdrawLimit = withdwawLimit;
        this.allowpayments = allowpayments;
        System.out.println("44numb: "+cardNumber+"Limit: "+withdwawLimit+"bool: "+allowpayments);
    }

    public int getWithdrawLimit() {
        return withdrawLimit;
    }

    public String getKortNumber() {
        return cardNumber;
    }
}

class normalAccount extends Account{

    public normalAccount(String t, Double s){
        super(t,s);
    }
}

class creditAccount extends Account {


    public creditAccount(String t, Double s, Double c) {
        super(t, s);
        this.credit_value= c;
    }


    protected void updateAccountInfoC(String nbr, Double val, Double credi){
        this.account_number = nbr;
        this.value = val;
        this.credit_value = credi;
        System.out.println("11111333");
    }

    @Override
    protected void removeMoney(int uusi){

        if (value + credit_value > uusi) {
            value = value - uusi;
        }
        else {
            System.out.println("Liian vähän rahaa");
        }
    }

}

