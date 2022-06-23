package edu.upc.dsa.DAO;

import edu.upc.dsa.models.User;
import edu.upc.dsa.models.Game;

import java.util.List;

public interface GameDAO {

    boolean addGame(Game game);

    List<Game> getAllGames();
    List<Game> getAllGamesByUsername(String username);
    List<Game> orderGamesByPoints();

    Game getGameByUsername(String username);
    Game getByParameter(String parameter, Object value);

    boolean existsUsername(String username);

    boolean update(Game game);
    boolean updateByParameter(Game game, String parameter, Object value);
    boolean updateUsername(String oldName, User newUser);
    boolean updatePointsByUsername(int points, String username);
    boolean updateHealthByUsername(int health, String username);
    boolean updateParameterByParameter(String parameter, Object parameterValue, String byParameter, Object byParameterValue);

    boolean delete(Game game);
    boolean deleteByParameter(String parameter, Object value);

}
