package edu.upc.dsa.services;



import edu.upc.dsa.DAO.*;
import edu.upc.dsa.UserManager;
import edu.upc.dsa.UserManagerImpl;

import io.swagger.annotations.*;


import edu.upc.dsa.DAO.ItemDAO;
import edu.upc.dsa.DAO.UserDAO;
import edu.upc.dsa.DAO.UserDAOImpl;
import edu.upc.dsa.DAO.InventoryDAO;
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

@Api(value = "/user", description = "Endpoint to User Service")
@Path("/user")
public class UserServices {

    private UserDAO userDAO;
    private InventoryDAO inventoryDAO;
    private ItemDAO itemDAO;
    private GameDAO gameDAO;

    public UserServices() {
        this.userDAO = UserDAOImpl.getInstance();
        this.inventoryDAO = InventoryDAOImpl.getInstance();
        this.itemDAO = ItemDAOImpl.getInstance();
        this.gameDAO = GameDAOImpl.getInstance();

        /*if (userDAO.userListSize() == 0) {
            this.userDAO.addUser("Esther", "EstheMC", "12345", "esther@gmail.com");
            this.userDAO.addUser("Oscar", "Oscar", "123", "oscar@gmail.com");

            Item i1 = new Item("Espada", "Para atacar", 50);
            Item i2 = new Item("Llave", "Abre una puerta", 100);

            //u1.addItem(i1);
            //u2.addItem(i2);
        }*/
    }

    //Login de usuario
    @POST
    @ApiOperation(value = "Login usuario", notes = "Password")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")

    })

    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response userLogIn(CredentialsLogIn credLogin) {
        User u = this.userDAO.getUser(credLogin.getUsername());

        if (u != null) {
            return Response.status(201).entity(u).build();
        } else
            return Response.status(404).build();
    }

    //Registro de usuario
    @POST
    @ApiOperation(value = "Registrar nuevo usuario", notes = "Nombre y password")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 500, message = "Validation Error"),
            @ApiResponse(code = 409, message = "User Already Exist")
    })
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User u) {
        if (u.getUsername().isEmpty() || u.getPassword().isEmpty()) {
            return Response.status(500).build();
        }else if(userDAO.existsusername(u.getUsername()) || userDAO.existsmail(u.getMail())){
            return Response.status(409).build();
        }else{
            u.setMail(u.getMail());
            u.setName(u.getName());
            this.userDAO.addUser(u.getName(), u.getUsername(), u.getPassword(), u.getMail());
            return Response.status(201).entity(u).build();
        }
    }

    //Get de la lista de usuarios
    @GET
    @ApiOperation(value = "Get de todos los usuarios", notes = "Get todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer = "Lista"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
       List<User> users = this.userDAO.getAllUsers();
       GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users){};
       return Response.status(201).entity(entity).build();
    }

    //Get de un usuario
    @GET
    @ApiOperation(value = "Get de un usuario", notes = "Get un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer = "Lista"),
    })
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username") String username) {
        User user = this.userDAO.getUser(username);
        GenericEntity<User> entity = new GenericEntity<User>(user){};
        return Response.status(201).entity(entity).build();
    }

    //Get lista de items de un usuario
    //Falta acabar de implementarla
    /*@GET

    @ApiOperation(value = "Get Item list", notes = "Get Item por username")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer = "Lista"),
            @ApiResponse(code = 404, message = "Username no encontrado"),

    })
    @Path("/item/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemListUser(@PathParam("username") String username) {
        User user = this.manager.getUser(username);
        if (user == null){
            return Response.status(404).build();
        }
        else{
            List<Item> itemList = user.getItemList();
            GenericEntity<List<Item>> entity = new GenericEntity<List<Item>>(itemList){};
            return Response.status(201).entity(entity).build();
        }
    }*/
    //Eliminar un usuario
    @DELETE
    @ApiOperation(value = "Delete a user", notes = "Eliminar usuario por username")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/delete/{username}")
    public Response deleteUser(@PathParam("username") String username) {

        User user = userDAO.getUser(username);
        if(userDAO.existsusername(username)){
            userDAO.deleteUserByUsername(username);
            inventoryDAO.deleteInventoryByUsername(username);
            return Response.status(200).entity(user).build();
        }
        return Response.status(404).build();
    }

    //Updateamos un usuario
    @PUT
    @ApiOperation(value = "Update a user", notes = "Updatear un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "Usuario no encontrado"),
            @ApiResponse(code = 406, message = "Usuario en uso"),
            @ApiResponse(code = 409, message = "Mail en uso"),
            @ApiResponse(code = 500, message = "Datos erroneos")
    })
    @Path("/update/{username}")
    public Response updateUser(@PathParam("username") String oldUsername, RegisterCredentials rCr) {

        User oldUser = userDAO.getUser(oldUsername);

        if (!userDAO.existsusername(oldUsername)) {
            return Response.status(404).build();
        } else {
            User newUser = new User(rCr.getName(), rCr.getPassword(), rCr.getUsername(), rCr.getMail());
            if (rCr.getName().isEmpty() && rCr.getPassword().isEmpty() && rCr.getUsername().isEmpty() && rCr.getMail().isEmpty())
                return Response.status(500).build();
            else {
                if (rCr.getName().isEmpty())
                    newUser.setName(oldUser.getName());
                if (rCr.getPassword().isEmpty())
                    newUser.setPassword(oldUser.getPassword());
                if (rCr.getUsername().isEmpty())
                    newUser.setUsername(oldUsername);
                if (rCr.getMail().isEmpty())
                    newUser.setMail(oldUser.getMail());
                if (!oldUsername.equals(rCr.getName()) && userDAO.existsusername(rCr.getName()))
                    return Response.status(406).build();
                if (!oldUser.getMail().equals(rCr.getMail()) && userDAO.existsmail(rCr.getMail()))
                    return Response.status(409).build();
                else {
                    userDAO.updateUserParameters(oldUsername, newUser);
                    inventoryDAO.updateUsername(oldUsername, newUser);
                    if (gameDAO.existsUsername(oldUsername))
                        gameDAO.updateUsername(oldUsername, newUser);
                    return Response.status(200).entity(newUser).build();
                }
            }
        }
    }



}
