package com.example.eerot.verkkopankki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewUser extends AppCompatActivity {

    Bank bank = Bank.getInstance();
    ;EditText editTextN1, editTextN2, editTextN3, editTextN4;

    private String name;
    private String address;
    private String city;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        editTextN1 = findViewById(R.id.editTextN1);
        editTextN2 = findViewById(R.id.editTextN2);
        editTextN3 = findViewById(R.id.editTextN3);
        editTextN4 = findViewById(R.id.editTextN4);

    }

    public void saveUser1(View v){
        name = editTextN1.getText().toString();
        address = editTextN2.getText().toString();
        city = editTextN3.getText().toString();
        password = editTextN4.getText().toString();;

        bank.Bank(name, address, city,password);
        Toast.makeText(this, "Uusi käyttäjä luotu", Toast.LENGTH_SHORT).show();

        editTextN1.setText("");
        editTextN2.setText("");
        editTextN3.setText("");
        editTextN4.setText("");


    }

}
