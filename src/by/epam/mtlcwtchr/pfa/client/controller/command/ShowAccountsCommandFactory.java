package by.epam.mtlcwtchr.pfa.client.controller.command;

import by.epam.mtlcwtchr.pfa.client.bean.Account;
import by.epam.mtlcwtchr.pfa.client.controller.command.exception.CommandNotSupportedException;
import by.epam.mtlcwtchr.pfa.client.service.Profile;
import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;
import by.epam.mtlcwtchr.pfa.client.service.impl.Admin;
import by.epam.mtlcwtchr.pfa.client.service.impl.Customer;

import java.util.HashMap;

public class ShowAccountsCommandFactory extends CommandFactory{

    Profile profile;

    ShowAccountsCommandFactory(Profile profile){
        this.profile = profile;
    }

    public void execute(String... args) throws CommandNotSupportedException {
        throw new CommandNotSupportedException("Command not supported");
    }

    public HashMap<Integer, Account> executeWithReturn(String... args) throws ServiceException{
        try {
            if (args==null || args.length == 0) {
                return ((Customer) profile).getAccounts();
            } else {
                return ((Admin) profile).getAccounts(Integer.parseInt(args[0], 16));
            }
        } catch (ClassCastException ex){
            throw new ServiceException("Wrong rules");
        }
    }

}
