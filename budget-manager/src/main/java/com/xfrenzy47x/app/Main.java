package com.xfrenzy47x.app;

import com.xfrenzy47x.app.model.Bank;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        int input = 1;
        while (input != 0) {
            input = bank.checkAction();
        }
    }
}



