package com.corntree.milpa.fly.api.rest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

import com.corntree.milpa.fly.api.rest.packet.request.RegistRequest;

public class HttpClient {
    public static void main(String[] args) throws Exception {
        long now = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            URLConnection connection = new URL("http://localhost:8080").openConnection();
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(new RegistRequest("ybak", "password", "ybak@mail.com").toJson().getBytes());
            InputStream inputStream = connection.getInputStream();
            String response = IOUtils.toString(inputStream);
            System.out.println(response);
            outputStream.close();
            inputStream.close();
        }
        System.out.println(System.currentTimeMillis() - now);

    }
}
