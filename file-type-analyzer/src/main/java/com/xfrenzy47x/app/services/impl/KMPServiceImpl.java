package com.xfrenzy47x.app.services.impl;

import com.xfrenzy47x.app.model.Pattern;
import com.xfrenzy47x.app.model.Result;
import com.xfrenzy47x.app.services.KMPService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;

public class KMPServiceImpl implements KMPService {
    private File filesLoc;
    private List<Pattern> patterns;
    public KMPServiceImpl(String dir, String patterns) {
        this.filesLoc = new File(dir);
        populatePatterns(patterns);
        this.patterns.sort(Comparator.comparing(Pattern::getPriority).reversed());
    }

    public void searchExtensions() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Callable<Result>> callables = new ArrayList<>();
        for (File aFile : Objects.requireNonNull(filesLoc.listFiles())) {
            callables.add(() -> kmpExtensionFound(aFile));
        }

        List<Future<Result>> futures = executor.invokeAll(callables);
        for (Future<Result> future : futures) {
            Result result = future.get();
            System.out.println(result.getFile() + ": " + result.getPattern().getFileType());
        }
        executor.shutdown();
    }

    private Result kmpExtensionFound(File file) {
        byte[] fileBytes = readBytes(file);

        for (Pattern thePattern : patterns) {
            String pattern = thePattern.getRegex();
            int m = pattern.length();
            int n = fileBytes.length;

            int[] lps = new int[m];
            int j = 0;

            computeLPSArray(pattern, m, lps);
            boolean found = false;
            int i = 0;
            while (i < n) {
                if (pattern.charAt(j) == (char) fileBytes[i]) {
                    j++;
                    i++;
                }
                if (j == m) {
                    found = true;
                    j = lps[j - 1];
                }

                else if (i < n && pattern.charAt(j) != (char) fileBytes[i]) {
                    if (j != 0)
                        j = lps[j - 1];
                    else
                        i = i + 1;
                }
            }
            if (found) {
                return new Result(file, thePattern);
            }
        }
        return new Result(file, new Pattern());
    }

    private void computeLPSArray(String pat, int M, int[] lps)
    {
        int len = 0;
        int i = 1;
        lps[0] = 0;

        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            }
            else
            {
                if (len != 0) {
                    len = lps[len - 1];
                }
                else
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }

    private void populatePatterns(String pattern) {
        patterns = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(pattern))) {
            while (scanner.hasNextLine()) {
                String[] lineSplit = scanner.nextLine().split(";");
                patterns.add(new Pattern(lineSplit[0], lineSplit[1], lineSplit[2]));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private byte[] readBytes(File file) {
        byte[] bytes = new byte[0];
        try (InputStream inputStream = new FileInputStream(file)) {
            bytes = inputStream.readAllBytes();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
}
