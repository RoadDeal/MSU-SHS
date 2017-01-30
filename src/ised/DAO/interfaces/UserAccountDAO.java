/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.DAO.interfaces;

import java.util.List;
import ised.model.UserAccount;
import ised.tools.ExceptionHandler;

/**
 *
 * @author ABDUL
 */
public interface UserAccountDAO {

    UserAccount getUserAccount(String userName) throws ExceptionHandler;

    List<UserAccount> getUserAccounts() throws ExceptionHandler;

    List<UserAccount> getUserAccounts(String userType) throws ExceptionHandler;

    List<UserAccount> getUserAccounts(String userType, String searchText) throws ExceptionHandler;

    boolean checkUserName(String username) throws ExceptionHandler;

    boolean checkUserAccount(int employeeID, String userType) throws ExceptionHandler;

    void resetPassword(int userID) throws ExceptionHandler;

    void changePassword(UserAccount userAccount) throws ExceptionHandler;

    void changeUserName(UserAccount userAccount) throws ExceptionHandler;

    void addUserAccount(UserAccount account) throws ExceptionHandler;

    void editUserAccount(UserAccount account) throws ExceptionHandler;

    void deleteUserAccount(int userID) throws ExceptionHandler;
}
