package com.example.androidcashier.pojo.close;

import com.example.androidcashier.pojo.Report;

import java.util.ArrayList;

public class Task1Closed {

    int State;
    String Message;
    ArrayList<Report> data;
    ArrayList<CloseModel> items;


    public ArrayList<Report> getData() {
        return data;
    }

    public void setData(ArrayList<Report> data) {
        this.data = data;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public ArrayList<CloseModel> getItems() {
        return items;
    }

    public void setItems(ArrayList<CloseModel> items) {
        this.items = items;
    }

    public Task1Closed(int state, String message, ArrayList<CloseModel> items) {
        State = state;
        Message = message;
        this.items = items;
    }
}
