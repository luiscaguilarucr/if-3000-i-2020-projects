package edu.ucr.rp.programacion2.proyecto.server;

import edu.ucr.rp.programacion2.proyecto.domain.Inventory;
import edu.ucr.rp.programacion2.proyecto.server.messages.Request;
import edu.ucr.rp.programacion2.proyecto.server.messages.RequestType;
import edu.ucr.rp.programacion2.proyecto.util.JsonUtil;
import junit.framework.TestCase;
import org.junit.Test;

public class RequestTest extends TestCase {
    private JsonUtil jsonUtil = new JsonUtil();

    @Test
    public void testWrite() {

        // CREATE REQUEST
        System.out.println("Creando un request para insertar catálogo...");
        Request request = new Request();
        request.setType(RequestType.INSERT_CATALOG);
        System.out.println(request);

        // OBJECT -> JSON
        System.out.println("Convirtiendo request Object a JSON Format...");
        String asJson = jsonUtil.asJson(request);
        System.out.println("as JSON: " + asJson);

        // JSON -> Object
        System.out.println("Convirtiendo request JSON Format en un Object... ");
        Request request1 = jsonUtil.asObject(asJson, Request.class);
        System.out.println("as Object :" + request1);


    }

    public void testSetType() {

        Inventory inventory = new Inventory("12");
        String str = jsonUtil.asJson(inventory);

        Inventory inventory1 = jsonUtil.asObject(str, Inventory.class);
    }
}