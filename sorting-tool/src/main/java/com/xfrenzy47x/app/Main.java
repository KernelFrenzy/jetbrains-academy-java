package com.xfrenzy47x.app;

import com.xfrenzy47x.app.comparator.CustomSortingLongComparator;
import com.xfrenzy47x.app.comparator.CustomSortingStringComparator;
import com.xfrenzy47x.app.enums.SortingType;
import com.xfrenzy47x.app.model.CustomSorting;
import com.xfrenzy47x.app.model.SortingData;
import com.xfrenzy47x.app.util.Helper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static final String SORTED_DATA = "Sorted data:";

    public static void main(String[] args) {

        SortingData sortingT = handleArgs(args);

        if (!sortingT.getError().isEmpty()) {
            System.out.println(sortingT.getError());
        } else {
            switch (sortingT.getDataType()) {
                case LONG:
                    handleLong(sortingT);
                    break;
                case LINE:
                    handleLine(sortingT);
                    break;
                case WORD:
                default:
                    handleWord(sortingT);
                    break;
            }
        }
    }

    private static SortingData handleArgs(String[] args) {
        SortingData sortingT = new SortingData();

        String prev = "";
        for (String arg : args) {
            if (prev.isEmpty() && arg.contains("-")) {
                prev = arg;
            } else {
                if (prev.equals("-sortingType") && arg.contains("-")) {
                    sortingT.setError("No sorting type defined!");
                } else if (prev.equals("-dataType") && arg.contains("-")) {
                    sortingT.setError("No data type defined!");
                } else {
                    sortingT = setAndGetSortingData(prev, arg, sortingT);
                    prev = "";
                }
            }
        }

        return sortingT;
    }

    private static SortingData setAndGetSortingData(String prev, String arg, SortingData sortingData) {
        switch (prev) {
            case "-sortingType":
                sortingData.setSortingType(arg);
                break;
            case "-dataType":
                sortingData.setDataType(arg);
                break;
            case "-inputFile":
                sortingData.setInputFile(arg);
                break;
            case "-outputFile":
                sortingData.setOutputFile(arg);
                break;
            default:
                System.out.println("\"" + prev + "\" is not a valid parameter. It will be skipped.");
                break;
        }

        return sortingData;
    }

    private static void handleLong(SortingData sortingT) {
        List<Long> numbers = new ArrayList<>();
        if (sortingT.getInputFile().isEmpty()) {
            while (scanner.hasNext()) {
                String temp = scanner.next();
                try {
                    long number = Long.parseLong(temp);
                    numbers.add(number);
                } catch (Exception ex) {
                    System.out.printf("\"%s\" is not a long. It will be skipped.", temp);
                }
            }
        } else {
            try (Scanner scan = new Scanner(new File(sortingT.getInputFile()))) {
                while (scan.hasNext()) {
                    String temp = scanner.next();
                    if (canParseToLong(temp)) {
                        numbers.add(Long.parseLong(temp));
                    }
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        createMessageLongAndPrint(numbers, sortingT);
    }

    private static boolean canParseToLong(String value) {
        boolean result = true;
        try {
            Long.parseLong(value);
        } catch (Exception ex) {
            System.out.printf("\"%s\" is not a long. It will be skipped.", value);
            result = false;
        }
        return result;
    }

    private static void createMessageLongAndPrint(List<Long> numbers, SortingData sortingData) {
        StringBuilder result = new StringBuilder().append("Total numbers: ").append(numbers.size()).append(".\n");

        if (sortingData.getSortingType().equals(SortingType.NATURAL)) {
            numbers.sort(Long::compareTo);
            result.append(SORTED_DATA);
            for (long number : numbers) {
                result.append(number + " ");
            }
        } else if (sortingData.getSortingType().equals(SortingType.BY_COUNT)) {
            List<Long> numbersPrinted = new ArrayList<>();
            List<CustomSorting> customSortings = new ArrayList<>();
            for (long number : numbers) {
                if (!numbersPrinted.contains(number)) {
                    long appearances = numbers.stream().filter(x -> x.equals(number)).count();
                    customSortings.add(new CustomSorting(appearances, number, "", numbers.size()));
                    numbersPrinted.add(number);
                }
            }

            customSortings.sort(new CustomSortingLongComparator());

            for (CustomSorting customSorting : customSortings) {
                result.append(customSorting.getMessage()).append("\n");
            }
        }

        Helper.print(result.toString(), sortingData.getOutputFile());
    }

    private static void handleLine(SortingData sortingT) {
        List<String> lines = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        if (sortingT.getInputFile().isEmpty()) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } else {
            try (Scanner scan = new Scanner(new File(sortingT.getInputFile()))) {
                while (scan.hasNextLine()) {
                    lines.add(scan.nextLine());
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        result.append("Total lines: ");
        createMessageStringAndPrint(result, lines, sortingT);
    }

    private static void createMessageStringAndPrint(StringBuilder result, List<String> strings, SortingData sortingData) {
        result.append(strings.size()).append(".\n");
        if (sortingData.getSortingType().equals(SortingType.NATURAL)) {
            strings.sort(String::compareTo);
            result.append(SORTED_DATA);
            for (String line : strings) {
                result.append(line).append(" ");
            }
        } else if (sortingData.getSortingType().equals(SortingType.BY_COUNT)) {
            List<String> linesPrinted = new ArrayList<>();
            List<CustomSorting> customSortings = new ArrayList<>();
            for (String line : strings) {
                if (!linesPrinted.contains(line)) {
                    long appearances = strings.stream().filter(x -> x.equals(line)).count();
                    customSortings.add(new CustomSorting(appearances, 0L, line, strings.size()));
                    linesPrinted.add(line);
                }
            }

            customSortings.sort(new CustomSortingStringComparator());

            for (CustomSorting customSorting : customSortings) {
                result.append(customSorting.getMessage() + "\n");
            }
        }

        Helper.print(result.toString(), sortingData.getOutputFile());
    }

    private static void handleWord(SortingData sortingT) {
        List<String> words = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        if (sortingT.getInputFile().isEmpty()) {
            while (scanner.hasNext()) {
                words.add(scanner.next());
            }
        } else {
            try (Scanner scan = new Scanner(new File(sortingT.getInputFile()))) {
                while (scan.hasNext()) {
                    words.add(scan.next());
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        result.append("Total words: ");
        createMessageStringAndPrint(result, words, sortingT);
    }
}
