package com.example.eerot.verkkopankki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class History extends AppCompatActivity {

    //TransferLog transfer = TransferLog.getInstance();
    Bank bank = Bank.getInstance();
    TextView textView;

    int userID= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        textView = findViewById(R.id.textView18);

        Bundle bundle = getIntent().getExtras();

        userID = bundle.getInt("UserID");

        setTextView();
    }

    //Finds transactions and prints them to screen
    public void setTextView() {
        int i=0;

        while (i<bank.users.get(userID).transferLog.loglist.size()){

            textView.append(bank.users.get(userID).transferLog.loglist.get(i).toString());
            i++;
        }
    }
}
