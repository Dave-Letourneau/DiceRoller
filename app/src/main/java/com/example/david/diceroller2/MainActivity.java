package com.example.david.diceroller2;

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
            int[] subTotals = roller(vals);

            int rollSum = 0;
            for(int n = 0; n < 7; n++) {
                // for each number in vals...
                rollSum += subTotals[n];
            }
            for (int n = 0; n < 7; n++) {
                rollSum += bonus[n];
            }
            rollDialogBox(subTotals, bonus, rollSum, vals);
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
            int[] vals = getDiceNumbers(); //needs to be fixed?
            int[] bonus = getBonus(); //needs to be fixed?
            // load button was clicked. Load bonuses + dice layout from preferences.
            loadDialogBox(vals, bonus);
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

    public int[] roller(int[] vals) {
        Random rand = new Random();
        int[] values = new int[7];

        int subValues = 0;
        int numOfDie = vals[0];
        for(int i = 0; i < numOfDie; i++){
            subValues += rand.nextInt(4) + 1;
        }
        values[0] = subValues;

        subValues = 0;
        numOfDie = vals[1];
        for(int i = 0; i < numOfDie; i++){
            subValues += rand.nextInt(6) + 1;
        }
        values[1] = subValues;

        subValues = 0;
        numOfDie = vals[2];
        for(int i = 0; i < numOfDie; i++){
            subValues += rand.nextInt(8) + 1;
        }
        values[2] = subValues;

        subValues = 0;
        numOfDie = vals[3];
        for(int i = 0; i < numOfDie; i++){
            subValues += rand.nextInt(10) + 1;
        }
        values[3] = subValues;

        subValues = 0;
        numOfDie = vals[4];
        for(int i = 0; i < numOfDie; i++){
            subValues += rand.nextInt(12) + 1;
        }
        values[4] = subValues;

        subValues = 0;
        numOfDie = vals[5];
        for(int i = 0; i < numOfDie; i++){
            subValues += rand.nextInt(20) + 1;
        }
        values[5] = subValues;

        subValues = 0;
        numOfDie = vals[6];
        for(int i = 0; i < numOfDie; i++){
            subValues += rand.nextInt(100) + 1;
        }
        values[6] = subValues;

        return values;
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
                    saveDialogReplace(strName, ar1, ar2);
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
    public void loadDialogBox(int[] vals, int[] bonus) {

        final int[] finalVals = vals;
        final int[] finalBonus = bonus;
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
                    deleteOrLoad(loadName, finalVals, finalBonus);

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
        DicePresets p = handle.getDice(loadName);

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

        // set edit texts to corresponding fields
        // EditText holds strings, so we concatenate the ints into strings.
        d4Num.setText("" + p.get_d4Num());
        d6Num.setText("" + p.get_d6Num());
        d8Num.setText("" + p.get_d8Num());
        d10Num.setText("" + p.get_d10Num());
        d12Num.setText("" + p.get_d12Num());
        d20Num.setText("" + p.get_d20Num());
        d100Num.setText("" + p.get_d100Num());

        d4Bon.setText("" + p.get_d4Bonus());
        d6Bon.setText("" + p.get_d6Bonus());
        d8Bon.setText("" + p.get_d8Bonus());
        d10Bon.setText("" + p.get_d10Bonus());
        d12Bon.setText("" + p.get_d12Bonus());
        d20Bon.setText("" + p.get_d20Bonus());
        d100Bon.setText("" + p.get_d100Bonus());

        Toast.makeText(getApplicationContext(), "values set.", Toast.LENGTH_SHORT).show();

    }

    public void saveDialogReplace(String saveName, int[] vals, int[] bonus){
        AlertDialog.Builder dialogBuilder;
        // create a duplicates to be declared final
        final String saveDupe = saveName;
        final int[] ar1 = vals;
        final int[] ar2 = bonus;
        //variables
        dialogBuilder = new AlertDialog.Builder(this);

        //process
        dialogBuilder.setTitle("Rename?");
        dialogBuilder.setMessage("This name has already been used. Would you like to replace it?");
        dialogBuilder.setPositiveButton("Replace", new DialogInterface.OnClickListener() {

            //when user clicks okay
            public void onClick(DialogInterface dialog, int which) {
                //add a method that deletes and then replaces the save
                handle.deletePreset(saveDupe);
                addPreset(saveDupe, ar1, ar2);
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

    //messages are correctly formatted, something strange is happening with the actual roll function
    //as vals holds the number of that type of die and there are a lot of strange numbers
    //we will have to fix this later.
    public void rollDialogBox(int[] subTotals, int[] bonus, int rollSum, int[] vals){
        AlertDialog.Builder dialogBuilder;
        //variables
        dialogBuilder = new AlertDialog.Builder(this);

        //process
        dialogBuilder.setTitle("Your Roll");
        dialogBuilder.setMessage(
                "Die - # of Die - Roll Total - Bonus\n" +
                        "d4        -      " + vals[0] + "       -    " + subTotals[0] + "       -    " + bonus[0] + "\n" +
                        "d6        -      " + vals[1] + "       -    " + subTotals[1] + "       -    " + bonus[1] + "\n" +
                        "d8        -      " + vals[2] +"       -    " + subTotals[2] + "       -    " + bonus[2] + "\n" +
                        "d10      -      " + vals[3] + "       -    " + subTotals[3] + "       -    " + bonus[3] + "\n" +
                        "d12      -      " + vals[4] + "       -    " + subTotals[4] + "       -    " + bonus[4] + "\n" +
                        "d20      -      " + vals[5] + "       -    " + subTotals[5] + "       -    " + bonus[5] + "\n" +
                        "d100    -      " + vals[6] + "       -    " + subTotals[6] + "       -    " + bonus[6] + "\n\n" +
                        "Total of all rolls and bonuses = " + rollSum);
        dialogBuilder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {

            //when user clicks okay
            public void onClick(DialogInterface dialog, int which) {
                //dismisses the dialog box
                dialog.dismiss();

            }
        });
        //output
        AlertDialog saveDialog = dialogBuilder.create();
        saveDialog.show();
    }

    //an inner dialog box to go within the onClick method of loadDialogBox
    public void deleteOrLoad(String loadName, int[] vals, int[] bonus){
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(this);
        final String finalLoadName = loadName;
        final int[] finalVals = vals;
        final int[] finalBonus = bonus;

        dialogBuilder.setMessage("Would you like to load or delete your save?");
        dialogBuilder.setPositiveButton("Load", new DialogInterface.OnClickListener() {

            //when user clicks okay
            public void onClick(DialogInterface dialog, int which) {
                loadPreset(finalLoadName);
                Toast.makeText(getApplicationContext(), "You have picked a saved roll.", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        // when user clicks cancel
        dialogBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Remove the preset from the database
                handle.deletePreset(finalLoadName);
                // Remove the preset from the list
                LOAD_LIST.remove(finalLoadName);
                Toast.makeText(getApplicationContext(), "Your save has been deleted.", Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });

        AlertDialog saveDialog = dialogBuilder.create();
        saveDialog.show();
    }
}
