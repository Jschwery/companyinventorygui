package com.example.companyinventorygui;


public class OutSourcedPart extends Part {


    public OutSourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    private final String companyName;

    public String getCompanyName() {
        return companyName;
    }

}
