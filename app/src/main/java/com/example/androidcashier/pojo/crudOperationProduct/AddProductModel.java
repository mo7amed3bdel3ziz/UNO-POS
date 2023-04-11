package com.example.androidcashier.pojo.crudOperationProduct;

public class AddProductModel {


    Double Selling_Price;
    String ItemName;
    String Barcode;

    int Record_ID = 1;
    String Description = "sample string 4";
    String Editor = "sample string 5";
    String Date = "2022-08-02T23:42:19.0016171+02:00";
    int ItemGroup_ID = 1;
    int Status = 64;
    Double size = 1.0;
    int Supplier_ID = 1;

    public AddProductModel(Double selling_Price, String itemName, String barcode) {
        Selling_Price = selling_Price;
        ItemName = itemName;
        Barcode = barcode;
    }
}
