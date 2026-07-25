package com.example.soap;

import com.example.soap.generated.SumaRequest;
import com.example.soap.generated.SumaResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SumaEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/soap/suma";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "sumaRequest")
    @ResponsePayload
    public SumaResponse sumar(@RequestPayload SumaRequest request) {
        int resultado = request.getA() + request.getB();

        SumaResponse response = new SumaResponse();
        response.setResultado(resultado);
        return response;
    }
}