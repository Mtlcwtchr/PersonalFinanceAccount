package by.epam.mtlcwtchr.pfa.client.service;

import by.epam.mtlcwtchr.pfa.client.bean.Account;
import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;

import java.util.HashMap;

public interface CustomerRules {

    HashMap<Integer, Account> getAccounts() throws ServiceException;
    void createAccount() throws ServiceException;
    void replenish(int accountID, int value) throws ServiceException;
    void withdraw(int accountID, int value) throws ServiceException;
    void getDebt(int accountID, int value) throws ServiceException;
    void payOff(int accountID, int value) throws ServiceException;

}
