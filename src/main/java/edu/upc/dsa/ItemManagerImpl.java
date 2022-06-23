package edu.upc.dsa;

import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;
import edu.upc.dsa.models.*;
import java.util.LinkedList;
import java.util.List;
import java.util.*;

public class ItemManagerImpl implements ItemManager {

    //logs
    final static Logger logger = Logger.getLogger(ItemManagerImpl.class);
    private List<Item> itemList;
    private static ItemManagerImpl instance;

    private ItemManagerImpl() {
        this.itemList = new LinkedList<>();
    }
    //Singleton
    public static ItemManagerImpl getInstance() {
        //logger.info(instance);
        if (instance == null)
            instance = new ItemManagerImpl();
        //logger.info(instance);
        return instance;
    }
    //Añadimos item
    @Override
    public Item addItem(Item item){
        String objeto = item.getName();
        for (Item i : this.itemList) {
            if (i.getName().equals(objeto)) {
                logger.info("Item " + objeto + " encontrado");
                return null;
            }
        }
        logger.info("Nuevo item: " + item);
        this.itemList.add(item);
        logger.info("Nuevo item añadido: " + item);
        return item;
    }

    //Get de un item
    @Override
    public Item getItem(String name){
        for(Item item: this.itemList){
            if(item.getName().equals(name)){
                logger.info("Item "+name+ " Encontrado");
                return item;
            }
        }
        logger.info("Item no encontrado");
        return null;
    }

    //Get de todos los items
    @Override
    public List<Item> getAllItems(){
        return this.itemList;
    }

    //Get de los items por precio ascendente
    @Override
    public List<Item> getItemsPorPrecio(){
        List<Item> orden = new LinkedList<>(this.itemList);
        Collections.sort(orden, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return Integer.compare(o1.getCoins(), o2.getCoins());
            }
        });
        logger.info("Listado de items ordenados por precio ascendente: " + orden.toString());
        return orden;
    }

    //Tamaño de la lista de items
    @Override
    public int itemListSize(){
        return this.itemList.size();
    }
}
