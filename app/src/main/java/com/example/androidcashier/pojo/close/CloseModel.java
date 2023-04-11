package com.example.androidcashier.pojo.close;

public class CloseModel {

    int  totalQty;
    Double  TotalPrice;
    String  BillDate;
    String  items;

    public CloseModel(int totalQty, Double totalPrice, String billDate, String items) {
        this.totalQty = totalQty;
        TotalPrice = totalPrice;
        BillDate = billDate;
        this.items = items;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public Double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getBillDate() {
        return BillDate;
    }

    public void setBillDate(String billDate) {
        BillDate = billDate;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
