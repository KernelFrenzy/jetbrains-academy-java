package com.xfrenzy47x.app.db;

import com.xfrenzy47x.app.models.Car;
import com.xfrenzy47x.app.models.Company;
import com.xfrenzy47x.app.models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    // JDBC driver name and database URL
    final String JDBC_DRIVER = "org.h2.Driver";
    String DB_URL = "jdbc:h2:./src/carsharing/db/";

    //  Database credentials
    final String USER = "";
    final String PASS = "";

    Connection conn;
    Statement statement;

    public Database(String dbName) {
        this.DB_URL += dbName;
        createTables();
    }

    private void init() throws SQLException, ClassNotFoundException {
        conn = null;
        statement = null;

        Class.forName(JDBC_DRIVER);

        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        conn.setAutoCommit(true);

        statement = conn.createStatement();
    }

    private void createTables() {
        try {
            init();

            String sql =
                    "CREATE TABLE IF NOT EXISTS COMPANY ( " +
                            "   ID INT NOT NULL AUTO_INCREMENT, " +
                            "   NAME VARCHAR(255) UNIQUE NOT NULL, " +
                            "   PRIMARY KEY (ID)" +
                            ");" +
                            "" +
                            "CREATE TABLE IF NOT EXISTS CAR ( " +
                            "   ID INT NOT NULL AUTO_INCREMENT, " +
                            "   NAME VARCHAR UNIQUE NOT NULL, " +
                            "   COMPANY_ID INT NOT NULL, " +
                            "   PRIMARY KEY (ID), " +
                            "   CONSTRAINT fk_car_company FOREIGN KEY (COMPANY_ID)" +
                            "   REFERENCES COMPANY(ID)" +
                            "); " +
                            "CREATE TABLE IF NOT EXISTS CUSTOMER ( " +
                            "   ID INT NOT NULL AUTO_INCREMENT, " +
                            "   NAME VARCHAR UNIQUE NOT NULL, " +
                            "   RENTED_CAR_ID INT NULL, " +
                            "   PRIMARY KEY (ID), " +
                            "   CONSTRAINT fk_customer_car FOREIGN KEY (RENTED_CAR_ID)" +
                            "   REFERENCES CAR(ID)" +
                            ");";
            statement.executeUpdate(sql);
            statement.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
        }
        finally {
            try{
                if(statement!=null) statement.close();
            } catch(SQLException ignored) {}
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    private boolean insertOrUpdate(String sql) {
        boolean result = true;
        try {
            init();
            statement.executeUpdate(sql);

            statement.close();
            conn.close();
        } catch(Exception se) {
            se.printStackTrace();
            result = false;
        }
        finally {
            try{
                if(statement!=null) statement.close();
            } catch(SQLException ignored) {
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            }
        }
        return result;
    }

    public void insertIntoCar(String carName, int companyId) {
        String sql = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES ('" + carName + "', " + companyId + ");";
        if (insertOrUpdate(sql)) {
            System.out.println("The car was created!");
        }
    }

    public void insertIntoCompany(String companyName) {
        String sql = "INSERT INTO COMPANY (NAME) VALUES ('" + companyName + "');";
        if (insertOrUpdate(sql)) {
            System.out.println("The company was created!");
        }
    }

    public void insertIntoCustomer(String name) {
        String sql = "INSERT INTO CUSTOMER (NAME, RENTED_CAR_ID) VALUES ('" + name + "', NULL);";
        if (insertOrUpdate(sql)) {
            System.out.println("The customer was created!");
        }
    }

    public boolean updateCustomer(int carId, int customerId) {
        String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = " + (carId == -1 ? "NULL" : carId) + " WHERE ID = " + customerId;
        return insertOrUpdate(sql);
    }

    public List<Company> readCompanies() {
        List<Company> companies = new ArrayList<>();
        try {
            init();
            String sql = "SELECT ID, NAME FROM COMPANY";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                companies.add(new Company(rs.getInt("ID"), rs.getString("NAME")));
            }
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement!=null) statement.close();
            } catch(SQLException ignored) {
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }

        return companies;
    }

    public List<Customer> readCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            init();
            String sql = "SELECT ID, NAME, RENTED_CAR_ID FROM CUSTOMER";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                Customer customer = new Customer();
                customer.id = rs.getInt("ID");
                customer.name = rs.getString("NAME");
                customer.rentedCarId = rs.getString("RENTED_CAR_ID") == null ? null : rs.getInt("RENTED_CAR_ID");
                customers.add(customer);
            }
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement!=null) statement.close();
            } catch(SQLException ignored) {
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }
        return customers;
    }

    public List<Car> readCars(int companyId, boolean getAvailableOnly) {

        List<Car> cars = new ArrayList<>();
        try {
            init();
            String sql = "SELECT ID, NAME, COMPANY_ID FROM CAR WHERE COMPANY_ID = " + companyId;
            if (getAvailableOnly) {
                sql += " AND ID NOT IN (SELECT RENTED_CAR_ID FROM CUSTOMER WHERE RENTED_CAR_ID IS NOT NULL);";
            }
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                cars.add(new Car(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("COMPANY_ID")));
            }
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement!=null) statement.close();
            } catch(SQLException ignored) {
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }

        return cars;
    }

    public Car readCar(int carId) {
        Car car = null;
        try {
            init();
            String sql = "SELECT ID, NAME, COMPANY_ID FROM CAR WHERE ID = " + carId;
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {
                car = (new Car(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("COMPANY_ID")));
            }
            rs.close();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement!=null) statement.close();
            } catch(SQLException se2) {
            }
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }

        return car;
    }
}
