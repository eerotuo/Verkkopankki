package com.example.eerot.verkkopankki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OwnData extends AppCompatActivity {

    Bank bank = Bank.getInstance();
    int userID;

    private String name;
    private String address;
    private String city;

    EditText editText3, editText4, editText5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_data);

        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);

        Bundle bundle = getIntent().getExtras();

        userID = bundle.getInt("UserID");

        getInfo();
    }

    //finds current info of user
    public void getInfo(){

        name = bank.users.get(userID).name;
        address = bank.users.get(userID).address;
        city = bank.users.get(userID).city;

        editText3.setText(name);
        editText4.setText(address);
        editText5.setText(city);
    }

    //updates the information of user
    public void saveInfo(View v){
        name = editText3.getText().toString();
        address = editText4.getText().toString();
        city = editText5.getText().toString();

        bank.setUserInfo(userID,name, address, city);
        Toast.makeText(this, "Tiedot päivitetty", Toast.LENGTH_SHORT).show();

        getInfo();
    }
}
