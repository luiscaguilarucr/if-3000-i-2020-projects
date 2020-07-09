package edu.ucr.rp.programacion2.proyecto.logic;

/**
 * This class is used to manage troubles when the service is used.
 *
 * The exception will contains a message with the details of the trouble.
 */
public class ServiceException extends Exception{
    public ServiceException(String message) {
        super(message);
    }
}
