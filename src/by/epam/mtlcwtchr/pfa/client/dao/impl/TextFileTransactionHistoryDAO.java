package by.epam.mtlcwtchr.pfa.client.dao.impl;

import by.epam.mtlcwtchr.pfa.client.dao.TransactionHistoryDAO;
import by.epam.mtlcwtchr.pfa.client.dao.connection.Connection;
import by.epam.mtlcwtchr.pfa.client.dao.connection.impl.TextFileConnection;
import by.epam.mtlcwtchr.pfa.client.dao.exception.DAOAccessException;
import by.epam.mtlcwtchr.pfa.client.dao.exception.DAOException;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class TextFileTransactionHistoryDAO implements TransactionHistoryDAO {

    private Properties properties;
    private String fileName;
    private boolean propertiesLoaded = false;

    @Override
    public void write(int ownerID, String transactionDescription) throws DAOException {

        loadProperties("historyFileSubName");
        Connection connection = new TextFileConnection(new Formatter().format("%s_%s",Integer.toHexString(ownerID),fileName).toString());
        connection.establish(true);

        PrintWriter writer = new PrintWriter(connection.getWriter());
        writer.write(new Date().toString() + " : " + transactionDescription+"\n");
        writer.flush();

        connection.close();

    }

    @Override
    public void create(int ownerID) throws DAOException {

        loadProperties("historyFileSubName");
        Connection connection = new TextFileConnection(new Formatter().format("%s_%s",Integer.toHexString(ownerID),fileName).toString());
        connection.create();

    }

    private void loadProperties(String propForFileName) throws DAOAccessException {
        if(propertiesLoaded) return;
        properties = new Properties();
        try {
            properties.load(new FileReader("properties/DATABASES_PATHS.properties"));
            fileName = properties.getProperty(propForFileName);
            propertiesLoaded = true;
        } catch (IOException ex){
            throw new DAOAccessException(ex);
        }
    }

}
