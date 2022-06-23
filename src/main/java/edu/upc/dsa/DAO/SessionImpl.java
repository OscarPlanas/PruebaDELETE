package edu.upc.dsa.DAO;

import edu.upc.dsa.util.ObjectHelper;
import edu.upc.dsa.util.QueryHelper;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class SessionImpl implements Session {
    private static SessionImpl instance;
    final static Logger logger = Logger.getLogger(SessionImpl.class);
    private final Connection conn;

    protected SessionImpl(Connection conn) {

        this.conn = conn;

    }

    @Override
    public boolean create(Object object){
        String insertQuery = QueryHelper.createQueryINSERT(object);
        logger.info(insertQuery);

        PreparedStatement pstm = null;
        try{
            pstm = conn.prepareStatement(insertQuery);
            int i = 1;
            for (String field: ObjectHelper.getFields(object)) {
                pstm.setObject(i++, ObjectHelper.getter(object, field));
            }
            logger.info(pstm.toString());
            pstm.executeQuery();
            return true;

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static SessionImpl getInstance(){
        if(instance==null){
            logger.info("Nueva instancia de: " + SessionImpl.class.getName());
            Connection conn = FactorySession.getConnection();
            instance = new SessionImpl(conn);
        }
        return instance;
    }
    public Object getByParameter(Class theClass, String byParameter, Object byParameterValue) {

        String selectQuery = QueryHelper.createQuerySELECTbyParameter(theClass, byParameter);
        logger.info(selectQuery);

        PreparedStatement pstm = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;

        try {
            Object object = theClass.getDeclaredConstructor().newInstance();

            pstm = conn.prepareStatement(selectQuery);

            pstm.setObject(1, byParameterValue);
            pstm.executeQuery();
            rs = pstm.getResultSet();

            if (rs.next()) {

                rsmd = rs.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String field = rsmd.getColumnName(i);
                    ObjectHelper.setter(object, field, rs.getObject(i));
                }
                return object;

            } else {
                return null;
            }

        } catch (SQLException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Object getByTwoParameters(Class theClass, String byFirstParameter, Object byFirstParameterValue, String bySecondParameter, Object bySecondParameterValue) {

        String selectQuery = QueryHelper.createQuerySELECTbyTwoParameters(theClass, byFirstParameter, bySecondParameter);
        logger.info(selectQuery);

        PreparedStatement pstm = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;

        try {
            Object object = theClass.getDeclaredConstructor().newInstance();

            pstm = conn.prepareStatement(selectQuery);

            pstm.setObject(1, byFirstParameterValue);
            pstm.setObject(2, bySecondParameterValue);
            logger.info(pstm.toString());
            pstm.executeQuery();
            rs = pstm.getResultSet();

            if (rs.next()) {

                rsmd = rs.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String field = rsmd.getColumnName(i);
                    ObjectHelper.setter(object, field, rs.getObject(i));
                }
                return object;

            } else {
                return null;
            }

        } catch (SQLException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object getParameterByParameter(Class theClass, String parameter, String byParameter, Object byParameterValue) {

        String selectQuery = QueryHelper.createQuerySELECTParameterByParameter(theClass, parameter, byParameter);
        logger.info(selectQuery);

        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = conn.prepareStatement(selectQuery);

            pstm.setObject(1, byParameterValue);
            pstm.executeQuery();
            rs = pstm.getResultSet();

            if (rs.next()) {
                return rs.getObject(1);
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void save(Object entity) {

        String insertQuery = QueryHelper.createQueryINSERT(entity);

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(insertQuery);
            pstm.setObject(1, 0);
            int i = 1;

            for (String field: ObjectHelper.getFields(entity)) {
                pstm.setObject(i++, ObjectHelper.getter(entity, field));
            }

            pstm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            this.conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Object get(Class theClass, int id) {
        String query = QueryHelper.createQuerySELECT(theClass);
        ResultSet rs;
        try{
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            ResultSetMetaData metadata = rs.getMetaData();
            int numberOfColumns = metadata.getColumnCount();

            Object o = theClass.newInstance();

            while (rs.next()){
                for (int i=1; i<=numberOfColumns; i++){
                    String columnName = metadata.getColumnName(i);
                    ObjectHelper.setter(o, columnName, rs.getObject(i));
                }
            }
            if(ObjectHelper.getter(o, "id") == null){
                return null;
            }
            return o;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean update(Object object) {
        String updateQuery = QueryHelper.createQueryUPDATE(object);
        logger.info(updateQuery);

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(updateQuery);
            int i = 1;

            for (String field: ObjectHelper.getFields(object)) {
                pstm.setObject(i++, ObjectHelper.getter(object, field));
            }

            pstm.setObject(i++, ObjectHelper.getter(object, ObjectHelper.getFields(object)[0]));
            pstm.executeQuery();
            logger.info(pstm.toString());
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateByParameter(Object object, String byParameter, Object byParameterValue) {

        String updateQuery = QueryHelper.createQueryUPDATEbyParameter(object.getClass(), byParameter);
        logger.info(updateQuery);

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(updateQuery);
            int i = 1;

            for (String field: ObjectHelper.getFields(object)) {
                pstm.setObject(i++, ObjectHelper.getter(object, field));
            }

            pstm.setObject(i++, ObjectHelper.getter(object, byParameterValue.toString()));
            pstm.executeQuery();
            logger.info(pstm.toString());
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateByTwoParameters(Object object, String byFirstParameter, Object byFirstParameterValue, String bySecondParameter, Object bySecondParameterValue) {

        String updateQuery = QueryHelper.createQueryUPDATEbyTwoParameters(object.getClass(), byFirstParameter, bySecondParameter);
        logger.info(updateQuery);

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(updateQuery);
            int i = 1;

            for (String field: ObjectHelper.getFields(object)) {
                pstm.setObject(i++, ObjectHelper.getter(object, field));
            }

            pstm.setObject(i++, ObjectHelper.getter(object, byFirstParameterValue.toString()));
            pstm.setObject(i++, ObjectHelper.getter(object, bySecondParameterValue.toString()));
            pstm.executeQuery();
            logger.info(pstm.toString());
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateParameterByParameter(Class theClass, String parameter, Object parameterValue, String byParameter, Object byParameterValue) {

        String updateQuery = QueryHelper.createQueryUPDATEParameterByParameter(theClass, parameter, byParameter);
        logger.info(updateQuery);

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(updateQuery);

            pstm.setObject(1, parameterValue);
            pstm.setObject(2, byParameterValue);
            logger.info(pstm.toString());
            pstm.executeQuery();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateParameterByTwoParameters(Class theClass, String parameter, Object parameterValue, String byFirstParameter, Object byFirstParameterValue, String bySecondParameter, Object bySecondParameterValue) {

        String updateQuery = QueryHelper.createQueryUPDATEParameterByTwoParameters(theClass, parameter, byFirstParameter, bySecondParameter);
        logger.info(updateQuery);

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(updateQuery);

            pstm.setObject(1, parameterValue);
            pstm.setObject(2, byFirstParameterValue);
            pstm.setObject(3, bySecondParameterValue);
            logger.info(pstm.toString());
            pstm.executeQuery();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean delete(Object entity) {

        String deleteQuery = QueryHelper.createQueryDELETE(entity);
        logger.info(deleteQuery);

        String [] username = ObjectHelper.getFields(entity);
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(deleteQuery);
            pstm.setString(1, username[1]);
            logger.info(pstm.toString());
            pstm.executeQuery();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteByParameter(Class theClass, String byParameter, Object byParameterValue) {

        String deleteQuery = QueryHelper.createQueryDELETEbyParameter(theClass, byParameter);
        logger.info(deleteQuery);

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(deleteQuery);

            pstm.setObject(1, byParameterValue);
            logger.info(pstm.toString());
            pstm.executeQuery();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteByTwoParameters(Class theClass, String byFirstParameter, Object byFirstParameterValue, String bySecondParameter, Object bySecondParameterValue) {

        String deleteQuery = QueryHelper.createQueryDELETEbyTwoParameters(theClass, byFirstParameter, bySecondParameter);
        logger.info(deleteQuery);

        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(deleteQuery);

            pstm.setObject(1, byFirstParameterValue);
            pstm.setObject(2, bySecondParameterValue);
            logger.info(pstm.toString());
            pstm.executeQuery();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Object> findAll(Class theClass) {
        return null;
    }

    public List<Object> findAll(Class theClass, HashMap params) {
        return null;
    }

    public List<Object> queryObjects(Class theClass) {
        String query = QueryHelper.createQuerySELECTAll(theClass);
        logger.info(query);

        PreparedStatement pstm = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        List<Object> objectResult = new LinkedList<>();
        Object object = null;

        try{
            object = theClass.newInstance();

            pstm = conn.prepareStatement(query);
            logger.info(pstm.toString());
            rs = pstm.executeQuery();

            while(rs.next()) {
                rsmd = rs.getMetaData();

                for(int j=1; j<=rsmd.getColumnCount(); j++) {
                    String name = rsmd.getColumnName(j);
                    ObjectHelper.setter(object,name, rs.getObject(j));
                }
                logger.info("Object added " +object);
                objectResult.add(object);
                object = theClass.newInstance();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return objectResult;
    }

    public List<Object> queryObjectsByParameter(Class theClass, String byParameter, Object byParameterValue) {

        String query = QueryHelper.createQuerySELECTbyParameter(theClass, byParameter);
        logger.info(query);

        PreparedStatement pstm = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        List<Object> objectResult = new LinkedList<>();
        Object object = null;

        try{
            object = theClass.newInstance();

            pstm = conn.prepareStatement(query);
            pstm.setObject(1, byParameterValue);

            logger.info(pstm.toString());
            rs = pstm.executeQuery();

            while(rs.next()) {
                rsmd = rs.getMetaData();

                for(int j=1; j<=rsmd.getColumnCount(); j++) {
                    String name = rsmd.getColumnName(j);
                    ObjectHelper.setter(object,name, rs.getObject(j));
                }
                logger.info("Object added " +object);
                objectResult.add(object);
                object = theClass.newInstance();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return objectResult;
    }

    public List<Object> orderObjectsByParameter(Class theClass, String byParameter) {

        String query = QueryHelper.createQueryORDERbyParameter(theClass, byParameter);
        logger.info(query);

        PreparedStatement pstm = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        List<Object> objectResult = new LinkedList<>();
        Object object = null;

        try{
            object = theClass.newInstance();

            pstm = conn.prepareStatement(query);

            logger.info(pstm.toString());
            rs = pstm.executeQuery();

            while(rs.next()) {
                rsmd = rs.getMetaData();

                for(int j=1; j<=rsmd.getColumnCount(); j++) {
                    String name = rsmd.getColumnName(j);
                    ObjectHelper.setter(object,name, rs.getObject(j));
                }
                logger.info("Object added " +object);
                objectResult.add(object);
                object = theClass.newInstance();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return objectResult;
    }

   /* public HashMap<Integer, Object> FindAllByParameter(Class theClass, String byParameter, Object byParameterValue) {

        String selectQuery = QueryHelper.createQuerySELECTbyParameter(theClass, byParameter);
        logger.info(selectQuery);

        PreparedStatement pstm = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        HashMap<Integer, Object> result = new HashMap<>();
        Object object = null;

        try {
            object = theClass.getDeclaredConstructor().newInstance();

            pstm = conn.prepareStatement(selectQuery);
            pstm.setObject(1, byParameterValue);
            rs = pstm.executeQuery();

            while (rs.next()) {
                rsmd = rs.getMetaData();

                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String field = rsmd.getColumnName(i);
                    ObjectHelper.setter(object, field, rs.getObject(i));
                }
                if (rs.getObject(1) instanceof Integer) {
                    result.put((int) rs.getObject(1), object);
                } else {
                    System.out.println("Error, " + rs.getObject(1) + " is not an Integer");
                }
                object = theClass.getDeclaredConstructor().newInstance();
            }
            return result;

        } catch (SQLException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<Integer, Object> FindAllByParameter(Class theClass, String byParameter, Object byParameterValue) {

        String selectQuery = QueryHelper.createQuerySELECTbyParameter(theClass, byParameter);
        logger.info(selectQuery);

        PreparedStatement pstm = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        HashMap<Integer, Object> result = new HashMap<>();
        Object object = null;

        try {
            object = theClass.getDeclaredConstructor().newInstance();

            pstm = conn.prepareStatement(selectQuery);
            pstm.setObject(1, byParameterValue);
            rs = pstm.executeQuery();

            while (rs.next()) {
                rsmd = rs.getMetaData();

                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String field = rsmd.getColumnName(i);
                    ObjectHelper.setter(object, field, rs.getObject(i));
                }
                if (rs.getObject(1) instanceof Integer) {
                    result.put((int) rs.getObject(1), object);
                } else {
                    System.out.println("Error, " + rs.getObject(1) + " is not an Integer");
                }
                object = theClass.getDeclaredConstructor().newInstance();
            }
            return result;

        } catch (SQLException | NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }*/
    @Override
    public int size() {
        String sizeQuery = QueryHelper.createQuerySIZEuser();
        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(sizeQuery);
            pstm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
