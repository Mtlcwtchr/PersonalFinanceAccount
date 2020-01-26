package by.epam.mtlcwtchr.pfa.client.service;

import by.epam.mtlcwtchr.pfa.client.bean.Account;
import by.epam.mtlcwtchr.pfa.client.bean.User;
import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;

import java.util.HashMap;

public interface AdminRules {

    HashMap<Integer, Account> getAccounts(int ownerID) throws ServiceException;
    HashMap<Integer, User> getUsersList() throws ServiceException;
    void setBanned(int userID, boolean value) throws ServiceException;
    void setBlocked(int ownerID, int accountID, boolean value) throws ServiceException;

}
