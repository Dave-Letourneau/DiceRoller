package com.example.david.diceroller;

/**
 * Created by David on 12/29/2015.
 *
 * This class contains the DicePreset objects to be stored
 * in the SQLdatabase
 */
public class DicePresets {

    // basic properties of the preset object
    private int _id;
    private String _presetName;

    // number of dice per type
    private int _d4Num;
    private int _d6Num;
    private int _d8Num;
    private int _d10Num;
    private int _d12Num;
    private int _d20Num;
    private int _d100Num;

    // bonuses per dice type
    private int _d4Bonus;
    private int _d6Bonus;
    private int _d8Bonus;
    private int _d10Bonus;
    private int _d12Bonus;
    private int _d20Bonus;
    private int _d100Bonus;

    public DicePresets(String _presetName) {
        // Constructor to allow user to name their preset
        this._presetName = _presetName;
    }


    // The setter methods for each field.
    // In the future, I might replace these with a single
    // setter and just use an Array.
    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_presetName(String _presetName) {
        this._presetName = _presetName;
    }

    public void set_d4Num(int _d4Num) {
        this._d4Num = _d4Num;
    }

    public void set_d6Num(int _d6Num) {
        this._d6Num = _d6Num;
    }

    public void set_d8Num(int _d8Num) {
        this._d8Num = _d8Num;
    }

    public void set_d10Num(int _d10Num) {
        this._d10Num = _d10Num;
    }

    public void set_d12Num(int _d12Num) {
        this._d12Num = _d12Num;
    }

    public void set_d20Num(int _d20Num) {
        this._d20Num = _d20Num;
    }

    public void set_d100Num(int _d100Num) {
        this._d100Num = _d100Num;
    }

    public void set_d4Bonus(int _d4Bonus) {
        this._d4Bonus = _d4Bonus;
    }

    public void set_d6Bonus(int _d6Bonus) {
        this._d6Bonus = _d6Bonus;
    }

    public void set_d8Bonus(int _d8Bonus) {
        this._d8Bonus = _d8Bonus;
    }

    public void set_d10Bonus(int _d10Bonus) {
        this._d10Bonus = _d10Bonus;
    }

    public void set_d12Bonus(int _d12Bonus) {
        this._d12Bonus = _d12Bonus;
    }

    public void set_d20Bonus(int _d20Bonus) {
        this._d20Bonus = _d20Bonus;
    }

    public void set_d100Bonus(int _d100Bonus) {
        this._d100Bonus = _d100Bonus;
    }

    // Getter methods for each field
    // Again, might change to arrays in the future
    public int get_id() {
        return _id;
    }

    public String get_presetName() {
        return _presetName;
    }

    public int get_d4Num() {
        return _d4Num;
    }

    public int get_d6Num() {
        return _d6Num;
    }

    public int get_d8Num() {
        return _d8Num;
    }

    public int get_d10Num() {
        return _d10Num;
    }

    public int get_d12Num() {
        return _d12Num;
    }

    public int get_d20Num() {
        return _d20Num;
    }

    public int get_d100Num() {
        return _d100Num;
    }

    public int get_d4Bonus() {
        return _d4Bonus;
    }

    public int get_d6Bonus() {
        return _d6Bonus;
    }

    public int get_d8Bonus() {
        return _d8Bonus;
    }

    public int get_d10Bonus() {
        return _d10Bonus;
    }

    public int get_d12Bonus() {
        return _d12Bonus;
    }

    public int get_d20Bonus() {
        return _d20Bonus;
    }

    public int get_d100Bonus() {
        return _d100Bonus;
    }
}
