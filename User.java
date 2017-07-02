package com.example.akshaydesai.drinkmate;

/**
 * Created by akshaydesai on 7/2/17.
 */

public class User {
    protected String sex;
    protected int weight;

    public User(String sx, int wt){
        sex = sx;
        weight = wt;
    }

    public String getSex(){
        return sex;
    }

    public int getWeight(){
        return weight;
    }

    public String toString(){
        return "The user is a "+sex+" who weighs "+weight+" lbs";
    }
}
