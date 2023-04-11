package com.example.androidcashier.pojo;

public class ItemsModel {
    public Double Selling_Price;
    public String ItemName;
    public String Barcode;
    public Double balanc = 0.0;
    public Double contaty = 1.0;
    // salying -> 0
    //returns -> 1
    public int documentType = 1;

    public int State;
    public String Message;

    public int sales_id = 1;

    public int SR_No = 1;
    public String Description = "sample string 2";
    public Double UnitPrice = 1.0;
    public int Suppier_id = 1;
    public Double size = 1.0;
    public Double Discount = 1.0;
    public Double Supply_Price = 1.0;


    public int BillNo = 1;
    public int ItemID = 1;

    public ItemsModel(Double selling_Price, String itemName, Double contaty) {
        Selling_Price = selling_Price;
        ItemName = itemName;
        this.contaty = contaty;
    }

    public ItemsModel() {
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

    public Double getContaty() {
        return contaty;
    }

    public void setContaty(Double contaty) {
        this.contaty = contaty;
    }

    public Double getBalanc() {
        return balanc;
    }

    public void setBalanc(Double balanc) {
        this.balanc = balanc;
    }

    public ItemsModel(Double selling_Price, String itemName, String barcode) {
        Selling_Price = selling_Price;
        ItemName = itemName;
        Barcode = barcode;
    }

    public ItemsModel(Double selling_Price, String itemName, String barcode, int documentType) {
        Selling_Price = selling_Price;
        ItemName = itemName;
        Barcode = barcode;
        this.documentType = documentType;
    }

    public Double getSelling_Price() {
        return Selling_Price;
    }

    public void setSelling_Price(Double selling_Price) {
        Selling_Price = selling_Price;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }
}
