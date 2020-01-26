package by.epam.mtlcwtchr.pfa.client.view;

import by.epam.mtlcwtchr.pfa.client.bean.User;
import by.epam.mtlcwtchr.pfa.client.controller.CommandController;
import by.epam.mtlcwtchr.pfa.client.dao.ClientDAO;
import by.epam.mtlcwtchr.pfa.client.dao.impl.ConsoleClientDAO;
import by.epam.mtlcwtchr.pfa.client.service.Chronicler;
import by.epam.mtlcwtchr.pfa.client.service.Profile;
import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;
import by.epam.mtlcwtchr.pfa.client.service.impl.Admin;
import by.epam.mtlcwtchr.pfa.client.service.impl.Customer;

public class Main {


    public static void main(String[] args) {

        ClientDAO clientDAO = new ConsoleClientDAO();
        Profile profile = null;


        try {
            profile = authenticate(clientDAO);
        } catch (ServiceException ex){
            System.out.println(ex.getMessage());
            System.exit(-1);
        }

        while (true){
            try{
                String request = clientDAO.getLine();
                CommandController.determineCommand(profile, request);
            } catch (ServiceException ex){
                System.out.println(ex.getMessage());
            }
        }

    }

    private static Profile authenticate(ClientDAO clientDAO) throws ServiceException{
        Profile profile = new Profile();
        String signRequest = clientDAO.getLine();
        CommandController.determineCommand(profile, signRequest);
        User user = profile.getUser();
        if(profile.getUser().getAccessModifier()){
            profile = new Admin();
        } else{
            profile = new Customer();
        }
        profile.setUser(user);
        return profile;
    }

}
