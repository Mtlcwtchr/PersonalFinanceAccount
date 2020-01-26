package by.epam.mtlcwtchr.pfa.client.dao.connection;

import by.epam.mtlcwtchr.pfa.client.dao.exception.DAOAccessException;
import by.epam.mtlcwtchr.pfa.client.dao.exception.DAOException;

import java.io.InputStream;
import java.io.OutputStream;

public interface Connection {

    void create() throws DAOAccessException;
    void establish(boolean append) throws DAOAccessException;
    void close() throws DAOAccessException;

    InputStream getReader() throws DAOException;
    OutputStream getWriter() throws DAOException;

}
