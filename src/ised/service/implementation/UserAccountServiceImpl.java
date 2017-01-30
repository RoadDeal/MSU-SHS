/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.service.implementation;

import java.util.List;
import ised.DAO.implementation.UserAccountDAOImpl;
import ised.DAO.interfaces.UserAccountDAO;
import ised.model.UserAccount;
import ised.service.interfaces.UserAccountService;
import ised.tools.ExceptionHandler;

/**
 *
 * @author ABDUL
 */
public class UserAccountServiceImpl implements UserAccountService {

    private UserAccountDAO dao;

    public UserAccountServiceImpl() {
        dao = new UserAccountDAOImpl();
    }

    public UserAccount getUserAccount(String userName) throws ExceptionHandler {
        return dao.getUserAccount(userName);
    }

    public List<UserAccount> getUserAccounts() throws ExceptionHandler {
        return dao.getUserAccounts();
    }

    public boolean checkUserAccount(int employeeID, String userType) throws ExceptionHandler {
        return dao.checkUserAccount(employeeID, userType);
    }

    public void resetPassword(int userID) throws ExceptionHandler {
        dao.resetPassword(userID);
    }

    public void changePassword(UserAccount userAccount) throws ExceptionHandler {
        dao.changePassword(userAccount);
    }

    public void changeUserName(UserAccount userAccount) throws ExceptionHandler {
        dao.changeUserName(userAccount);
    }

    public boolean checkUserName(String username) throws ExceptionHandler {
        return dao.checkUserName(username);
    }

    public void addUserAccount(UserAccount account) throws ExceptionHandler {
        dao.addUserAccount(account);
    }

    public void editUserAccount(UserAccount account) throws ExceptionHandler {
        dao.editUserAccount(account);
    }

    public List<UserAccount> getUserAccounts(String userType) throws ExceptionHandler {
        return dao.getUserAccounts(userType);
    }

    public void deleteUserAccount(int userID) throws ExceptionHandler {
        dao.deleteUserAccount(userID);
    }

    public List<UserAccount> getUserAccounts(String userType, String searchText) throws ExceptionHandler {
        return dao.getUserAccounts(userType, searchText);
    }
}
