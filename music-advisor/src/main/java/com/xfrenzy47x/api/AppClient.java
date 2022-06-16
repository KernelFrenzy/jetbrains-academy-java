package com.xfrenzy47x.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AppClient {
    HttpClient client;

    String accessToken;
    int port;

    String ACCESS_URL;
    String RESOURCE_URL;
    String CLIENT_ID = "3b30bdd4365b445c982297be859b241f";
    String BASE_64 = "M2IzMGJkZDQzNjViNDQ1Yzk4MjI5N2JlODU5YjI0MWY6YTA5OWIwZGNjNjRkNDE0YzkxNjc0YzA3YzQ0Y2JhYzc=";
    String REDIRECT_URI = "http://localhost:";

    public AppClient(String accessUrl, String resourceUrl, int port) {
        this.ACCESS_URL = accessUrl;
        this.RESOURCE_URL = resourceUrl;
        this.accessToken = "";
        this.REDIRECT_URI += port;
        this.port = port;
        client = HttpClient.newBuilder().build();
    }

    public int getPort() {
        return this.port;
    }

    public void printAuthUrl() {
        System.out.println(ACCESS_URL + "/authorize?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&response_type=code");
    }

    public boolean authorize(String code) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Basic " + BASE_64)
                .uri(URI.create(ACCESS_URL + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString("redirect_uri=" + REDIRECT_URI + "&grant_type=authorization_code&code=" + code))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
        if (jsonObject.get("access_token") != null) {
            accessToken = jsonObject.get("access_token").getAsString();
        }

        return !accessToken.isEmpty();
    }

    public JsonObject getSomething(String api) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(RESOURCE_URL + api))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return JsonParser.parseString(response.body()).getAsJsonObject();
    }
}
