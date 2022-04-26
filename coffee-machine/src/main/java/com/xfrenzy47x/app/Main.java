package com.xfrenzy47x.app;

import com.xfrenzy47x.app.interfaces.services.ICoffeeMachine;
import com.xfrenzy47x.app.services.CoffeeMachine;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ICoffeeMachine machine = new CoffeeMachine();
        do {
            String value = scanner.nextLine();
            machine.receiveInput(value);
        } while (!machine.exitApplication());
    }
}
