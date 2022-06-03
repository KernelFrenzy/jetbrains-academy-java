package com.xfrenzy47x.app;

import com.xfrenzy47x.app.services.KMPService;
import com.xfrenzy47x.app.services.impl.KMPServiceImpl;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        if (args.length < 2) {
            System.out.println("More arguments required!");
        } else {
            String file = args[0];
            String pattern = args[1];
            KMPService kmpService = new KMPServiceImpl(file, pattern);
            kmpService.searchExtensions();
        }
    }
}
