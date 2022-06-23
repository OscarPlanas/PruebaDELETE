package edu.upc.dsa;

import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class UserManagerTest {
    private static Logger logger = Logger.getLogger(UserManagerTest.class);
    UserManagerImpl manager;


    @Before
    public void setUp() {
        this.manager = UserManagerImpl.getInstance();

        //Configuring Log4j
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        logger.debug("Debug Test Message!");
        logger.info("Info Test Message!");
        logger.warn("Warning Test Message!");
        logger.error("Error Test Message!");
        //Initialzing Test User
        this.manager.addUser(new User("Renuka", "Renu12","Renujeje" ,"renuka@gmail.com"));

    }
    @Test
    public void loginTest(){
        manager.userLogIn("EstheMC", "12345");
        manager.userLogIn("Renujeje", "Renu12");
    }
    @Test
    public void RegistroTest(){
        this.manager.addUser(new User("Esther", "12345", "EstheMC", "esther@gmail.com"));
    }
}
