package edu.upc.dsa;

import edu.upc.dsa.models.Item;
import edu.upc.dsa.models.User;

import java.util.List;

public interface UserManager {


    //Registro usuario
    public User addUser(User user);
    User getUser(String username);

    //Eliminar usuario
    public void deleteUser(String username);

    //Login usuario
    public User userLogIn(String username, String pass);
    public List<User> getLoggedUsers();
    public void logOutUser(String username);

    //Ver usuario
    public int userListSize();
    public List<User> getAllUsers();

    //Updatear usuario
    public User changeName(User u);

    }
