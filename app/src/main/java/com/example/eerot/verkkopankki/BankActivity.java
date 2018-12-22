package com.example.eerot.verkkopankki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class BankActivity extends AppCompatActivity {

    Bank bank = Bank.getInstance();

    Spinner spinner;
    String choise;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

        spinner = findViewById(R.id.spinner7);
        dropdown();


    }

    public void deleteUser(View view){

        if (choise.equals("Valitse muokattava käyttäjä")){
            Toast.makeText(this, "Valitse käyttäjä", Toast.LENGTH_SHORT).show();
        }
        else {
            int id = bank.findUser(choise);
            bank.deleteUser(id);
            Toast.makeText(this, "Käyttäjä ja sen tilit tuhottu", Toast.LENGTH_SHORT).show();
        }
        dropdown();

    }

    public void editUserInfo(View view){

        if (choise.equals("Valitse muokattava käyttäjä")){
            Toast.makeText(this, "Valitse käyttäjä", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(BankActivity.this,OwnData.class);
            intent.putExtra("UserID",userID);

            startActivity(intent);
        }

    }

    public void editCards(View view){

        if (choise.equals("Valitse muokattava käyttäjä")){
            Toast.makeText(this, "Valitse käyttäjä", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(BankActivity.this,ManageCards.class);
            intent.putExtra("UserID",userID);

            startActivity(intent);
        }

    }

    public void editAccounts(View view){

        if (choise.equals("Valitse muokattava käyttäjä")){
            Toast.makeText(this, "Valitse käyttäjä", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(BankActivity.this,AddAccount.class);
            intent.putExtra("UserID",userID);
            intent.putExtra("permission",true);

            startActivity(intent);
        }

    }

    public void viewHistory(View view){

        if (choise.equals("Valitse muokattava käyttäjä")){
            Toast.makeText(this, "Valitse käyttäjä", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(BankActivity.this,History.class);

            intent.putExtra("UserID",userID);


            startActivity(intent);
        }

    }


    public void dropdown(){
        int i= 0;

        final ArrayList choises = new ArrayList();

        choises.add(0, "Valitse muokattava käyttäjä");

        while (i<(bank.users.size())){
            choises.add(bank.users.get(i).name);
            i++;
        }

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, choises);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choise = parent.getItemAtPosition(position).toString();

                if (!choise.equals("Valitse muokattava käyttäjä")){
                    userID = bank.findUser(choise);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }
}
