package edu.upc.dsa;

import edu.upc.dsa.models.Item;

import java.util.List;

public interface ItemManager {

    //Funciones de Item
    public Item addItem(Item item);
    public Item getItem(String name);
    public List<Item> getAllItems();
    public List<Item> getItemsPorPrecio();
    public int itemListSize();
}
