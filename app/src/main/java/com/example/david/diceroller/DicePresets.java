package com.example.david.diceroller;

/**
 * Created by David on 12/29/2015.
 *
 * This class contains the DicePreset objects to be stored
 * in the SQLdatabase MyDBHandler.java
 */
public class DicePresets {

    // basic properties of the preset object
    private int _id;
    private String _presetName;

    // number of dice per type, stored in an array.
    private int[] _diceNum;

    // bonuses per dice type
    private int[] _diceBonus;

    public DicePresets(String _presetName) {
        // Constructor to allow user to name their preset
        this._presetName = _presetName;
    }

    // Methods to set values
    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_presetName(String _presetName) {
        this._presetName = _presetName;
    }

    public void set_diceNum(int[] _diceNum) {
        this._diceNum = _diceNum;
    }

    public void set_diceBonus(int[] _diceBonus) {
        this._diceBonus = _diceBonus;
    }

    // Methods to get values
    public int get_id() {
        return _id;
    }

    public String get_presetName() {
        return _presetName;
    }

    public int[] get_diceNum() {
        return _diceNum;
    }

    public int[] get_diceBonus() {
        return _diceBonus;
    }
}
