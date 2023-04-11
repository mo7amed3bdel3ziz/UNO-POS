package com.example.androidcashier.pojo;

import java.util.ArrayList;

public class SetBillModel {
    public String ComID;
    public String AndroidID;
    ArrayList<ItemsModel> BillD=new ArrayList<>();

    public SetBillModel(String comID, String androidID, ArrayList<ItemsModel> items) {
        ComID = comID;
        AndroidID = androidID;
        this.BillD = items;
    }

    public String getComID() {
        return ComID;
    }

    public void setComID(String comID) {
        ComID = comID;
    }

    public String getAndroidID() {
        return AndroidID;
    }

    public void setAndroidID(String androidID) {
        AndroidID = androidID;
    }

    public ArrayList<ItemsModel> getItems() {
        return BillD;
    }

    public void setItems(ArrayList<ItemsModel> items) {
        this.BillD = items;
    }
}
