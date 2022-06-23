package edu.upc.dsa;


import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.*;

public class ItemManagerTest {
    private static Logger logger = Logger.getLogger(UserManagerTest.class);
    ItemManagerImpl manager;


    @Before
    public void setUp() {
        this.manager = ItemManagerImpl.getInstance();

        //Configuring Log4j
        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        logger.debug("Debug Test Message!");
        logger.info("Info Test Message!");
        logger.warn("Warning Test Message!");
        logger.error("Error Test Message!");
        //Initialzing Test User
        this.manager.addItem(new Item("flecha", "para el arco", 15));

    }

    @Test
    public void itemTest(){
        manager.getItem("flecha");
    }
}
