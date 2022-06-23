package edu.upc.dsa;

import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;

import java.lang.Object;
import java.util.*;

public class UserManagerImpl implements UserManager {
    private List<User> userList;
    private List<User> onlineUsersList;
    private HashMap<String, User> mapUser;
    private static UserManagerImpl instance;
    //logs
    final static Logger logger = Logger.getLogger(UserManagerImpl.class);

    private UserManagerImpl() {
        this.userList = new LinkedList<>();
        this.onlineUsersList = new LinkedList<>();
    }

    //Singleton
    public static UserManagerImpl getInstance() {
        //logger.info(instance);
        if (instance == null)
            instance = new UserManagerImpl();
        //logger.info(instance);
        return instance;
    }

    //Añadir usuario
    @Override
    public User addUser(User user) {
        String username = user.getUsername();
        for (User u : this.userList) {
            if (u.getUsername().equals(username)) {
                logger.info("Usuario " + username + " encontrado");
                return null;
            }
        }
        logger.info("Nuevo usuario: " + user);
        this.userList.add(user);
        logger.info("Nuevo usuario añadido: " + user);
        return user;
    }

    //Get de un usuario
    @Override
    public User getUser(String username) {
        logger.info("Usuario: " + username);
        for (User user : this.userList) {
            if (user.getUsername().equals((username))) {
                logger.info("Usuario " + username + " encontrado");
                return user;
            }
        }
        logger.info("Usuario " + username + " no encontrado");
        return null;
    }

    //Login de un usuario
    @Override
    public User userLogIn(String username, String pass) {
        User u = this.getUser(username);
        if (u == null) {
            logger.info("Este usuario no existe");
            return null;
        } else if (u.getPassword().equals(pass)) {
            this.onlineUsersList.add(u);
            logger.info("Usuario " + username + " ha podido entrar correctamente");
            return u;
        } else {
            logger.info("Contraseña incorrecta");
            return null;
        }
    }

    //Get de usuarios logueados
    @Override
    public List<User> getLoggedUsers() {
        return onlineUsersList;
    }

    //Desloguear
    @Override
    public void logOutUser(String username) {
        User u = this.getUser(username);
        if (u == null) {
            logger.info("El usuario no existe");
        } else {
            this.onlineUsersList.remove(u);
            logger.info("Usuario " + username + " ha salido correctamente");
        }
    }

    //Eliminamos usuario a través del username
    @Override
    public void deleteUser(String username) {
        User user = this.getUser(username);
        if (user == null) {
            logger.info("Usuario: " + username + " no encontrado");
        } else {
            this.userList.remove(user);
            logger.info("Usuario " + username + " eliminado");
        }
    }
    //Get de todos los usuarios
    @Override
    public List<User> getAllUsers(){
        return this.userList;
    }

    //Retorna tamaño de la lista de usuarios
    @Override
    public int userListSize() {
        return this.userList.size();
    }
    //Updateamos usuario, cambiamos la contraseña
    @Override
    public User changeName(User u){
        User user = this.getUser(u.getUsername());
        if(u!=null){
            logger.info("Usuario a updatear: "+ u.getUsername());
            //user.setUsername(u.getUsername());
            user.setName(u.getName());
            logger.info("Usuario updateado: "+user.getUsername());
        }
        else {
            logger.info("Usuario "+u.getUsername()+" no encontrado");
        }
        return user;
    }

}

