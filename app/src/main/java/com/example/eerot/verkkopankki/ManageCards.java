package com.example.eerot.verkkopankki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class ManageCards extends AppCompatActivity {

    Switch spin;
    EditText editText11, editText12;
    Spinner spinner4;

    Bank bank = Bank.getInstance();


    int userID;
    String choise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_cards);

        Bundle bundle = getIntent().getExtras();

        userID = bundle.getInt("UserID");

        spin = findViewById(R.id.switch1);
        editText11 = findViewById(R.id.editText11);
        editText12 = findViewById(R.id.editText12);
        spinner4 = findViewById(R.id.spinner4);

        dropdown();

    }

    public void dropdown(){
        int i= 0;

        final ArrayList choises = new ArrayList();

        choises.add(0, "Luo uusi kortti tilille/muokkaa vanhaa:");

        while (i<(bank.users.get(userID).accounts.size())){
            choises.add(bank.users.get(userID).accounts.get(i).account_number);
            i++;
        }

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, choises);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner4.setAdapter(dataAdapter);

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choise = parent.getItemAtPosition(position).toString();

                if (!choise.equals("Luo uusi kortti tilille/muokkaa vanhaa:")) {
                    editText11.setText("");
                    editText12.setText("");

                    showInfo();

                    int idd = findAccountID(choise);
                    if (bank.users.get(userID).accounts.get(idd).bc != null) {
                        setInfo();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //returns the index of the right account
    public int findAccountID(String choise){

        int i= 0;
        int error = -11;

        for (i = 0; i < bank.users.get(userID).accounts.size(); i++){
            if (bank.users.get(userID).accounts.get(i).account_number.equals(choise)){
                return i;
            }
        }
        return error;

    }

    //finds all the info of existing account. sets them to textView
    public void setInfo(){

        if (!choise.equals("Luo uusi kortti tilille/muokkaa vanhaa:")){
            int id= findAccountID(choise);

            String card = bank.users.get(userID).accounts.get(id).bc.getKortNumber();
            int limit = bank.users.get(userID).accounts.get(id).bc.getWithdrawLimit();
            boolean allowpayment = bank.users.get(userID).accounts.get(id).bc.allowpayments;

            editText11.setText(card);
            editText12.setText(Integer.toString(limit));
        }

    }


    //adds card to the account
    public void addCard(View v){

        if ( !choise.equals("Luo uusi kortti tilille/muokkaa vanhaa:")){
            int id= findAccountID(choise);

            String number = editText11.getText().toString();
            int limit = Integer.parseInt(editText12.getText().toString());
            boolean allowpayment = spin.isChecked();

            System.out.println("numb: "+number+"Limit: "+limit+"bool: "+allowpayment);

            bank.users.get(userID).accounts.get(id).addNewCard(number,limit,allowpayment);
            Toast.makeText(this, "Kortin tiedot päivitetty/luotu",Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(this, "Kortin päivitys/luonti epäonistui",Toast.LENGTH_SHORT).show();

        }

    }

    public void deleteCard(View v){

        if ( !choise.equals("Luo uusi kortti tilille/muokkaa vanhaa:")){
            int id= findAccountID(choise);

            bank.users.get(userID).accounts.get(id).deleteCard();
            Toast.makeText(this, "Kortti poistettu",Toast.LENGTH_SHORT).show();

            editText11.setText("");
            editText12.setText("");
        }
    }

    public void showInfo(){
        if ( !choise.equals("Luo uusi kortti tilille/muokkaa vanhaa:")){
            int id= findAccountID(choise);

            if (bank.users.get(userID).accounts.get(id).bc == null){
                Toast.makeText(this, "Tilillä ei ole korttia",Toast.LENGTH_SHORT).show();
            }
        }

    }


}
