package com.example.androidcashier.pojo.authModel;

public class ModelStatMg {
     int State;
           String  Message;
           Double TotalBill;

           int BillNumber;
           String Url;

    public ModelStatMg(int state, String message, Double totalBill, int billNumber, String url) {
        State = state;
        Message = message;
        TotalBill = totalBill;
        BillNumber = billNumber;
        Url = url;
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

    public Double getTotalBill() {
        return TotalBill;
    }

    public void setTotalBill(Double totalBill) {
        TotalBill = totalBill;
    }

    public int getBillNumber() {
        return BillNumber;
    }

    public void setBillNumber(int billNumber) {
        BillNumber = billNumber;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
