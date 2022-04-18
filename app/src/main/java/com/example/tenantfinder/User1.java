package com.example.tenantfinder;


public class User1 extends ListItem {
    String Type;
    String address;
    String price;
    String phone;
    String details;
    String imageUrl;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User1(String s)
    {}
    public User1()
    {

    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getType() {
        return Type;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public String getPhone() {
        return phone;
    }

    public String getDetails() {
        return details;
    }
}
