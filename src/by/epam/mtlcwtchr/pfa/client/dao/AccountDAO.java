package by.epam.mtlcwtchr.pfa.client.dao;

import by.epam.mtlcwtchr.pfa.client.bean.Account;
import by.epam.mtlcwtchr.pfa.client.dao.exception.DAOException;

import java.util.HashMap;

public interface AccountDAO {

    void createAccount(int ownerID) throws DAOException;
    void create(int ownerID) throws DAOException;
    void replenish(int ownerID,int keyNumber, int amount) throws DAOException;
    void withdraw(int ownerID,int keyNumber, int amount) throws DAOException;
    void payOff(int ownerID, int keyNumber, int amount) throws DAOException;
    void borrow(int ownerID, int keyNumber, int amount) throws DAOException;
    void setBlocked(int ownerID, int keyNumber, boolean isBanned) throws DAOException;
    Account read(int ownerID,int keyNumber) throws DAOException;
    HashMap<Integer, Account> readAllForOwner(int ownerID) throws DAOException;

}