package com.example.androidcashier.pojo;

import java.util.ArrayList;

public class Task {
    ArrayList<ItemsModel>Items=new ArrayList<>();

    public ArrayList<ItemsModel> getItems() {
        return Items;
    }

    public void setItems(ArrayList<ItemsModel> items) {
        Items = items;
    }
}
