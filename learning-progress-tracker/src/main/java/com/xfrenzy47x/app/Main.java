package com.xfrenzy47x.app;

import com.xfrenzy47x.app.services.MenuService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuService service = new MenuService(scanner);
        service.run();
    }
}
