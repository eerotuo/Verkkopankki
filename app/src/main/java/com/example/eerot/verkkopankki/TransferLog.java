package com.example.eerot.verkkopankki;

import java.util.ArrayList;

public class TransferLog {

    private static TransferLog log = null;
    final ArrayList loglist = new ArrayList();

    public static TransferLog getInstance(){
        if (log == null){
            log = new TransferLog();

            System.out.println("bank luoto");
            return log;
        }
        return log;
    }
    //Adds money transfer from accounts to list
    public void addMoneyTransfer(String from, double amount, String to){

        loglist.add("Tilisiirto:\nMistä: "+from+" Mihin: "+to+" Summa: "+amount+"\n\n");
        System.out.println("Mistä: "+from+"Mihin: "+to+"Summa: "+amount);
    }


    //Adds money transfer from cards to list
    public void cardsTransfers(String card, double amount){

        loglist.add("Korttinosto:\nKortti: "+card+" Nostosumma: "+amount+"\n\n");
        System.out.println("Kortti: "+card+"Nostosumma: "+amount);
    }




}
