package com.example.david.diceroller;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;


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
            int[] vals = getDiceNumbers();

            int rollSum = 0;
            for(int n: vals) {
                // for each number in vals...
                rollSum += roller(n, vals[n]);
            }
            // call dialogue pop-up method with results from Roller method

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

    public int roller(int dice, int num) {
        Random rand = new Random();
        int displacement = 0; // number used to convert index dice into an actual dice
        if (dice <= 4) {
            displacement = 4 + (2*dice);
        } else if (dice == 5) {
            displacement = 20;
        } else {
            displacement = 100;
        }

        int total = 0;
        for (int i = 0; i < num; i++) {
            total += rand.nextInt(displacement) + 1;
        }
        return total;
    }


    public int[] getDiceNumbers() {

        // create EditTexts corresponding to the blanks on the app design
        // the following process of creating EditText and intitializing the array values is
        // not very effective. Can we change it to something more flexible?
        EditText d4num = (EditText) findViewById(R.id.d4numBox);
        EditText d6num = (EditText) findViewById(R.id.d6numBox);
        EditText d8num = (EditText) findViewById(R.id.d8numBox);
        EditText d10num = (EditText) findViewById(R.id.d10numBox);
        EditText d12num = (EditText) findViewById(R.id.d12numBox);
        EditText d20num = (EditText) findViewById(R.id.d20numBox);
        EditText d100num = (EditText) findViewById(R.id.d100numBox);
        // array to hold their values
        int[] vals = new int[7];
        vals[0] = Integer.parseInt(d4num.getText().toString());
        vals[1] = Integer.parseInt(d6num.getText().toString());
        vals[2] = Integer.parseInt(d8num.getText().toString());
        vals[3] = Integer.parseInt(d10num.getText().toString());
        vals[4] = Integer.parseInt(d12num.getText().toString());
        vals[5] = Integer.parseInt(d20num.getText().toString());
        vals[6] = Integer.parseInt(d100num.getText().toString());
        return vals;
    }
}
