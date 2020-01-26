package by.epam.mtlcwtchr.pfa.client.controller.command;

import by.epam.mtlcwtchr.pfa.client.controller.command.exception.CommandNotSupportedException;
import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;

import java.util.HashMap;

public class HelpCommandFactory extends CommandFactory{

    public void execute(String... args) throws ServiceException {
        throw new CommandNotSupportedException("Command not supported");
    }

    public HashMap<Integer, String> executeWithReturn(String... args) {
        HashMap<Integer, String> map = new HashMap<>();
        int i=-1;
        for (CommandType value : CommandType.values()) {
            map.put(++i, value.name());
        }
        return map;
    }

}
