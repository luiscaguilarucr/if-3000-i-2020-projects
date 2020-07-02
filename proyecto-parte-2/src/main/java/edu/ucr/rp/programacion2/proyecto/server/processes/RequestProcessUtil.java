package edu.ucr.rp.programacion2.proyecto.server.processes;

import edu.ucr.rp.programacion2.proyecto.util.JsonUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestProcessUtil {
    private static JsonUtil jsonUtil = new JsonUtil();

    /**
     *
     * @param responseType
     * @param socket
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static  <T> T receive(Class<T> responseType, Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        return jsonUtil.asObject((String) in.readObject(), responseType);

    }

    public static void send(Object request, Socket socket) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(jsonUtil.asJson(request));
    }
}
