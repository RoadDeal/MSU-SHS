/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.tools;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author ABDUL
 */
public class DataDispatcher {

    public static void dispatch(ResultSet resultSet, Statement statement, Connection conn) throws ExceptionHandler{

        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException exception) {
                throw new ExceptionHandler(exception);
            }
        }

        if(statement != null){
            try {
                statement.close();
            } catch (SQLException exception) {
                throw new ExceptionHandler(exception);
            }
        }

        if(conn != null){
            try {
                conn.close();
            } catch (SQLException exception) {
                throw new ExceptionHandler(exception);
            }
        }
    }

    public static void dispatch(ResultSet resultSet, PreparedStatement pStatement, Connection conn) throws ExceptionHandler{

        if( resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (pStatement != null){
            try {
                pStatement.close();
            } catch (SQLException exception) {
                throw new ExceptionHandler(exception);
            }
        }

        if (conn != null){
            try {
                conn.close();
            } catch (SQLException exception) {
                throw new ExceptionHandler(exception);
            }

        }
    }
}
