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

public class AddAccount extends AppCompatActivity {


    EditText editText6, editText7, editText8;
    Spinner spinner, spinner2;

    String accountnumber, choise, choise2;
    int userID= 0;
    int accountid = 0;
    Double  value, credit;
    Boolean permission;

    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        editText6 = findViewById(R.id.editText6);
        editText7 = findViewById(R.id.editText7);
        editText8 = findViewById(R.id.editText8);
        spinner =findViewById(R.id.spinner);
        spinner2 =findViewById(R.id.spinner2);

        Bundle bundle = getIntent().getExtras();

        userID = bundle.getInt("UserID");
        permission = bundle.getBoolean("permission");

        System.out.println("Testi boolean:"+permission);

        dropdown();

    }


    //creates new account or updates the information of existing account
    public void saveAccount(View v){

        accountnumber = editText6.getText().toString();
        value = Double.valueOf(editText7.getText().toString());

        //creates credit account
        if (choise2.equals("Luottotili")){

            credit = Double.valueOf(editText8.getText().toString());

            bank.users.get(userID).addCreditAccount(accountnumber, value, credit);
            Toast.makeText(this, "Luottotili luotu",Toast.LENGTH_SHORT).show();
            editText6.setText("");
            editText7.setText("");
            editText8.setText("");
        }

        //creates new normal account
        else if (choise2.equals("Tavallinen tili")){

            bank.users.get(userID).addAccount(accountnumber, value);
            Toast.makeText(this, "Tavallinen tili luotu",Toast.LENGTH_SHORT).show();
            editText6.setText("");
            editText7.setText("");
            editText8.setText("");
        }

        //changes the information of existing account
        else if (choise.equals("Muokkaa olemassa olevaa tiliä") && !choise2.equals("Valitse muokattava tili:")){
            int i= 0;

            accountnumber = editText6.getText().toString();
            value = Double.valueOf(editText7.getText().toString());

            if ((bank.users.get(userID).accounts.get(accountid).credit_value) != null) {

                if (editText8.getText().toString().equals("")){
                    Toast.makeText(this,"Syötä luottoraja",Toast.LENGTH_SHORT).show();
                }
                else{
                    credit = Double.valueOf(editText8.getText().toString());
                    bank.users.get(userID).accounts.get(accountid).updateAccountInfoC(accountnumber,value,credit);
                    System.out.println("Credit");
                }

            }

            else {
                bank.users.get(userID).accounts.get(accountid).updateAccountInfo(accountnumber,value);
                bank.users.get(userID);
                System.out.println("Normal");
            }
            Toast.makeText(this,"Tiedot päivitetty",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Valitse tili",Toast.LENGTH_SHORT).show();
        }

    }


    public void dropdown(){

        final ArrayList choises = new ArrayList();


        choises.add(0, "Mitä haluat tehdä?");
        choises.add("Luo uusi tili");
        choises.add("Muokkaa olemassa olevaa tiliä");



        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, choises);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choise = parent.getItemAtPosition(position).toString();
                dropdown2();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //creates choices to spinner2 after choice has been selected from the first spinner
    public void dropdown2(){

        //If user wants to create new account
        if ( choise.equals("Luo uusi tili")) {

            final ArrayList choises2 = new ArrayList();


            choises2.add(0, "Minkä tilin haluat luoda?");
            choises2.add("Tavallinen tili");
            choises2.add("Luottotili");

            ArrayAdapter<String> dataAdapter;
            dataAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, choises2);
            dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner2.setAdapter(dataAdapter);
        }

        //If user wants to change information of account
        if ( choise.equals("Muokkaa olemassa olevaa tiliä")) {
            ArrayList choises3 = new ArrayList();
            choises3.add(0, "Valitse muokattava tili:");

            int i= 0;
            while (i<(bank.users.get(userID).accounts.size())){
                choises3.add(bank.users.get(userID).accounts.get(i).account_number);
                i++;
            }

            ArrayAdapter<String> dataAdapter;
            dataAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, choises3);
            dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner2.setAdapter(dataAdapter);
        }

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choise2 = parent.getItemAtPosition(position).toString();

                //gets the values of selected account and sets them to textviews
                if (choise.equals("Muokkaa olemassa olevaa tiliä") && !choise2.equals("Valitse muokattava tili:")){
                    int i= 0;

                    editText6.setText("");
                    editText7.setText("");
                    editText8.setText("");


                    while (i<(bank.users.get(userID).accounts.size())){

                        if (bank.users.get(userID).accounts.get(i).account_number.equals(choise2)){
                            accountid = i;
                        }
                        i++;
                    }
                    accountnumber = bank.users.get(userID).accounts.get(accountid).account_number;
                    value = bank.users.get(userID).accounts.get(accountid).value;

                    if ((bank.users.get(userID).accounts.get(accountid).credit_value) != null) {
                        credit = bank.users.get(userID).accounts.get(accountid).credit_value;
                        editText8.setText(String.valueOf(credit));
                    }

                    editText6.setText(accountnumber);
                    editText7.setText(String.valueOf(value));



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void deleteAccount(View v){
        int i= 0;

        if (permission == false){
            Toast.makeText(this, "Vain pankilla oikeus tuhota tili",Toast.LENGTH_SHORT).show();
        }
        else if (choise== null | choise2 == null){
            Toast.makeText(this, "Valitse toiminto valikosta",Toast.LENGTH_SHORT).show();
        }
        else if (permission && !choise2.equals("Valitse muokattava tili:")){
            while ( i<(bank.users.get(userID).accounts.size())){

                if (bank.users.get(userID).accounts.get(i).account_number.equals(choise2)){
                    accountid = i;
                }
                i++;
            }

            bank.users.get(userID).accounts.remove(accountid);
            dropdown2();
            Toast.makeText(this, "Tili poistettu",Toast.LENGTH_SHORT).show();
            editText6.setText("");
            editText7.setText("");
            editText8.setText("");

        }
        else {
            Toast.makeText(this, "Valitse poistetava tili",Toast.LENGTH_SHORT).show();
        }
    }

}
