package by.epam.mtlcwtchr.pfa.client.controller;

import by.epam.mtlcwtchr.pfa.client.controller.command.CommandFactory;
import by.epam.mtlcwtchr.pfa.client.controller.command.type.CommandType;
import by.epam.mtlcwtchr.pfa.client.controller.command.exception.CommandNotSupportedException;
import by.epam.mtlcwtchr.pfa.client.service.Chronicler;
import by.epam.mtlcwtchr.pfa.client.service.Profile;
import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;

import java.util.HashMap;

public class CommandController {

    public static void determineCommand(Profile profile, String request) throws ServiceException{

        String[] requestSplitted = request.split(" ");

        try {
            if (profile.getUser() == null) {
                if (CommandType.valueOf(requestSplitted[0]) != CommandType.SIGN_IN &&
                        CommandType.valueOf(requestSplitted[0]) != CommandType.SIGN_UP) {
                    throw new ServiceException("Must authenticate first");
                }
            }
        } catch (IllegalArgumentException ex){
            throw new CommandNotSupportedException("Command not supported");
        }

        try {
            if (CommandType.valueOf(requestSplitted[0]) == CommandType.SIGN_IN ||
                    CommandType.valueOf(requestSplitted[0]) == CommandType.SIGN_UP) {
                if (profile.getUser() != null) {
                    throw new ServiceException("Already authenticated");
                }
            }
        } catch (IllegalArgumentException ex){
            throw new CommandNotSupportedException("Command not supported");
        }

        String[] args = new String[requestSplitted.length-1];
        System.arraycopy(requestSplitted, 1, args, 0, args.length);

        CommandFactory commandFactory;
        try{
            commandFactory =CommandFactory.getCommandFactory(profile, CommandType.valueOf(requestSplitted[0]));
        } catch (IllegalArgumentException ex){
            throw new CommandNotSupportedException("Command not supported");
        }

        HashMap<?, ?> map = new HashMap<>();

        try {
            assert commandFactory != null;
            commandFactory.execute(args);
        } catch (CommandNotSupportedException ex) {
            map = commandFactory.executeWithReturn(args);
        }

        if(!(requestSplitted[0].equals("SIGN_IN")||requestSplitted[0].equals("SIGN_UP")))
            Chronicler.writeChronicle(profile.getUser(), request);

        map.forEach((i, obj) -> System.out.println(obj));

    }

}
