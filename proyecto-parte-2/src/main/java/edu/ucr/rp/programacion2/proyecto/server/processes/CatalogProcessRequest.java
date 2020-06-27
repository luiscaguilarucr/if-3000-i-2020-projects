package edu.ucr.rp.programacion2.proyecto.server.processes;

import edu.ucr.rp.programacion2.proyecto.server.messages.CatalogRequest;
import edu.ucr.rp.programacion2.proyecto.server.messages.Request;

import java.io.IOException;
import java.net.Socket;

import static edu.ucr.rp.programacion2.proyecto.server.processes.RequestProcessUtil.receive;

public class CatalogProcessRequest {

    private Request InsertCatalogProcess(Socket socket) throws IOException, ClassNotFoundException {
        CatalogRequest orderRequest = receive(CatalogRequest.class, socket);
//        System.out.println("Recibiendo la orden:");
//        System.out.println(jsonUtil.asJson(orderRequest.getMenuItems()));//Solo para ejemplificar que recibimos una opcion de menu
//        InvoiceResponse invoiceResponse = new InvoiceResponse();
//        invoiceResponse.setAmount(10_000);//modificar sumando las opciones de menu
//        send(invoiceResponse, socket);
//        PaymentRequest paymentRequest = receive(PaymentRequest.class, socket);
//        System.out.println("Pago Recibido");
//        System.out.println(jsonUtil.asJson(paymentRequest));
        return receive(Request.class, socket);

    }
}
