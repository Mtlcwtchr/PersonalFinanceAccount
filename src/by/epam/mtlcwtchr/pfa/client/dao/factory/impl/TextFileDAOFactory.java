package by.epam.mtlcwtchr.pfa.client.dao.factory.impl;

import by.epam.mtlcwtchr.pfa.client.dao.AccountDAO;
import by.epam.mtlcwtchr.pfa.client.dao.TransactionHistoryDAO;
import by.epam.mtlcwtchr.pfa.client.dao.UserDAO;
import by.epam.mtlcwtchr.pfa.client.dao.factory.DAOFactory;
import by.epam.mtlcwtchr.pfa.client.dao.impl.TextFileAccountDAO;
import by.epam.mtlcwtchr.pfa.client.dao.impl.TextFileTransactionHistoryDAO;
import by.epam.mtlcwtchr.pfa.client.dao.impl.TextFileUserDAO;

public class TextFileDAOFactory extends DAOFactory {

    public UserDAO getUserDAO(){
        return new TextFileUserDAO();
    }
    public AccountDAO getAccountDAO(){
        return new TextFileAccountDAO();
    }
    public TransactionHistoryDAO getTransactionHistoryDAO(){
        return new TextFileTransactionHistoryDAO();
    }

}
