package com.example.eerot.verkkopankki;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int userID = 0;
    TextView textView;

    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.textView);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printData();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle bundle = getIntent().getExtras();

        userID = bundle.getInt("UserID");

        System.out.println("Pääohjelmasta: "+userID);
        printData();

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        printData();

        if (id == R.id.own_data) {
            Intent intent = new Intent(MainActivity.this,OwnData.class);
            intent.putExtra("UserID",userID);

            startActivity(intent);

        } else if (id == R.id.add_account) {
            Intent intent2 = new Intent(MainActivity.this,AddAccount.class);
            intent2.putExtra("UserID",userID);
            intent2.putExtra("permission",false);
            startActivity(intent2);

        } else if (id == R.id.send_money) {
            Intent intent2 = new Intent(MainActivity.this,SendMoney.class);
            intent2.putExtra("UserID",userID);
            startActivity(intent2);

        } else if (id == R.id.manage_cards) {
            Intent intent2 = new Intent(MainActivity.this,ManageCards.class);
            intent2.putExtra("UserID",userID);
            startActivity(intent2);

        } else if (id == R.id.history) {
            Intent intent2 = new Intent(MainActivity.this,History.class);
            intent2.putExtra("UserID",userID);
            startActivity(intent2);

        }else if (id == R.id.card_withdraw) {
            Intent intent2 = new Intent(MainActivity.this,CardWithdraw.class);
            intent2.putExtra("UserID",userID);
            startActivity(intent2);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void signOut(View v){
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }


    //finds user's accounts and prints them to the screen
    public void printData(){

        textView.setText("Käytössä olevat tilisi:\n");

        for ( int i = 0; i< bank.users.get(userID).accounts.size(); i++){

            String account= bank.users.get(userID).accounts.get(i).account_number;
            double value = bank.users.get(userID).accounts.get(i).value;

            textView.append("TIli: "+account + "  Arvo:"+value+"€\n");
        }

    }
}
