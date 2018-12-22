package com.example.eerot.verkkopankki;

import java.util.ArrayList;

public class Bank {


    ArrayList<User> users = new ArrayList<User>();

    private static Bank bk = null;

    public static Bank getInstance(){
        if (bk == null){
            bk = new Bank();

            System.out.println("bank luoto");
            return bk;
        }

        return bk;
    }


    //Creates one user, some accounts and cards when you run the program for the first time
    public Bank(){
        users.add(new User("eero", "Linnunrata","Lappeenranta", "eero"));
        System.out.println("Käyttäjä lisätty");
        users.get(0).addAccount("T12345",500.0);
        users.get(0).addCreditAccount("L12345",500.0,400.0);

        users.get(0).accounts.get(0).addNewCard("CT111",300,true);
        users.get(0).accounts.get(1).addNewCard("CL222",300,true);
    }

    //Creates new user and adds it to the list
    public void Bank(String name, String addr, String city, String pswrd){
        users.add(new User(name, addr, city, pswrd));
        System.out.println("Käyttäjä lisätty");
    }


    //Updates the user info
    public void setUserInfo(int i,String n, String a, String c){
        users.get(i).setUser(n,a,c);

    }

    public void deleteUser(int index){
        users.remove(index);

    }

    public int findUser(String name){
        int i = 0;

        for (User user : users) {

            System.out.println(user.getName());
            System.out.println(name);

            if (user.getName().equals(name)){
                return i;
            }
            i++;
        }

        return -11;
    }


}




