package com.example.androidcashier.pojo.crudOperationProduct;

public class UpdateProductModel {
            String   Barcode;
            Double Selling_Price;

    public UpdateProductModel(String barcode, Double selling_Price) {
        Barcode = barcode;
        Selling_Price = selling_Price;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public Double getSelling_Price() {
        return Selling_Price;
    }

    public void setSelling_Price(Double selling_Price) {
        Selling_Price = selling_Price;
    }
}
