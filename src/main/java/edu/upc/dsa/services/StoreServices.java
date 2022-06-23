package edu.upc.dsa.services;


import edu.upc.dsa.UserManager;
import edu.upc.dsa.UserManagerImpl;
import edu.upc.dsa.ItemManagerImpl;
import edu.upc.dsa.ItemManager;
import edu.upc.dsa.models.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/store", description = "Endpoint to User Service")
@Path("/store")
public class StoreServices {

    private ItemManager manager;
    public StoreServices() {
        this.manager = ItemManagerImpl.getInstance();

        this.manager.addItem(new Item("Manzana", "Regenera vida", 200));
        this.manager.addItem(new Item("Espada", "Para atacar", 50));
        this.manager.addItem(new Item("Llave", "Abre una puerta", 100));
        this.manager.addItem(new Item("Palo", "No hace nada", 10));



    }

    //Get de la lista de items
    @GET
    @ApiOperation(value = "Get de todos los items", notes = "Get todos los items")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class, responseContainer = "Lista"),
    })
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllItems() {
        List<Item> listaitems = this.manager.getAllItems();
        GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(listaitems){};
        return Response.status(201).entity(entity).build();
    }

    //Get de la lista de items ordenados por precio
    @GET
    @ApiOperation(value = "Get de todos los items por precio", notes = "Get todos los items por precio")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Item.class, responseContainer = "Lista"),
            @ApiResponse(code = 404, message = "Lista no encontrada")
    })
    @Path("/getOrdenado")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemsPorPrecio() {
        List<Item> listaitemsprecio = this.manager.getItemsPorPrecio();
        GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(listaitemsprecio){};

        if(listaitemsprecio.size() > 0){
            return Response.status(201).entity(entity).build();
        }
        return Response.status(404).entity(entity).build();
    }
}
