package com.xfrenzy47x.server.helper;

import com.xfrenzy47x.server.model.CoreData;

import java.io.*;

public class Utility {

    private Utility() {
        throw new IllegalStateException("Utility class");
    }

    public static void serialize(Object obj, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos);) {
            oos.writeObject(obj);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Object deserialize(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static CoreData initializeCoreData() {
        CoreData coreData = new CoreData();
        Object obj = Utility.deserialize(coreData.getCoreDataPath());
        if (obj != null) {
            coreData = (CoreData) obj;
        }
        return coreData;
    }
}
