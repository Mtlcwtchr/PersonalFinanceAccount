package by.epam.mtlcwtchr.pfa.client.dao.impl;

import by.epam.mtlcwtchr.pfa.client.dao.ClientDAO;

import java.util.Scanner;

public class ConsoleClientDAO implements ClientDAO {

    @Override
    public String getLine() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        return line;
    }

}
