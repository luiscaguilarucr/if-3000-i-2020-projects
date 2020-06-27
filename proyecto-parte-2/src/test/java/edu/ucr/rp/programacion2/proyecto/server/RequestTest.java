package edu.ucr.rp.programacion2.proyecto.server;

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
        System.out.println("JSON Format: ");

        System.out.println(json);

        Request request1 = jsonUtil.asObject(json, Request.class);


        System.out.println("Object Format: ");
        System.out.println(request1);
    }

    public void testSetType() {
    }
}