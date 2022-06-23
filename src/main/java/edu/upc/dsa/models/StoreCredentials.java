package edu.upc.dsa.models;

public class StoreCredentials {

    String nameItem;
    String username;

    public StoreCredentials(){}

    public StoreCredentials(String nameItem, String username) {
        this.nameItem = nameItem;
        this.username = username;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
