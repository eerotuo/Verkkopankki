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

public class CardWithdraw extends AppCompatActivity {

    Bank bank = Bank.getInstance();
    TransferLog transfer = TransferLog.getInstance();

    int userID;
    Spinner spinner;
    EditText editText;

    String choise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_withdraw);

        Bundle bundle = getIntent().getExtras();
        spinner = findViewById(R.id.spinner5);
        editText = findViewById(R.id.editText13);

        userID = bundle.getInt("UserID");
        dropdown();
    }

    //lists all the cards that are created
    public void dropdown(){
        int i= 0;

        final ArrayList choises = new ArrayList();

        choises.add(0, "Miltä kortilta rahaa nostetaan?");

        while (i<(bank.users.get(userID).accounts.size())){

            if (bank.users.get(userID).accounts.get(i).bc != null){
                choises.add(bank.users.get(userID).accounts.get(i).bc.cardNumber);
            }
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //goes through all the accounts and returns the index of the right account
    public int findAccountID(String choise){

        int i= 0;
        int error = -11;

        for (i = 0; i < bank.users.get(userID).accounts.size(); i++){
            if (bank.users.get(userID).accounts.get(i).bc.cardNumber.equals(choise)){
                return i;
            }
        }
        return error;

    }

    //takes money from the account if it is possible
    public void withdrawMoney(View v){

        if (!choise.equals("Miltä kortilta rahaa nostetaan?")){

            int accID = findAccountID(choise);
            int amount = Integer.parseInt(editText.getText().toString());


            if (bank.users.get(userID).accounts.get(accID).bc.allowpayments == false){
                Toast.makeText(this, "Korttimaksut estetty",Toast.LENGTH_SHORT).show();
            }

            else if (bank.users.get(userID).accounts.get(accID).bc.withdrawLimit<amount){
                Toast.makeText(this, "Nostoraja ylittynyt",Toast.LENGTH_SHORT).show();
            }

            else if (bank.users.get(userID).accounts.get(accID).getWithrawlimit()<amount){
                Toast.makeText(this, "Tililtä ei voida nostaa noin paljoa rahaa",Toast.LENGTH_SHORT).show();
            }
            else {
                bank.users.get(userID).accounts.get(accID).removeMoney(amount);
                //transfer.cardsTransfers(choise,amount);

                bank.users.get(userID).transferLog.cardsTransfers(choise,amount);
                Toast.makeText(this, "Tililtä nostettiin"+amount+" €",Toast.LENGTH_SHORT).show();
            }
        }

        else {
            Toast.makeText(this, "Valitse kortti ensin",Toast.LENGTH_SHORT).show();
        }
    }


}
