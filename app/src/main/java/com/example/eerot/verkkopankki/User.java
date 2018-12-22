package com.example.eerot.verkkopankki;

import java.util.ArrayList;

public class User {

    String name = null;
    String address = null;
    String city = null;
    String password = null;
    ArrayList<Account> accounts = new ArrayList<Account>();
    TransferLog transferLog = new TransferLog();

    //creates new user info
    public User(String n, String a, String c, String p){
        name = n;
        address = a;
        city = c;
        password = p;
    }

    //Updates the user info
    public void setUser(String n, String a, String c){
        name = n;
        address = a;
        city = c;
    }

    public String getName(){
        return name;
    }


    //Add normal account to user
    public void addAccount(String tilinro, Double summa) {
        Account account = new normalAccount(tilinro, summa);
        accounts.add(account);

    }

    //Add credit account to user
    public void addCreditAccount(String tilinro, Double summa, Double luottoraja) {
        Account account2 = new creditAccount(tilinro, summa, luottoraja);
        accounts.add(account2);

    }


    public void deleteAccount(String tilinro) {

        Account poistettava = null;

        for (Account account : accounts) {
            if (account.getNumber().equals(tilinro)) {
                poistettava = account;
            }
        }

        accounts.remove(poistettava);
        System.out.println("Tili poistettu.");

    }

}

