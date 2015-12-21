package com.example.david.diceroller;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // To Cole and Faraz: The onCreate method is included by default and
        // needs to have references to your widgets based on their ID's. Follow the
        // pattern below to reference widgets by their ID.
        Button rollDice = (Button) findViewById(R.id.rollButton);
        Button saveDice = (Button) findViewById(R.id.saveButton1);
        Button loadDice = (Button) findViewById(R.id.loadButton1);
        // if you were wondering why the last two have numbers thrown in, it's because
        // there might be more than one screen where you can save and load dice.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void buttonOnClick(View v) {
        // Method to handle each button
        // Currently, the setText is just used to make sure the buttons are being clicked
        // properly.
        if (v.getId() == R.id.rollButton) {
            // the roll dice button was clicked. Change activity, or use a dialogue box?
            // either way, display results to user.
            ((Button)v).setText("Test");

        } else if (v.getId() == R.id.saveButton1) {
            // save button was clicked. Save the current bonuses + dice layout to preferences.
            ((Button)v).setText("Test2");
        } else if (v.getId() == R.id.loadButton1) {
            // load button was clicked. Load bonuses + dice layout from preferences.
            ((Button)v).setText("Test3");
        }
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
}
