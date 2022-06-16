package com.xfrenzy47x.controller;

import java.util.ArrayList;
import java.util.List;

public class DataController {
    private int currentPage;
    private int minPages;
    private int maxPages;
    private int dataPerPage;

    private List<String> data;

    public DataController(int dataPerPage) {
        this.dataPerPage = dataPerPage;
        resetController();
    }

    public void setData(List<String> data) {
        this.data = data;
        currentPage = 1;
        minPages = 1;
        maxPages = (data.size() / dataPerPage) + ((data.size() % dataPerPage) > 0 ? 1 : 0);

        printData();
    }

    public boolean hasData() {
        return !this.data.isEmpty();
    }

    public void resetController() {
        currentPage = 1;
        maxPages = 1;
        minPages = 1;
        this.data = new ArrayList<>();
    }

    private void printData() {
        data.stream().skip(dataPerPage * (currentPage-1)).limit(dataPerPage).forEach(System.out::println);
        System.out.println("---PAGE " + currentPage + " OF " + maxPages + "---");
    }

    public void updatePaging(String direction) {
        int tempCurrentPage = currentPage;
        currentPage = direction.equalsIgnoreCase("prev") ? currentPage-1 : currentPage;
        currentPage = direction.equalsIgnoreCase("next") ? currentPage+1 : currentPage;

        if (currentPage < minPages || currentPage > maxPages) {
            currentPage = tempCurrentPage;
            System.out.println("No more pages.");
            return;
        }

        printData();
    }
}
