package com.example.androidcashier.pojo;

import com.example.androidcashier.pojo.close.Task1Closed;
import com.example.androidcashier.ui.invoice.ReturnModel;

import java.util.ArrayList;

public class SetBillRutern {
    public String ComID;
    public String AndroidID;
    ArrayList<ReturnModel> BillD=new ArrayList<>();


    public SetBillRutern(String comID, String androidID, ArrayList<ReturnModel> billD) {
        ComID = comID;
        AndroidID = androidID;
        BillD = billD;
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

    public ArrayList<ReturnModel> getBillD() {
        return BillD;
    }

    public void setBillD(ArrayList<ReturnModel> billD) {
        BillD = billD;
    }
}
