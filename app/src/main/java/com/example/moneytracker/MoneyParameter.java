
package com.example.moneytracker;

public class MoneyParameter {
    int Id;
    String Title;
    String Description;
    String Date;
    Double Amount;
    int Type;
    private boolean isSelected = false;

    public MoneyParameter(int id,String title, String description, Double amount, String date, int type) {
        Id = id;
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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isSelected(){
        return isSelected;
    }
    public void setSelected(boolean x){
        isSelected = x;
    }

    public String getAmountToString(){
        String amount = String.format("%.2f",Amount);
        return amount;
    }
}

