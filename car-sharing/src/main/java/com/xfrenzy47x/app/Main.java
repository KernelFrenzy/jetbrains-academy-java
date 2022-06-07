package com.xfrenzy47x.app;

import com.xfrenzy47x.app.db.Database;
import com.xfrenzy47x.app.services.MainService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String dbName = args.length == 2 ? args[1] : "stage-four";

        Database db = new Database(dbName);
        Scanner scanner = new Scanner(System.in);

        MainService mainService = new MainService(db, scanner);
        mainService.run();
    }
}
