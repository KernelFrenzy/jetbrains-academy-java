package com.xfrenzy47x.app;

import com.xfrenzy47x.app.model.ShipInformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Helper {
    private Helper() {
        throw new IllegalStateException("Utility class");
    }
    protected static final Character[] LETTERS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

    public static List<ShipInformation> buildShipInformationList() {
        List<ShipInformation> shipInformationList = new ArrayList<>();
        shipInformationList.add(new ShipInformation("Aircraft Carrier", 5));
        shipInformationList.add(new ShipInformation("Battleship", 4));
        shipInformationList.add(new ShipInformation("Submarine", 3));
        shipInformationList.add(new ShipInformation("Cruiser", 3));
        shipInformationList.add(new ShipInformation("Destroyer", 2));
        return shipInformationList;
    }

    public static void passControl() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
