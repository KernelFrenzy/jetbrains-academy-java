package com.xfrenzy47x.server.model;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class CoreData implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String CORE_DATA_FILE = "data.bin";
    private static final String CORE_DATA_LOC = "core-data/";
    private static final String DIR_SAVE_LOC = "data/";
    private static final String SERVER_ROOT = "/home/xfrenzy47x/git/jetbrains-academy-java-for-beginners/file-server/src/main/java/com/xfrenzy47x/server/";

    private final TreeMap<Integer, String> savedFiles;

    public CoreData() {
        createDir(new File(getSavePath()));
        createFile(new File(getCoreDataPath()));
        savedFiles = new TreeMap<>();
    }

    public String getCoreDataPath() {
        return SERVER_ROOT + CORE_DATA_LOC + CORE_DATA_FILE;
    }

    public String getSavePath() {
        return SERVER_ROOT + DIR_SAVE_LOC;
    }

    private void createDir(File dir) {
        try {
            dir.mkdir();
        } catch (SecurityException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addData(Integer id, String fileName) {
        savedFiles.put(id, fileName);
    }

    public void setData(String getBy, String fileNameOrId) {
        int tempId = -1;
        String tempName = "";
        if (getBy.equalsIgnoreCase("BY_ID")) {
            tempId = Integer.parseInt(fileNameOrId);
            tempName = savedFiles.get(tempId);
        } else if (getBy.equalsIgnoreCase("BY_NAME")) {
            for (Map.Entry<Integer, String> entry : savedFiles.entrySet()) {
                if (entry.getValue().equals(fileNameOrId)) {
                    tempId = entry.getKey();
                    tempName = fileNameOrId;
                    break;
                }
            }
        }

        if (tempId == -1) {
            tempName = generateFileName(fileNameOrId);
            tempId = savedFiles.size() > 0 ? savedFiles.lastKey() + 1 : 1;
        }

        fileName = tempName;
        fileId = tempId;
    }

    private String generateFileName(String fileName) {
        return fileName.equalsIgnoreCase("BLANK") ? System.currentTimeMillis() + ".bin" : fileName;
    }

    public void deleteData(Integer id) {
        savedFiles.remove(id);
    }

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    private Integer fileId;
    public Integer getFileId() {
        return fileId;
    }
}