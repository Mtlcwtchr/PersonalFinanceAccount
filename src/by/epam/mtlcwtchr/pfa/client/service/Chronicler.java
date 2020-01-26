package by.epam.mtlcwtchr.pfa.client.service;

import by.epam.mtlcwtchr.pfa.client.bean.User;
import by.epam.mtlcwtchr.pfa.client.dao.TransactionHistoryDAO;
import by.epam.mtlcwtchr.pfa.client.dao.exception.DAOException;
import by.epam.mtlcwtchr.pfa.client.dao.factory.DAOFactory;
import by.epam.mtlcwtchr.pfa.client.dao.type.DAOType;
import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;

public final class Chronicler {

    public static void writeChronicle(User user, String description) throws ServiceException{
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOType.TEXT_FILE);
        assert daoFactory != null;
        TransactionHistoryDAO transactionHistoryDAO = daoFactory.getTransactionHistoryDAO();
        try {
            transactionHistoryDAO.write(user.getId(), description);
        } catch (DAOException ex){
            throw new ServiceException(ex);
        }
    }

}
