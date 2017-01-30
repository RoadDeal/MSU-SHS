/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.DAO.interfaces;

import java.sql.Connection;
import ised.tools.ExceptionHandler;

/**
 *
 * @author ABDUL
 */
public interface DBManager {

    public Connection getConnection() throws ExceptionHandler;
    public boolean isConnectedToDatabase() throws ExceptionHandler;
    
}
