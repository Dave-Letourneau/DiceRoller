package com.example.david.diceroller;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Field;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    MyDBHandler handle;


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
        handle = new MyDBHandler(this, null, null, 1);
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
            int bonus = getBonus();

            int rollSum = 0;
            for(int n: vals) {
                // for each number in vals...
                rollSum += roller(n, vals[n]);
            }
            rollSum += bonus;
            ((Button)v).setText("Test1");
            // call dialogue pop-up method with rollSum value displayed.

        } else if (v.getId() == R.id.saveButton1) {
            // save button was clicked. Save the current bonuses + dice layout to database.
            // Waiting for Cole to create the dialogue.
           // DicePresets preset = new DicePresets(colesInput.getText().toString());
           // handle.addPreset(preset);
            // This above code might ACTUALLY need to go inside the dialogue method.
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
        // array to hold their values
        //commit
        int[] vals = new int[7];
        //
        for (int i = 0; i < 7; i++) {
            int disp = 0; // disp stands for displacement.
            // This step is needed because the ID's for the dice correspond to themselves, rather than
            // index positions in the array. If it becomes to inefficient to calculate this,
            // we can change their ID's to be 0 - 7 respectively.
            if (i <= 4) {
                disp = 4 + (2*i);
            } else if (i == 5) {
                disp = 20;
            } else {
                disp = 100;
            }
            String stringId = "d" + disp + "numBox";
            int textId = getResId(stringId, this, R.id.class); // No error?
            EditText diceRef=(EditText)findViewById(textId); // textId
            vals[i] = Integer.parseInt(diceRef.getText().toString());
        }
        //
        return vals;
    }

    public int getBonus() {
        // if the method used above for looping works, I'll update this method

        EditText d4bonus = (EditText) findViewById(R.id.d4Bonus);
        EditText d6bonus = (EditText) findViewById(R.id.d6Bonus);
        EditText d8bonus = (EditText) findViewById(R.id.d8Bonus);
        EditText d10bonus = (EditText) findViewById(R.id.d10Bonus);
        EditText d12bonus = (EditText) findViewById(R.id.d12Bonus);
        EditText d20bonus = (EditText) findViewById(R.id.d20Bonus);
        EditText d100bonus = (EditText) findViewById(R.id.d100Bonus);

        int d4 = Integer.parseInt(d4bonus.getText().toString());
        int d6 = Integer.parseInt(d6bonus.getText().toString());
        int d8 = Integer.parseInt(d8bonus.getText().toString());
        int d10 = Integer.parseInt(d10bonus.getText().toString());
        int d12 = Integer.parseInt(d12bonus.getText().toString());
        int d20 = Integer.parseInt(d20bonus.getText().toString());
        int d100 = Integer.parseInt(d100bonus.getText().toString());

        return (d4 + d6 + d8 + d10 + d12 + d20 + d100);
    }

    public static int getResId(String variableName, Context context, Class<?> c) {
        // a method to convert a string into a reference ID int value.
        // Found on google when looking up how to convert strings to ID's.
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
