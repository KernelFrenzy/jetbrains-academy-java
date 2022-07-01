package com.xfrenzy47x.db;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    Logger logger = Logger.getLogger(Database.class.getName());

    private String JDBC = "jdbc:sqlite:";

    private Connection connection;
    private List<String> tables;
    private Statement statement;

    public Database(String db) {
        if (db.isEmpty()) return;
        connection = null;
        tables = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(JDBC + db);
            populateTables();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void initForTesting() throws SQLException {
        deleteDatabaseFiles();

        connection = DriverManager.getConnection(JDBC + "dbOne.db");
        statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS contacts (\n" +
                "\tcontact_id INTEGER PRIMARY KEY,\n" +
                "\tfirst_name TEXT NOT NULL,\n" +
                "\tlast_name TEXT NOT NULL,\n" +
                "\temail TEXT NOT NULL UNIQUE,\n" +
                "\tphone TEXT NOT NULL UNIQUE\n" +
                ");");
        statement.execute("CREATE TABLE IF NOT EXISTS groups (\n" +
                "   group_id INTEGER PRIMARY KEY,\n" +
                "   name TEXT NOT NULL\n" +
                ");");
        connection.close();

        connection = DriverManager.getConnection(JDBC + "dbTwo.db");
        statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS projects (\n" +
                "\tid integer PRIMARY KEY,\n" +
                "\tname text NOT NULL,\n" +
                "\tbegin_date text,\n" +
                "\tend_date text\n" +
                ");");
        connection.close();
    }

    private void deleteDatabaseFiles() {
        File firstFile = new File("dbOne.db");
        if (firstFile.exists()) {
            firstFile.delete();
        }

        File secondFile = new File("dbTwo.db");
        if (secondFile.exists()) {
            secondFile.delete();
        }
    }

    private void populateTables() {
        statement = null;
        try {
            statement = connection.createStatement();
            String sql = "SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%'";
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                tables.add(result.getString("name"));
            }

            result.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public DefaultTableModel executeQuery(String query) {
        statement = null;
        DefaultTableModel model = new DefaultTableModel();
        logger.log(Level.INFO, "The Query: {0}", query);
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();

            List<Object> columns = new ArrayList<>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                columns.add(metaData.getColumnName(i));
            }
            model.setColumnIdentifiers(columns.toArray());

            while (resultSet.next()) {
                List<Object> data = new ArrayList<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    data.add(resultSet.getObject(i));
                }
                model.addRow(data.toArray());
            }

            resultSet.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            model = null;
        }

        return model;
    }

    public List<String> getTables() {
        return tables;
    }
}
