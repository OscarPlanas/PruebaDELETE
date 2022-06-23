package edu.upc.dsa.models;

public class Item {
    private String name;
    private String description;
    private int coins;

    public Item(String name, String descripcion, int coins){
        this.name = name;
        this.description = descripcion;
        this.coins = coins;
    }
    public Item(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCoins() {
        return coins;
    }

    public int setCoins(int coins) {
        return this.coins = coins;
    }
}
