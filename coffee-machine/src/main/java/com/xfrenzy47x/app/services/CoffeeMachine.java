package com.xfrenzy47x.app.services;

import com.xfrenzy47x.app.enums.Coffee;
import com.xfrenzy47x.app.enums.State;
import com.xfrenzy47x.app.interfaces.services.ICoffeeMachine;

public class CoffeeMachine implements ICoffeeMachine {

    private int money;
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private State state;
    private boolean exit;
    private String currentInput;

    public CoffeeMachine() {
        money = 550;
        water = 400;
        milk = 540;
        beans = 120;
        cups = 9;
        exit = false;
        printActions();
    }

    public boolean exitApplication() {
        return exit;
    }

    public void receiveInput(String value) {
        System.out.println();
        currentInput = value.toLowerCase();

        switch (state) {
            case CHOOSE_ACTION -> handleBaseActions();
            case PURCHASING -> continuePurchase();
            case PROMPT_FOR_WATER -> {
                incrementWater();
                promptForAddingMilk();
            }
            case PROMPT_FOR_MILK -> {
                incrementMilk();
                promptForAddingBeans();
            }
            case PROMPT_FOR_BEANS -> {
                incrementBeans();
                promptForAddingCups();
            }
            case PROMPT_FOR_CUPS -> {
                incrementCups();
                printActions();
            }
        }
    }

    //region Print and Handle Base Actions

    private void printActions() {
        System.out.println();
        state = (State.CHOOSE_ACTION);
        System.out.println("Write action (buy, fill, take, remaining, exit)");
    }

    private void handleBaseActions() {
        switch (currentInput) {
            case "buy" -> startPurchaseProcess();
            case "fill" -> promptForAddingWater();
            case "take" -> {
                System.out.println("I gave you $" + money);
                money = 0;
                printActions();
            }
            case "remaining" -> printCoffeeMachineStats();
            case "exit" -> exit = true;
            default -> {
                // Error Handling?
            }
        }
    }

    //endregion

    //region Purchasing

    private void startPurchaseProcess() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
        state = State.PURCHASING;
    }

    private void continuePurchase() {
        if (currentInput.equals("back")) {
            printActions();
        } else {
            int coffee = Integer.parseInt(currentInput);
            switch (coffee) {
                case 1 -> calcAndPrintAnswer(250, 0, 16, 4, Coffee.ESPRESSO);
                case 2 -> calcAndPrintAnswer(350, 75, 20, 7, Coffee.LATTE);
                case 3 -> calcAndPrintAnswer(200, 100, 12, 6, Coffee.CAPPUCCINO);
                default -> { /* Error Handling ? */ }
            }
        }
    }

    private void calcAndPrintAnswer(int waterRequired, int milkRequired, int beansRequired, int moneyGained, Coffee coffee) {
        String message = "";

        if (water >= waterRequired && milk >= milkRequired && beans >= beansRequired && cups >= 1) {
            message = buildPositiveMessageForPurchase(coffee);

            water -= waterRequired;
            beans -= beansRequired;
            milk -= milkRequired;
            cups--;
            money += moneyGained;
        } else {
            message = water < waterRequired ? buildNegativeMessageForPurchase("water") : message;
            message = message.isEmpty() && milk < milkRequired ? buildNegativeMessageForPurchase("milk") : message;
            message = message.isEmpty() && beans < beansRequired ? buildNegativeMessageForPurchase("beans") : message;
            message = message.isEmpty() && cups == 0 ? buildNegativeMessageForPurchase("cups") : message;
        }

        System.out.println(message);
        printActions();
    }

    private String buildPositiveMessageForPurchase(Coffee coffee) {
        return "I have enough resources, making you " +
                (coffee.equals(Coffee.ESPRESSO) ? "an " + coffee.toString().toLowerCase() : "a " + coffee.toString().toLowerCase()) +
                "!";
    }

    private String buildNegativeMessageForPurchase(String ingredient) {
        return "Sorry, not enough " + ingredient + "!";
    }

    //endregion

    //region Filling Coffee Machine

    private void promptForAddingWater() {
        System.out.println("Write how many ml of water you want to add: ");
        state = State.PROMPT_FOR_WATER;
    }

    private void incrementWater() {
        water += Integer.parseInt(currentInput);
    }

    private void promptForAddingMilk() {
        System.out.println("Write how many ml of milk you want to add: ");
        state = State.PROMPT_FOR_MILK;
    }

    private void incrementMilk() {
        milk += Integer.parseInt(currentInput);
    }

    private void promptForAddingBeans() {
        System.out.println("Write how many grams of coffee beans you want to add: ");
        state = State.PROMPT_FOR_BEANS;
    }

    private void incrementBeans() {
        beans += Integer.parseInt(currentInput);
    }

    private void promptForAddingCups() {
        System.out.println("Write how many disposable cups of coffee you want to add: ");
        state = State.PROMPT_FOR_CUPS;
    }

    private void incrementCups() {
        cups += Integer.parseInt(currentInput);
    }

    //endregion

    //region Print Coffee Machine Stats

    private void printCoffeeMachineStats() {
        System.out.println("The coffee machine has: ");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(beans + " g of coffee beans");
        System.out.println(cups + " disposable cups");
        System.out.println("$" + money + " of money");

        printActions();
    }

    //endregion
}
