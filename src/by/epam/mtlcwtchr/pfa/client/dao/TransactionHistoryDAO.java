package by.epam.mtlcwtchr.pfa.client.dao;

import by.epam.mtlcwtchr.pfa.client.dao.exception.DAOException;

import java.util.Calendar;
import java.util.HashMap;

public interface TransactionHistoryDAO {

    void write(int ownerID, String transactionDescription) throws DAOException;
    void create(int ownerID) throws DAOException;

}
