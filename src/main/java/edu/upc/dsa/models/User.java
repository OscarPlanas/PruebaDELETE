package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class User {
    private String name;
    private String password;
    private String username;
    private String mail;
    //public List<Item> itemList = new ArrayList<>();
    //private double numCoins;
   // private List<String> items;
    public User(String name, String username, String pass, String mail) {
        Random rand = new Random();

        this.name = name;
        this.password = pass;
        this.username = username;
        this.mail = mail;
    }
    //public List<Item> getItemList(){
    //    return itemList;
    //}

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addItem(Item e){}

}
