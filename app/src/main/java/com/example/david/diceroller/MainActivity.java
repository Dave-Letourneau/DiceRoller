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
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    // this holds the database for saving presets
    MyDBHandler handle;
    // TO_DO: ArrayList global variable.
    public ArrayList<String> LOAD_LIST  = new ArrayList<String>();


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

        // Arrays needed to be declared final.
        final int[] ar1 = numDice;
        final int[] ar2 = bonus;

        //process
        dialogBuilder.setTitle("Save Name");
        dialogBuilder.setMessage("What do you want to name your save?");
        dialogBuilder.setView(txtInput);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            //when user clicks okay
            public void onClick(DialogInterface dialog, int which) {
                String strName = "default";
                    strName = txtInput.getText().toString();
                    // Added a check to make sure that the save name is unique
                    if (LOAD_LIST.contains(strName)) {
                        saveDialogReplace();
                    }else{
                        //Added the save name to the global arrayList
                        LOAD_LIST.add(strName);
                        // Add the preset to the database
                        addPreset(strName, ar1, ar2);
                        Toast.makeText(getApplicationContext(), "Your save has been named.", Toast.LENGTH_LONG).show();

                    }
            }
        });
        // when user clicks cancel
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(),"Your save has not been named.", Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });

        //output
        AlertDialog saveDialog = dialogBuilder.create();
        saveDialog.show();


    }

    //adds basic functionality to the load button
    public void loadDialogBox() {

        // if statement to check empty list
        if (LOAD_LIST.isEmpty()) {
            Toast.makeText(getApplicationContext(), "You have not saved any roll presets.", Toast.LENGTH_LONG).show();
        } else {

            //variables
            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this);
            final String loadNamesList[] = LOAD_LIST.toArray(new String[LOAD_LIST.size()]);

            //process
            dialogBuilder.setTitle("Please select your save");
            dialogBuilder.setSingleChoiceItems(loadNamesList, -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String loadName = loadNamesList[which];
                    // Load values here
                    loadPreset(loadName);
                    Toast.makeText(getApplicationContext(), "You have picked a saved roll.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            });

            //output
            AlertDialog saveDialog = dialogBuilder.create();
            saveDialog.show();
        }
    }

    public void addPreset(String name, int[] numDice, int[] bonus) {
        // Uses the DicePresets constructor to initialize a new preset object with values from the
        // users edit text fields
        DicePresets preset = new DicePresets(name, numDice[0], numDice[1], numDice[2], numDice[3], numDice[4], numDice[5], numDice[6],
                bonus[0], bonus[1], bonus[2], bonus[3], bonus[4], bonus[5], bonus[6]);
         handle.addPreset(preset);
    }

    public void loadPreset(String loadName) {
        // When the user loads a presets, this method will place their roll values into the edit
        // text fields.
        Toast.makeText(getApplicationContext(), "SACK CAGA WAEFGA.", Toast.LENGTH_SHORT).show();

        int v[] = handle.getPreset(loadName);
        Toast.makeText(getApplicationContext(), "Array successful", Toast.LENGTH_SHORT).show();

        // references to EditText fields
        EditText d4Num = (EditText)findViewById(R.id.d4numBox);
        EditText d6Num = (EditText)findViewById(R.id.d6numBox);
        EditText d8Num = (EditText)findViewById(R.id.d8numBox);
        EditText d10Num = (EditText)findViewById(R.id.d10numBox);
        EditText d12Num = (EditText)findViewById(R.id.d12numBox);
        EditText d20Num = (EditText)findViewById(R.id.d20numBox);
        EditText d100Num = (EditText)findViewById(R.id.d100numBox);

        EditText d4Bon = (EditText)findViewById(R.id.d4Bonus);
        EditText d6Bon = (EditText)findViewById(R.id.d6Bonus);
        EditText d8Bon = (EditText)findViewById(R.id.d8Bonus);
        EditText d10Bon = (EditText)findViewById(R.id.d10Bonus);
        EditText d12Bon = (EditText)findViewById(R.id.d12Bonus);
        EditText d20Bon = (EditText)findViewById(R.id.d20Bonus);
        EditText d100Bon = (EditText)findViewById(R.id.d100Bonus);
        Toast.makeText(getApplicationContext(), "Get Refs successful", Toast.LENGTH_SHORT).show();

        d4Num.setText(v[0]);
        d6Num.setText(v[1]);
        d8Num.setText(v[2]);
        d10Num.setText(v[3]);
        d12Num.setText(v[4]);
        d20Num.setText(v[5]);
        d100Num.setText(v[6]);
        d4Bon.setText(v[7]);
        d6Bon.setText(v[8]);
        d8Bon.setText(v[9]);
        d10Bon.setText(v[10]);
        d12Bon.setText(v[11]);
        d20Bon.setText(v[12]);
        d100Bon.setText(v[13]);
        Toast.makeText(getApplicationContext(), "Set refs stuck?.", Toast.LENGTH_SHORT).show();

    }

    public void saveDialogReplace(){
        AlertDialog.Builder dialogBuilder;
        //variables
        dialogBuilder = new AlertDialog.Builder(this);

        //process
        dialogBuilder.setTitle("Rename?");
        dialogBuilder.setMessage("This name has already been used. Would you like to replace it?");
        dialogBuilder.setPositiveButton("Replace", new DialogInterface.OnClickListener() {

            //when user clicks okay
            public void onClick(DialogInterface dialog, int which) {
                //add a method that deletes and then replaces the save
                Toast.makeText(getApplicationContext(), "Your save has been replaced.", Toast.LENGTH_LONG).show();

            }
        });
        // when user clicks cancel
        dialogBuilder.setNegativeButton("Don't Replace", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Your save has not been replaced.", Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });
        //output
        AlertDialog saveDialog = dialogBuilder.create();
        saveDialog.show();
    }
}
