package edu.upc.dsa.models;

public class Inventory {
    String username;
    String NameItem;
    int quantItem;
    String descrItem;
    String photoItem;

    public Inventory() {}

    public Inventory(String username, String NameItem, int quantItem, String descrItem, String photoItem) {
        this.username = username;
        this.NameItem = NameItem;
        this.quantItem = quantItem;
        this.descrItem = descrItem;
        this.photoItem = photoItem;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNameItem() {
        return NameItem;
    }

    public void setNameItem(String nameItem) {
        NameItem = nameItem;
    }

    public int getQuantItem() {
        return quantItem;
    }

    public void setQuantItem(int quantItem) {
        this.quantItem = quantItem;
    }

    public String getDescrItem() {
        return descrItem;
    }

    public void setDescrItem(String descrItem) {
        this.descrItem = descrItem;
    }

    public String getPhotoItem() {
        return photoItem;
    }

    public void setPhotoItem(String photoItem) {
        this.photoItem = photoItem;
    }
}
