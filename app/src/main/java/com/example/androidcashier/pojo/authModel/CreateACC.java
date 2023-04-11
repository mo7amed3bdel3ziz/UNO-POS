package com.example.androidcashier.pojo.authModel;

public class CreateACC {


        String   ClientName;
        String   AndroidID;
        String   Status;
        String   CompanyName;
        String   Phone;

    public CreateACC(String clientName, String androidID, String status, String companyName, String phone) {
        ClientName = clientName;
        AndroidID = androidID;
        Status = status;
        CompanyName = companyName;
        Phone = phone;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getAndroidID() {
        return AndroidID;
    }

    public void setAndroidID(String androidID) {
        AndroidID = androidID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
