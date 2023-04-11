package com.example.androidcashier.ui;

import ir.mirrajabi.searchdialog.core.Searchable;

public class Categorys implements Searchable {
    String CurrencyName;
    String value;


    public Categorys(String currencyName, String value) {
        CurrencyName = currencyName;
        this.value = value;
    }

    @Override
    public String getTitle() {
        return  CurrencyName;
    }
    public String getValue() {
        return  value;
    }

    public String getCurrencyName() {
        return CurrencyName;
    }

    public void setCurrencyName(String currencyName) {
        CurrencyName = currencyName;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
