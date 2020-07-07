package edu.ucr.rp.programacion2.proyecto.server.messages;

import edu.ucr.rp.programacion2.proyecto.util.JsonUtil;
import junit.framework.TestCase;
import org.junit.Test;

import static edu.ucr.rp.programacion2.proyecto.server.messages.RequestType.INSERT_CATALOG;
import static edu.ucr.rp.programacion2.proyecto.server.messages.RequestType.UPDATE_CATALOG;

public class RequestTypeTest extends TestCase {


    JsonUtil jsonUtil = new JsonUtil();

    @Test
    public void testEnum(){


        System.out.println("Request Type: " + INSERT_CATALOG);
        System.out.println("Convirtiendo de OBJECT A JSON...");

        String str = jsonUtil.asJson(INSERT_CATALOG);
        System.out.println("Request Type (as JSON): " + str);


        System.out.println("Convirtiendo de JSON A OBJECT...");

        RequestType requestType = jsonUtil.asObject(str, RequestType.class);
        System.out.println("Request Type (as OBJECT): " + requestType);


    }
}