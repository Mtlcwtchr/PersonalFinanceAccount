package by.epam.mtlcwtchr.pfa.client.dao.connection.impl;

import by.epam.mtlcwtchr.pfa.client.dao.connection.Connection;
import by.epam.mtlcwtchr.pfa.client.dao.exception.DAOAccessException;
import by.epam.mtlcwtchr.pfa.client.dao.exception.DAOException;

import java.io.*;
import java.util.Properties;

public class TextFileConnection implements Connection {

    private FileInputStream reader;
    private FileOutputStream writer;

    private String directoryName;
    private String fileName;

    public TextFileConnection(String fileName) throws DAOAccessException{
        Properties properties = new Properties();
        this.fileName = fileName;
        try {
            properties.load(new FileReader("properties/DATABASES_PATHS.properties"));
            directoryName = properties.getProperty("directoryName");
        } catch (IOException ex){
            throw new DAOAccessException(ex);
        }
    }

    public void create() throws DAOAccessException {
        try {
            new File(directoryName + "\\" + fileName + ".txt").createNewFile();
        } catch (IOException ex){
            throw new DAOAccessException(ex);
        }
    }

    public void establish(boolean append) throws DAOAccessException{
        try {
            reader = new FileInputStream(directoryName+"\\"+fileName+".txt");
            writer = new FileOutputStream(directoryName+"\\"+fileName+".txt", append);
        } catch (IOException ex){
            throw new DAOAccessException(ex);
        }
    }

    public void close() throws DAOAccessException{
        try{
            reader.close();
            writer.close();
        } catch (IOException ex){
            throw new DAOAccessException(ex);
        }
    }

    public FileInputStream getReader() {
        return reader;
    }
    public FileOutputStream getWriter() {
        return writer;
    }

}
