package com.shamardn.android.restaurant.data;

public class FoodDomain {
    private String title;
    private String details;
    private int fees;
    private int number;
    private int img;

    public FoodDomain(String title, String details, int fees, int number, int img) {
        this.title = title;
        this.details = details;
        this.fees = fees;
        this.number = number;
        this.img = img;
    }

    public FoodDomain(String title, String details, int fees, int img) {
        this.title = title;
        this.details = details;
        this.fees = fees;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
