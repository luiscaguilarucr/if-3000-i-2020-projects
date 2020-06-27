package edu.ucr.rp.programacion2.proyecto.server;

import edu.ucr.rp.programacion2.proyecto.server.messages.Request;
import edu.ucr.rp.programacion2.proyecto.server.messages.RequestType;
import edu.ucr.rp.programacion2.proyecto.util.JsonUtil;
import junit.framework.TestCase;
import org.junit.Test;

public class RequestTest extends TestCase {
    private JsonUtil jsonUtil = new JsonUtil();

    @Test
    public void testWrite() {
        Request request = new Request();

        request.setType(RequestType.START);

        String json = jsonUtil.asJson(request);

        System.out.println(json);

        Request request1 = jsonUtil.asObject(json, Request.class);


        System.out.println(json);
    }

    public void testSetType() {
    }
}