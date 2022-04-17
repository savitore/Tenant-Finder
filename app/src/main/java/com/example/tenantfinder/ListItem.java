package com.example.tenantfinder;

public class ListItem {

    private String head;
    private String desc;//desc=price
    private String address;
    private String details;
    private String pho;

    public ListItem() {

    }

    public ListItem(String s, String price, String address, String details, String phone_no) {
        this.head = s;
        this.desc = price;
        this.address= address;
        this.details= details;
        this.pho=phone_no;
    }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    public String getAddress() {
        return address;
    }

    public String getDetails() {
        return details;
    }

    public String getPho() {
        return pho;
    }
}
