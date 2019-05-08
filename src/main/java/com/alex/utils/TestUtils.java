package com.alex.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class TestUtils {

    public String requestToString(HttpRequestBase request)  {
        String response = "";
        HttpClient client = HttpClientBuilder.create().build();
        try {
            response =  EntityUtils.toString(client.execute(request).getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public HttpResponse request(HttpRequestBase request)  {
        HttpResponse response = null;
        HttpClient client = HttpClientBuilder.create().build();
        try {
            response =  client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
