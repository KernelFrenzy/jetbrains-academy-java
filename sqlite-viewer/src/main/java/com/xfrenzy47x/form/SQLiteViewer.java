package com.xfrenzy47x.form;

import com.xfrenzy47x.db.Database;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SQLiteViewer extends JFrame implements ActionListener {

    Database theDatabase;
    JTextField fileNameTextField = new JTextField();
    JButton openFileButton = new JButton();
    JComboBox<String> tablesComboBox = new JComboBox<>();
    JTextArea queryTextArea = new JTextArea();
    JButton executeQueryButton = new JButton();
    JTable table = new JTable();

    public SQLiteViewer() {
        try {
            theDatabase = new Database("");
            theDatabase.initForTesting();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 600);
        setTitle("SQLite Viewer");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new GridLayout(6, 1, 5, 5));

        fileNameTextField.setVisible(true);
        fileNameTextField.setName("FileNameTextField");
        add(fileNameTextField);

        openFileButton.setName("OpenFileButton");
        openFileButton.setText("Open");
        openFileButton.setVisible(true);
        openFileButton.addActionListener(this::actionPerformed);
        add(openFileButton);

        tablesComboBox.setName("TablesComboBox");
        tablesComboBox.setVisible(true);
        tablesComboBox.addActionListener(this::actionPerformed);
        add(tablesComboBox);

        queryTextArea.setName("QueryTextArea");
        queryTextArea.setRows(5);
        queryTextArea.setText("");
        queryTextArea.setEnabled(false);
        queryTextArea.setVisible(true);
        add(queryTextArea);

        executeQueryButton.setName("ExecuteQueryButton");
        executeQueryButton.setVisible(true);
        executeQueryButton.setText("Execute");
        executeQueryButton.setEnabled(false);
        executeQueryButton.addActionListener(this::actionPerformed);
        add(executeQueryButton);

        table.setName("Table");
        table.setVisible(true);
        add(table);

        repaint();
    }

    private void setComponentsState(boolean state) {
        queryTextArea.setEnabled(state);
        executeQueryButton.setEnabled(state);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (source == openFileButton) {
            String dbName = fileNameTextField.getText();
            theDatabase = new Database(dbName);
            List<String> tables = theDatabase.getTables();
            setComponentsState(false);
            tablesComboBox.removeAllItems();
            if (!tables.isEmpty()) {
                for (String stringTable : tables) {
                    tablesComboBox.addItem(stringTable);
                }
                tablesComboBox.setSelectedIndex(0);
                setComponentsState(true);
            } else {
                JOptionPane.showMessageDialog(new Frame(), "File doesn't exist!");
            }

        } else if (source == tablesComboBox && tablesComboBox.getItemCount() > 0) {
            String tableName = tablesComboBox.getSelectedItem().toString();
            String query = "SELECT * FROM " + tableName + ";";
            queryTextArea.setText(query);
        } else if (source == executeQueryButton && !queryTextArea.getText().isEmpty() && tablesComboBox.getItemCount() > 0) {
            String query = queryTextArea.getText();
            DefaultTableModel model = theDatabase.executeQuery(query);
            if (model == null) {
                JOptionPane.showMessageDialog(new Frame(), "Incorrect query!");
            } else {
                table.setModel(model);
            }
        }

        repaint();
    }
}
