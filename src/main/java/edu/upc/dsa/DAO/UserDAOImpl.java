package edu.upc.dsa.DAO;

import edu.upc.dsa.UserManagerImpl;
import edu.upc.dsa.models.User;

import java.sql.Connection;
import java.util.List;
import java.util.LinkedList;

public class UserDAOImpl implements UserDAO {
    private static UserDAO instance;
    private final Session session;

    private UserDAOImpl() {

        session = FactorySession.openSession();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAOImpl();
        }
        return instance;
    }

    @Override
    public int addUser(String name, String username, String password, String email) {
        int usuarioID = 0;

        try {
            User usuario = new User(name, username, password, email);
            session.save(usuario);
        } catch (Exception e) {
            // LOG
        } finally {
            session.close();
        }

        return usuarioID;
    }

    @Override
    public List<User> getAllUsers() {
        return ((List) session.queryObjects(User.class));
    }

    @Override
    public User getUser(String username) {
        try {
            return ((User) session.getByParameter(User.class, "username", username));
        } catch (Exception e) {
            // LOG
            return null;
        } finally {
            session.close();
        }

    }

    @Override
    public int userListSize() {
        try {
            session.size();
        } catch (Exception e) {
            // LOG
        }
        return 0;
    }

    @Override
    public boolean deleteUser(User user) {
        return session.delete(user);
    }

    @Override
    public boolean deleteUserByUsername(String username) {
        return session.deleteByParameter(User.class, "username", username);
    }

    @Override
    public boolean deleteByParameter(String parameter, Object value) {
        return session.deleteByParameter(User.class, parameter, value);
    }

    @Override
    public boolean existsusername(String username) {
        if(session.getByParameter(User.class, "username", username) == null){
            return false;
        }else return true;
    }

    @Override
    public boolean existsmail(String email) {
        if(session.getByParameter(User.class, "mail", email) == null){
            return false;
        }else return true;
    }

    @Override
    public boolean getPasswordByName(String name, String password) {
        String passwordUser = (String) session.getParameterByParameter(User.class, "password", "name", name);
        if (password.equals(passwordUser))
            return true;
        else
            return false;
    }

    @Override
    public Object getParameterByParameter(String parameter, String byParameter, Object value) {
        return session.getParameterByParameter(User.class, parameter, byParameter, value);
    }
    @Override
    public boolean updateUser(User user) {
        return session.update(user);
    }

    @Override
    public boolean updateByParameter(User user, String parameter, Object value) {
        return session.updateByParameter(user, parameter, value);
    }

    @Override
    public boolean updateUserCoinsByUsername(int userCoins, String userName) {
        return session.updateParameterByParameter(User.class, "coins", userCoins, "name", userName);
    }

    @Override
    public boolean updateUserParameters(String oldName, User newUser) {
        if (session.updateParameterByParameter(User.class, "name", newUser.getName(), "name", oldName) &&
                session.updateParameterByParameter(User.class, "password", newUser.getPassword(), "name", newUser.getName()) &&
                session.updateParameterByParameter(User.class, "mail", newUser.getMail(), "name", newUser.getName()))
            return true;
        else
            return false;
    }

    @Override
    public boolean updateParameterByParameter(String parameter, Object parameterValue, String byParameter, Object byParameterValue) {
        return session.updateParameterByParameter(User.class, parameter, parameterValue, byParameter, byParameterValue);
    }

}

