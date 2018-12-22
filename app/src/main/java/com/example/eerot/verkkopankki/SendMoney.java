package com.example.eerot.verkkopankki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SendMoney extends AppCompatActivity {

    Bank bank = Bank.getInstance();

    int userID;
    Spinner spinner3;
    EditText editText9,editText10;

    String choise, accName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        spinner3 = findViewById(R.id.spinner3);
        editText9 = findViewById(R.id.editText9);
        editText10 = findViewById(R.id.editText10);

        Bundle bundle = getIntent().getExtras();

        userID = bundle.getInt("UserID");

        dropdown();
    }

    //lists all the accounts that user has
    public void dropdown(){
        int i= 0;

        final ArrayList choises = new ArrayList();

        choises.add(0, "Miltä tililtä rahaa siirretään?");

        while (i<(bank.users.get(userID).accounts.size())){
            choises.add(bank.users.get(userID).accounts.get(i).account_number);
            i++;
        }

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, choises);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choise = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //returns the index of the selected account
    public boolean findAccount(String accName){


        for ( int j= 0; j < bank.users.size(); j++){

            for (int i= 0; i < bank.users.get(j).accounts.size(); i++){
                if (bank.users.get(j).accounts.get(i).account_number.equals(accName)){
                    return true;
                }
            }

        }

        return false;
    }

    //takes money from the account
    public void removeMoney(int money){

        for (int i= 0; i < bank.users.get(userID).accounts.size(); i++){
            if (bank.users.get(userID).accounts.get(i).account_number.equals(choise)){
                bank.users.get(userID).accounts.get(i).removeMoney(money);
            }
        }

    }

    //adds money to the account
    public void addMoney(String account, int money){

        for ( int j= 0; j < bank.users.size(); j++){

            for (int i= 0; i < bank.users.get(j).accounts.size(); i++){
                if (bank.users.get(j).accounts.get(i).account_number.equals(accName)){
                    bank.users.get(j).accounts.get(i).addMoney(money);
                }
            }

        }

    }


    //checks if there is enough money in the account (money+credit limit)
    public boolean checkIfAllowPayment(int moneyamount){

        for (int i= 0; i < bank.users.get(userID).accounts.size(); i++){
            if (bank.users.get(userID).accounts.get(i).account_number.equals(choise)){
                if (moneyamount <= bank.users.get(userID).accounts.get(i).getWithrawlimit()){
                    return true;
                }
            }
        }

        return false;
    }

    public void sendMoney(View v){

        int money = Integer.parseInt(editText9.getText().toString());
        accName = editText10.getText().toString();

        if (editText10.getText() == null){
            Toast.makeText(this, "Valitse vastaanottaja",Toast.LENGTH_SHORT).show();
        }

        else if (findAccount(accName) == true){

            if (checkIfAllowPayment(money)== true) {
                removeMoney(money);
                addMoney(accName, money);
                //transfer.addMoneyTransfer(choise,money,accName);
                bank.users.get(userID).transferLog.addMoneyTransfer(choise,money,accName);
                Toast.makeText(this, "Tilisiirto tehty oman pankin sisällä", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Tilillä liian vähän rahaa", Toast.LENGTH_SHORT).show();
            }
        }

        else if (choise.equals("Miltä tililtä rahaa siirretään?")){
            Toast.makeText(this, "Valitse tili",Toast.LENGTH_SHORT).show();
        }

        else {

            if (checkIfAllowPayment(money)== true) {
                removeMoney(money);
                //transfer.addMoneyTransfer(choise,money,"tuntematon tili");
                bank.users.get(userID).transferLog.addMoneyTransfer(choise,money,"tuntematon tili");
                Toast.makeText(this, "Tilisiirto tehty toiseen pankkiin.", Toast.LENGTH_SHORT).show();
            }

            else{
                Toast.makeText(this, "Tilillä liian vähän rahaa", Toast.LENGTH_SHORT).show();
            }
        }


    }
}
