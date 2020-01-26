package by.epam.mtlcwtchr.pfa.client.controller.command.exception;

import by.epam.mtlcwtchr.pfa.client.service.exception.ServiceException;

public class CommandNotSupportedException extends ServiceException {

    public CommandNotSupportedException(String message) {
        super(message);
    }

    public CommandNotSupportedException(Throwable cause) {
        super(cause);
    }
}
