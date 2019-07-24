package com.example.moneytracker;

public class MoneyParameter {
    String Title;
    String Description;
    String Date;
    Double Amount;
    int Type;

    public MoneyParameter(String title, String description, Double amount, String date, int type) {
        Title = title;
        Description = description;
        Amount = amount;
        Type = type;
        Date = date;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public Double getAmount() {
        return Amount;
    }

    public int getType() {
        return Type;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
