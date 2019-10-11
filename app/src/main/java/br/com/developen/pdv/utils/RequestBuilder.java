package br.com.developen.pdv.utils;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class RequestBuilder {

    private static final String REST_SERVER_URL = "http://200.98.202.29:8080/erp/rest/";

//    private static final String REST_SERVER_URL = "http://192.168.0.108:8080/erp/rest/";

    private static Client client;

    static {

        if (client == null){

            client = ClientBuilder.newClient();

            client.register(JacksonJsonProvider.class);

            client.register(AndroidFriendlyFeature.class);

        }

    }

    public static WebTarget build(String... paths){

        WebTarget webTarget = client.target(REST_SERVER_URL);

        for (String s: paths)

            webTarget = webTarget.path(s);

        return webTarget;

    }

}