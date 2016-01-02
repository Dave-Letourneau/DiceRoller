package com.example.david.diceroller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    // this holds the database for saving presets
    MyDBHandler handle;
    // TO_DO: ArrayList global variable.

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
            int[] bonus = getBonus();

            int rollSum = 0;
            for(int n: vals) {
                // for each number in vals...
                rollSum += roller(n, vals[n]);
            }
            for (int n: bonus) {
                rollSum += bonus[n];
            }

            ((Button)v).setText("Test1");
            // call dialogue pop-up method with rollSum value displayed.

        } else if (v.getId() == R.id.saveButton1) {
            // save button was clicked. Save the current bonuses + dice layout to database.
            // We need the arrays so we can pass these values to the preset objects.
            int[] vals = getDiceNumbers();
            int[] bonus = getBonus();

            saveDialogBox(vals, bonus);
            ((Button)v).setText("Test2");
        } else if (v.getId() == R.id.loadButton1) {
            // load button was clicked. Load bonuses + dice layout from preferences.
            loadDialogBox();
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
            // checking for nulls
            if (diceRef.getText().toString().equals("")) {
                vals[i] = 0;
            } else {
                vals[i] = Integer.parseInt(diceRef.getText().toString());
            }
        }
        //
        return vals;
    }

    public int[] getBonus() {
        // if the method used above for looping works, I'll update this method

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
            String stringId = "d" + disp + "Bonus";
            int textId = getResId(stringId, this, R.id.class); // No error?
            EditText diceRef=(EditText)findViewById(textId); // textId
            // checking for nulls
            if (diceRef.getText().toString().equals("")) {
                vals[i] = 0;
            } else {
                vals[i] = Integer.parseInt(diceRef.getText().toString());
            }
        }
        //
        return vals;
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

    public void saveDialogBox(int[] numDice, int[] bonus){
        AlertDialog.Builder dialogBuilder;
        //variables
        dialogBuilder = new AlertDialog.Builder(this);

        final EditText txtInput = new EditText(this);
        //this gets the input from the user

        // Handler to record preset into database
        DicePresets preset = new DicePresets(txtInput.getText().toString());
        preset.set_d4Num(numDice[0]);
        preset.set_d4Bonus(bonus[0]);

        preset.set_d6Num(numDice[1]);
        preset.set_d6Bonus(bonus[1]);

        preset.set_d8Num(numDice[2]);
        preset.set_d8Bonus(bonus[2]);

        preset.set_d10Num(numDice[3]);
        preset.set_d10Bonus(bonus[3]);

        preset.set_d12Num(numDice[4]);
        preset.set_d12Bonus(bonus[4]);

        preset.set_d20Num(numDice[5]);
        preset.set_d20Bonus(bonus[5]);

        preset.set_d100Num(numDice[6]);
        preset.set_d100Bonus(bonus[6]);
        handle.addPreset(preset);
        //

        //process
        dialogBuilder.setTitle("Save Name");
        dialogBuilder.setMessage("What do you want to name your save?");
        dialogBuilder.setView(txtInput);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            //when user clicks okay
            public void onClick(DialogInterface dialog, int which) {
                String strName = "default";
                strName = txtInput.getText().toString();
                // TO_DO: Contains check
                // TO_DO: add to arrayList strName;
                Toast.makeText(getApplicationContext(), "Your save has been named.", Toast.LENGTH_SHORT).show();
            }
        });
        // when user clicks cancel
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(),"Your save has not been named.", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        //output
        AlertDialog saveDialog = dialogBuilder.create();
        saveDialog.show();


    }

    //adds basic functionality to the load button
    public void loadDialogBox(){
        //variables
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(this);
        final String[] loadNamesList = {"Save1","Save2","Save3","Save4","Save5","Save6","Save7","Save8","Save9","Save10"};
        // final String[] dcl;
        // dcl = (Parameter);

        //process
        dialogBuilder.setTitle("Please select your save");
        dialogBuilder.setSingleChoiceItems(loadNamesList, -1, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                String loadName = loadNamesList[which];
                // Load values here
                // loadPreset(loadName);
                Toast.makeText(getApplicationContext(),"You have picked a saved roll.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        //output
        AlertDialog saveDialog = dialogBuilder.create();
        saveDialog.show();
    }

}
