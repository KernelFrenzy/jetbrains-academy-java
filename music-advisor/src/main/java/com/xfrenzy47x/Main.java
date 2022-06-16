package com.xfrenzy47x;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xfrenzy47x.api.AppClient;
import com.xfrenzy47x.api.AppServer;
import com.xfrenzy47x.controller.DataController;
import com.xfrenzy47x.model.Category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static boolean authenticated = false;
    static DataController dataController;
    static AppClient client;
    static List<Category> categoryList = new ArrayList<>();

    private static void init(String[] args) {
        String accessUrl = "https://accounts.spotify.com";
        String resourceUrl = "https://api.spotify.com";
        int dataPerPage = 5;
        int port = 8080;

        String prev = "";
        for (String arg : args) {
            if (arg.equals("-access") || arg.equals("-resource") || arg.equals("-page")) {
                prev = arg;
            } else if (prev.equals("-access")) {
                prev = "";
                accessUrl = arg;
                Random random = new Random();
                port = random.nextInt(1000) + 8000;
            } else if (prev.equals("-resource")) {
                prev = "";
                resourceUrl = arg;
            } else if (prev.equals("-page")) {
                prev = "";
                dataPerPage = Integer.parseInt(arg);
            }
        }

        dataController = new DataController(dataPerPage);
        client = new AppClient(accessUrl, resourceUrl, port);
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String action = "0";

        init(args);

        while (!action.equals("exit")) {
            action = scanner.nextLine();

            if (authenticated) {
                switch (action) {
                    case "featured":
                        actionFeatured();
                        break;
                    case "new":
                        actionNew();
                        break;
                    case "categories":
                        actionCategories(true);
                        break;
                    case "prev":
                    case "next":
                        dataController.updatePaging(action);
                        break;
                }

                if (action.contains("playlists")) {
                    actionPlaylists(action.replaceAll("playlists", "").trim());
                }
            }

            if (action.equals("auth")) {
                actionAuth();
            } else if (action.equals("exit")) {
                if (dataController.hasData()) {
                    action = "0";
                    dataController.resetController();
                } else {
                    actionExit();
                }
            } else if (!authenticated) {
                System.out.println("Please, provide access for application.");
            }
        }
    }

    private static void actionAuth() throws IOException, InterruptedException {
        if (!authenticated) {
            client.printAuthUrl();
            System.out.println("waiting for code...");

            AppServer appServer = new AppServer(client.getPort());

            boolean gotCode = false;
            while(!gotCode) {
                if (appServer.getCode() != null) {
                    gotCode = true;
                } else {
                    Thread.sleep(100);
                }
            }

            String code = appServer.getCode().split("=")[1];


            System.out.println("code received");
            System.out.println(code);
            System.out.println("Making http request for access_token...");
            authenticated = client.authorize(code);

        }

        if (authenticated) {
            System.out.println("---SUCCESS---");
        }
    }

    private static void actionExit() {
        System.out.println("---GOODBYE---");
    }

    private static void actionNew() throws IOException, InterruptedException {
        JsonObject newReleases = client.getSomething("/v1/browse/new-releases");
        List<String> newData = new ArrayList<>();
        for (JsonElement item : newReleases.get("albums").getAsJsonObject().get("items").getAsJsonArray()) {
            JsonObject object = item.getAsJsonObject();
            String theLine = object.get("name").getAsString() + "\n";

            String artists = "";
            for (JsonElement artist : object.get("artists").getAsJsonArray()) {
                artists += artist.getAsJsonObject().get("name").getAsString() + ", ";
            }
            artists = "[" + artists.substring(0, artists.lastIndexOf(", ")) + "]";
            theLine += artists + "\n";

            theLine += (object.get("external_urls").getAsJsonObject().get("spotify").getAsString().replaceAll("\"", ""));

            newData.add(theLine);
        }
        dataController.setData(newData);
    }

    private static void actionFeatured() throws IOException, InterruptedException {
        JsonObject featured = client.getSomething("/v1/browse/featured-playlists");
        List<String> newData = new ArrayList<>();
        for (JsonElement item : featured.get("playlists").getAsJsonObject().get("items").getAsJsonArray()) {
            JsonObject object = item.getAsJsonObject();

            String theLine = object.get("name").getAsString() + "\n";
            theLine += object.get("external_urls").getAsJsonObject().get("spotify").getAsString().replaceAll("\"", "");

            newData.add(theLine);
        }
        dataController.setData(newData);
    }

    private static void actionCategories(boolean display) throws IOException, InterruptedException {
        JsonObject categories = client.getSomething("/v1/browse/categories");
        categoryList = new ArrayList<>();
        List<String> newData = new ArrayList<>();
        for (JsonElement item : categories.get("categories").getAsJsonObject().get("items").getAsJsonArray()) {
            JsonObject object = item.getAsJsonObject();

            categoryList.add(new Category(object.get("id").getAsString().trim(), object.get("name").getAsString().trim()));
            if (display) {
                newData.add(object.get("name").getAsString());
            }
        }
        if (display) {
            dataController.setData(newData);
        }
    }

    private static void actionPlaylists(String category) throws IOException, InterruptedException {
        if (categoryList.isEmpty()) {
            actionCategories(false);
        }

        Category theCategory = categoryList.stream().filter(x -> x.getName().equalsIgnoreCase(category)).findFirst().orElse(null);
        if (theCategory == null) {
            System.out.println("Unknown category name.");
        } else {
            JsonObject playlists = client.getSomething("/v1/browse/categories/" + theCategory.getId() + "/playlists");

            if (playlists.get("error") == null) {
                List<String> newData = new ArrayList<>();
                for (JsonElement item : playlists.get("playlists").getAsJsonObject().get("items").getAsJsonArray()) {
                    JsonObject object = item.getAsJsonObject();

                    String theLine = object.get("name").getAsString() + "\n";
                    theLine += (object.get("external_urls").getAsJsonObject().get("spotify").getAsString().replaceAll("\"", ""));
                    newData.add(theLine);
                }
                dataController.setData(newData);
            } else {
                System.out.println(playlists.get("error").getAsJsonObject().get("message").getAsString());
            }

        }
    }
}
