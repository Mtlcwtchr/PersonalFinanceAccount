package by.epam.mtlcwtchr.pfa.client.dao.factory;

import by.epam.mtlcwtchr.pfa.client.dao.*;
import by.epam.mtlcwtchr.pfa.client.dao.factory.impl.TextFileDAOFactory;
import by.epam.mtlcwtchr.pfa.client.dao.type.DAOType;

public abstract class DAOFactory {

    public static DAOFactory getDAOFactory(DAOType daoType){
        if(daoType==DAOType.TEXT_FILE)
            return new TextFileDAOFactory();
        return null;
    }

    public abstract UserDAO getUserDAO();
    public abstract AccountDAO getAccountDAO();
    public abstract TransactionHistoryDAO getTransactionHistoryDAO();

}
