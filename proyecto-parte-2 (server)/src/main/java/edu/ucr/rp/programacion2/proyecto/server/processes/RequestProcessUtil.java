package edu.ucr.rp.programacion2.proyecto.server.processes;

import edu.ucr.rp.programacion2.proyecto.util.JsonUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class contains methods used to send and receive data form one socket to another.
 */
public class RequestProcessUtil {
    // Variables
    private static final JsonUtil jsonUtil = new JsonUtil();
    // Methods

    /**
     * This method waits until receives a new request sent by a other socket.
     *
     * @param responseType Request Type class.
     * @param socket that contains the request.
     * @param <T> The class of the object that will be waiting.
     *
     * @return The Object.class specified as T.
     * @throws IOException Error in connection.
     * @throws ClassNotFoundException Error when tried tho convert the ObjectInputStream into T.
     */
    public static  <T> T receive(Class<T> responseType, Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return jsonUtil.asObject((String) in.readObject(), responseType);

    }

    /**
     * This method will send a new request with an object in it.
     *
     * @param request Request to send.
     * @param socket that contains the request.
     * @throws IOException Error in connection.
     */
    public static void send(Object request, Socket socket) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(jsonUtil.asJson(request));
    }
}
