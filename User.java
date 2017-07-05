package com.example.akshaydesai.drinkmate;

/**
 * Created by akshaydesai on 7/2/17.
 */

public class User {
    protected String sex;
    protected double weight;

    public User(String sx, double wt){
        sex = sx;
        weight = wt;
    }

    public String getSex(){
        return sex;
    }

    public double getWeight(){
        return weight;
    }

    public String toString(){
        return "The user is a "+sex+" who weighs "+weight+" lbs";
    }
}
