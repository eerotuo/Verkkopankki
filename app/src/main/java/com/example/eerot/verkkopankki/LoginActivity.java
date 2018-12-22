package com.example.eerot.verkkopankki;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView textView3;
    EditText editText, editText2;
    int userid;

    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        editText = (EditText) findViewById(R.id.editText);

        editText2 = (EditText) findViewById(R.id.editText2);
        editText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_GO || id == EditorInfo.IME_NULL) {
                    login();
                    return true;
                }
                return false;
            }
        });

    }

    //checks if the username and passwords are in the user list and are a match
    public boolean checkData(String name, String password){

        String username;
        String userpassword;

        userid = bank.findUser(name);

        if (userid != -11) {

            username = bank.users.get(userid).name;
            userpassword = bank.users.get(userid).password;

            System.out.println("käyttis: " + username + " salasana:" + userpassword);

            if (name.equals(username) && password.equals(userpassword)) {
                System.out.println("True");
                return true;
            }
        }

        Toast.makeText(this, "Käyttäjätietoja ei löytynyt", Toast.LENGTH_SHORT).show();

        System.out.println("False");
        return false;
    }


    //try to log into account
    public void login(){

        String pName = editText.getText().toString();
        String pPassword = editText2.getText().toString();


        if (pName.equals("admin") && pPassword.equals("admin")){
            Intent intent = new Intent(LoginActivity.this,BankActivity.class);
            startActivity(intent);
        }


        else if (checkData(pName, pPassword) == true){

            System.out.println("Login");

            Intent intent = new Intent(LoginActivity.this,MainActivity.class);

            intent.putExtra("UserID",userid);

            startActivity(intent);
        }
    }

    //try to log into account when button pressed
    public void login(View v){

        String pName = editText.getText().toString();
        String pPassword = editText2.getText().toString();

        if (pName.equals("admin") && pPassword.equals("admin")){
            Intent intent = new Intent(LoginActivity.this,BankActivity.class);
            startActivity(intent);
        }


        else if (checkData(pName, pPassword) == true){

            System.out.println("Login");

            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("UserID",userid);

            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Tarkista salasana ja käyttäjätunnus", Toast.LENGTH_SHORT).show();
        }
    }

    public void newUser(View v){

        Intent intent = new Intent(LoginActivity.this,NewUser.class);

        startActivity(intent);

    }



}
