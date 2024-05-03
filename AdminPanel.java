import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("ALL")
public class AdminPanel extends JFrame {

    //FONTS
    static Font font50bold = new Font("Courier New", Font.BOLD, 50);
    static Font font32bold = new Font("Courier New", Font.BOLD, 32);
    static Font font24bold = new Font("Courier New", Font.BOLD, 24);
    static Font font16plain = new Font("Courier New", Font.PLAIN, 16);
    static Font font16bold = new Font("Courier New", Font.BOLD, 16);
    static Font font14bold = new Font("Courier New", Font.BOLD, 14);

    static Font getFont16MTbold = new Font("Arial Rounded MT Bold", Font.PLAIN, 16);
    private final String line = "\n-----------------------------------------------------------------------------------------------\n";
    Color buttonColor = new Color(173, 255, 165);
    Color buttonColor1 = new Color(63, 248, 45);
    Color textColor = new Color(223, 15, 15);
    Border emptyBorder = BorderFactory.createEmptyBorder();
    Border border2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    JPanel panel;
    String selectedDatabase1 = "quiz_exam";
    private String selectedTable = ""; // Initialize with an empty string
    private JTextArea textArea;
    private JLabel timeLabel; // Declare the timeLabel at the class level
    private JLabel headingLabel1;
    private JLabel headingLabel2;
    private JLabel headingLabelTQ;

    AdminPanel() {
        setSize(750, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        adminLoginPanel();
        // Create a copyright label
        JLabel copyrightLabel = new JLabel("Powered by SV INFOTECH © 2023 LALIT KUMAR. All Rights Reserved");
        copyrightLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        copyrightLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add the copyright label to the frame
        getContentPane().add(copyrightLabel, BorderLayout.SOUTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AdminPanel();
        new Database();
    }

    private void adminLoginPanel() {

        // Create JLabel for Admin Login
        JLabel adminLabel = new JLabel("Admin Login");
        adminLabel.setFont(font32bold); // Set font size
        adminLabel.setBorder(BorderFactory.createCompoundBorder(emptyBorder, emptyBorder)); // Add border

        // Create JTextField for username input
        JTextField textField = new JTextField(10);
        textField.setFont(font16plain);
        textField.setBorder(BorderFactory.createCompoundBorder(emptyBorder, emptyBorder)); // Add border

        // Create JTextField for password input
        JPasswordField passwordField = new JPasswordField(10);
        passwordField.setFont(font16plain);
        passwordField.setBorder(BorderFactory.createCompoundBorder(emptyBorder, emptyBorder)); // Add border

        // Create JButton for submit action
        JButton button = new JButton("Submit");
        button.setFont(font24bold); // Set font size
        button.setForeground(Color.BLACK); // Set text color
        button.setBackground(buttonColor); // Set background color
        button.setBorder(BorderFactory.createCompoundBorder(emptyBorder, emptyBorder)); // Add border

        // Create a JPanel to hold the components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Set GridBagConstraints to center components
        gbc.insets = new Insets(5, 50, 10, 0); // Add some padding
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
        gbc.weightx = 0; // Allow horizontal resizing
        gbc.gridwidth = 1; // Set width to 2 cells
        gbc.ipady = 20; // Set height
        gbc.ipadx = 50; // Set width

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(adminLabel, gbc);

        gbc.insets = new Insets(5, 10, 10, 0); // Add some padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(textField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.ipady = 6;
        panel.add(button, gbc);

       /* button.addActionListener(e -> {
            String id = textField.getText();
            String pass = passwordField.getText();

            remove(panel);
            startTimer(); // Start the timer when entering the admin panel
            adminPanel();
            repaint();
            revalidate();
            new Database();
        });*/

        button.addActionListener(e -> {
            String id = textField.getText();
            String pass = passwordField.getText();

            if (id.isEmpty() || pass.isEmpty()) {
                System.out.println("Fill all fields");
            } else {

                if (Database.adminLoginValidation(id, pass)) {
                    remove(panel);
                    startTimer(); // Start the timer when entering the admin panel
                    adminPanel();
                    repaint();
                    revalidate();
                    new Database();
                    System.out.println("Welcome");
                    System.out.println("Login Successfully");
                } else {
                    System.out.println("Invalid Login Credential");
                }
            }
        });



        // Add the panel to the frame
        add(panel);
    }

    /*-------------------------------ADMIN-PANEL-------------------------------*/
    private void adminPanel() {
        final JButton[] clickedButton = {null};

        panel = new JPanel(new GridBagLayout());  //MAIN PANEL

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
        gbc.weightx = 0.1; // 20% width for the first column
        gbc.weighty = 1.0; // 100% height for both columns

        JPanel panel1 = new JPanel(new GridBagLayout()); // Nested GridBagLayout
        panel1.setBackground(buttonColor);

        // Create a common constraint for buttons to fit width and set the height
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.gridx = 0;
        buttonGbc.fill = GridBagConstraints.BOTH;
        buttonGbc.weightx = 1;
        buttonGbc.weighty = 1; // Ensure all components share the same vertical space

        // Create an array of button names
        String[] buttonNames = {"Create Database", "Databases Info", "Delete Database", "Create Table", "Table Info", "Delete Table", "Add Data", "Data Info", "Set Questions", "More"};

        // Create and add 7 buttons to panel1 with action listeners
        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.setFont(font16bold);
            button.setBackground(buttonColor);
            button.setBorder(BorderFactory.createCompoundBorder(emptyBorder, border2));
            button.setPreferredSize(new Dimension(button.getPreferredSize().width, 70)); //Set the preferred button
            // height

            // Add action listener to the button
            final int buttonNumber = i; // Store the button number in a final variable
            button.addActionListener(e -> {


                if (clickedButton[0] != null) {
                    clickedButton[0].setBackground(buttonColor);
                }

                // Set the background color of the clicked button
                button.setBackground(buttonColor1);

                // Update the clickedButton variable
                clickedButton[0] = (JButton) e.getSource();
                // Perform a different action for each button
                switch (buttonNumber) {
                    //CREATE DATABASE BUTTON
                    case 0 -> {
                        System.out.println("clicked on create database button");
                        textArea.setText("Creating a Database..." + line);
                        createDatabase();
                    }
                    //VIEW DATABASE BUTTON
                    case 1 -> {
                        System.out.println("clicked on show database button");
                        textArea.setText("Database Information..." + line);
                        showDatabase();
                    }
                    //DELETE DATABASE BUTTON
                    case 2 -> {
                        System.out.println("clicked delete database button");
                        textArea.setText("Deleting a Database..." + line);
                        deleteDatabaseCB();
                    }
                    //CREATE TABLE BUTTON
                    case 3 -> {
                        System.out.println("clicked create table button");
                        textArea.setText("Creating a Table..." + line);
                        createTable();
                    }
                    //VIEW TABLE BUTTON
                    case 4 -> {
                        System.out.println("clicked on view table button");
                        textArea.setText("Table Information..." + line);
                        showTableCB();
                    }
                    //DELETE TABLE BUTTON
                    case 5 -> {
                        System.out.println("Clicked on delete table button");
                        textArea.setText("Deleting a Table..." + line);
                        deleteTableCB();
                    }
                    //ADD DATA BUTTON
                    case 6 -> {
                        System.out.println("clicked on add data button");
                        textArea.setText("Inserting Data into Table..." + line);
                        addData();
                    }
                    //VIEW DATA BUTTON
                    case 7 -> {
                        System.out.println("clicked on view data button");
                        textArea.setText("Table Data Information..." + line);
                        showData();
                    }
                    //SET QUESTION BUTTON
                    case 8 -> {
                        System.out.println("clicked on set question button");
                        textArea.setText("Opening Panel..." + line);
                        setQuestionDirect();
                    }
                    //VIEW MORE BUTTON
                    case 9 -> {
                        System.out.println("clicked on more button");

                        textArea.setText("No More Action...."+line);
                    }

                    // Add cases for other buttons as needed
                    default -> System.out.println("default");
                    // Default action

                }

            });

            panel1.add(button, buttonGbc);
        }
        // Add panel1 to the first column
        panel.add(panel1, gbc);

        //RIGHT SIDE PANEL
        gbc.weightx = 0.9; // 90% width for the second column
        JPanel panel2 = new JPanel(new GridBagLayout());
        panel2.setBackground(Color.LIGHT_GRAY);

        // Create a panel to hold the headings
        JPanel headingPanel = new JPanel(new GridBagLayout());
        headingPanel.setBackground(Color.LIGHT_GRAY);


        // Create the second heading label
        JLabel headingLabel1 = new JLabel("Admin Panel");
        headingLabel1.setFont(font50bold);
        GridBagConstraints headingGbc1 = new GridBagConstraints();
        headingGbc1.gridx = 1;
        headingGbc1.gridy = 0;
        headingGbc1.weightx = 1;
        headingGbc1.anchor = GridBagConstraints.CENTER;
        headingPanel.add(headingLabel1, headingGbc1);

        JLabel headingLabel5 = new JLabel("Manage your Database");
        headingLabel5.setFont(font16bold);
        GridBagConstraints headingGbc5 = new GridBagConstraints();
        headingGbc5.gridx = 1;
        headingGbc5.gridy = 1;
        headingGbc5.weightx = 1;
        headingGbc5.anchor = GridBagConstraints.CENTER;
        headingPanel.add(headingLabel5, headingGbc5);

        // Create the time label
        timeLabel = new JLabel();
        timeLabel.setForeground(textColor);
        timeLabel.setFont(font14bold);
        GridBagConstraints timeLabelGbc = new GridBagConstraints();
        timeLabelGbc.gridx = 1; // Adjust the column index as needed
        timeLabelGbc.gridy = 3;
        timeLabelGbc.anchor = GridBagConstraints.SOUTH;
        headingPanel.add(timeLabel, timeLabelGbc);

        // Add the headingPanel to panel2
        GridBagConstraints panel2Gbc = new GridBagConstraints();
        panel2Gbc.gridx = 0;
        panel2Gbc.gridy = 0;
        panel2Gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2Gbc.weightx = 1.0;
        panel2Gbc.weighty = 0.1; // Adjust the weight as needed
        panel2.add(headingPanel, panel2Gbc);

        // Create a text area
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createCompoundBorder(border2, border2));
        textArea.setFont(getFont16MTbold);

        GridBagConstraints textAreaGbc = new GridBagConstraints();
        textAreaGbc.gridx = 0;
        textAreaGbc.gridy = 1;
        textAreaGbc.fill = GridBagConstraints.BOTH;
        textAreaGbc.weightx = 1.0;
        textAreaGbc.weighty = 0.9; // Adjust the weight as needed
        panel2.add(new JScrollPane(textArea), textAreaGbc);

        // Add panel2 to the second column
        panel.add(panel2, gbc);


        add(panel);
        setVisible(true);
    }

    private void createDatabase() {

        String dbName;
        while (true) {
            dbName = JOptionPane.showInputDialog(this, "Enter Database Name:", "Database Creation", JOptionPane.INFORMATION_MESSAGE);
            if (dbName == null) {
                textArea.append("Database creation canceled..." + line);
                break;
            } else if (!dbName.trim().isEmpty()) {
                if (Database.databaseExists(dbName)) {
                    textArea.append("'" + dbName + "' Database already exists...\nPlease try again..." + line);
                    int res = JOptionPane.showConfirmDialog(this, "Do you want view databases", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (res == JOptionPane.YES_OPTION) {
                        showDatabase();
                    }
                } else {
                    try {
                        Database.createDatabase(dbName);
                        textArea.append("'" + dbName + "' Database Successfully Created..." + line);
                        int res = JOptionPane.showConfirmDialog(this, "Do you want view databases", "Confirm", JOptionPane.YES_NO_OPTION);
                        if (res == JOptionPane.YES_OPTION) {
                            showDatabase();
                        }
                        break; // Exit the loop
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } else {
                textArea.append("Please Enter Valid Database Name" + line);
            }
        }

    }

    private void showDatabase() {

        // Call the showDatabase method to get the list of databases and the count
        StringBuilder databasesInfo = Database.showDatabase();

        // Count the number of databases by splitting the string on newline characters
        String[] lines = databasesInfo.toString().split("\n");
        int databaseCount = lines.length; // Subtract 1 to exclude the empty line at the end

        if (databaseCount > 0) {
            // Set the text of the JTextArea with the list of databases and the count
            textArea.append("Total '" + databaseCount + "' Databases on Server." + line + "Database name...\n" + databasesInfo + line);

        } else {
            // Inform the user if there are no databases
            textArea.append("No databases found..." + line);
        }

    }

    private void deleteDatabaseCB() {

        // Call the showDatabase method to get the list of databases
        StringBuilder databasesInfo = Database.showDatabase();

        // Split the database names into an array
        String[] databaseNames = databasesInfo.toString().split("\n");
        String selectedDatabase;
        // Display a dialog to select a database
        selectedDatabase = (String) JOptionPane.showInputDialog(this, "Select a database:", "Database Selection", JOptionPane.QUESTION_MESSAGE, null, databaseNames, databaseNames[0]);

        if (!(selectedDatabase == null)) {
            textArea.append("'" + selectedDatabase + "' Database selected for deletion..." + line);
            // Ask for confirmation before deleting the selected database
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want\nDelete '" + selectedDatabase + "' Database?", "Confirm Database " + "Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Database.deleteDatabase(selectedDatabase);
                    textArea.append("'" + selectedDatabase + "' Database " + "Successfully deleted..." + line);
                    int res = JOptionPane.showConfirmDialog(this, "Do you want view databases", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (res == JOptionPane.YES_OPTION) {
                        showDatabase();
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                textArea.append("Database deletion canceled." + line);
            }
        } else {
            textArea.append("Database deletion canceled." + line);
        }

    }

    private void createTable() {

        StringBuilder databasesInfo = Database.showDatabase();

        // Split the database names into an array
        String[] databaseNames = databasesInfo.toString().split("\n");
        String selectedDatabase;
        // Display a dialog to select a database
        selectedDatabase = (String) JOptionPane.showInputDialog(this, "Select a database:", "Database Selection", JOptionPane.QUESTION_MESSAGE, null, databaseNames, databaseNames[0]);
        if (!(selectedDatabase == null || selectedDatabase.isEmpty())) {
            textArea.append("'" + selectedDatabase + "' database selected..." + line);

            while (true) {
                // Input for table name
                String tableName = JOptionPane.showInputDialog(this, "Enter Table Name:");
                if (tableName != null) {
                    if (!tableName.isEmpty()) {
                        if (Database.tableExists(tableName)) {

                            textArea.append("'" + tableName + "' " + "Table already exist..." + line);

                        } else {

                            textArea.append("'" + tableName + "' table is creating..." + line);

                            int columnCount = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the number of " + "columns:"));


                            StringBuilder createTableQuery = new StringBuilder("CREATE TABLE " + tableName + "(");
                            for (int j = 0; j < columnCount; j++) {
                                // Input for column name
                                String columnName = JOptionPane.showInputDialog(this, "Enter Column " + (j + 1) + " Name:");
                                if (columnName == null || columnName.isEmpty()) {
                                    textArea.append("Please enter a valid column name." + line);
                                    return;
                                }


                                // Input for data type
                                String[] dataTypes = {"INT", "BIGINT", "VARCHAR(20)", "VARCHAR(50)", "VARCHAR(200)",
                                        "DATE", "BOOLEAN", "DECIMAL", "TEXT", "BLOB"}; // Add more data types as needed
                                String dataType = (String) JOptionPane.showInputDialog(this, "Select Data Type for Column " + columnName + ":", "Data Type Selection", JOptionPane.QUESTION_MESSAGE, null, dataTypes, dataTypes[0]);

                                if (dataType == null || dataType.isEmpty()) {
                                    textArea.append("Please select a valid data type." + line);
                                    break;
                                }

                                // Input for constraints
                                boolean primaryKey = JOptionPane.showConfirmDialog(this, "Is Column " + columnName + " a Primary Key?", "Primary Key", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                                boolean notNull = JOptionPane.showConfirmDialog(this, "Is Column " + columnName + " Not Null?", "Not Null", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                                boolean unique = JOptionPane.showConfirmDialog(this, "Is Column " + columnName + " Unique?", "Unique", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                                boolean autoIncrement = JOptionPane.showConfirmDialog(this, "Is Column " + columnName + " Auto Increment?", "Auto Increment", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                                // Build the column part of the query
                                createTableQuery.append(columnName).append(" ").append(dataType);
                                if (primaryKey) {
                                    createTableQuery.append(" PRIMARY KEY");
                                }
                                if (notNull) {
                                    createTableQuery.append(" NOT NULL");
                                }
                                if (unique) {
                                    createTableQuery.append(" UNIQUE");
                                }
                                if (autoIncrement) {
                                    createTableQuery.append(" AUTO_INCREMENT");
                                }

                                if (j < columnCount - 1) {
                                    createTableQuery.append(", ");
                                }
                            }
                            createTableQuery.append(");");

                            System.out.println(createTableQuery);
                            // Display the query in the text area (replace with your GUI code)
                            Database.createTable(selectedDatabase, String.valueOf(createTableQuery));
                            textArea.append("'" + tableName + "' Table Successfully created..." + line);

                            int res = JOptionPane.showConfirmDialog(this, "Do you want view column\nNewly created table '" + tableName + "'", "Show Column", JOptionPane.YES_NO_OPTION);
                            if (res == JOptionPane.YES_OPTION) {
                                // Get and display the columns of the selected table
                                String columnsInfo = Database.showTableColumn(tableName);
                                textArea.append("Newly created '" + tableName + "' Table Information...\n" + columnsInfo + line);
                            }

                            int res1 = JOptionPane.showConfirmDialog(this, "Do you want add data\nNewly created " + "table '" + tableName + "'", "Add Data", JOptionPane.YES_NO_OPTION);
                            if (res1 == JOptionPane.YES_OPTION) {
                                addTableData(tableName);
                            }
                            break;
                        }
                    } else {
                        textArea.append("Enter table name" + line);
                    }
                } else {
                    textArea.append("cancel table creation..." + line);
                    break;
                }
            }
        } else {
            textArea.append("cancel table creation..." + line);
        }
    }

    private void showTableCB() {

        // Call the showDatabase method to get the list of databases
        StringBuilder databasesInfo = Database.showDatabase();
        // Split the database names into an array
        String[] databaseNames = databasesInfo.toString().split("\n");
        String selectedDatabase;
        // Display a dialog to select a database
        selectedDatabase = (String) JOptionPane.showInputDialog(this, "Select a database:", "Database Selection", JOptionPane.QUESTION_MESSAGE, null, databaseNames, databaseNames[0]);

        if (selectedDatabase == null) {
            textArea.append("Cancel..." + line);
        } else {

            // Call the showTables method to get the list of tables and the count for the selected database
            StringBuilder tablesInfo = Database.showTables(selectedDatabase);

            String[] lines = tablesInfo.toString().split("\n");
            int tableCount = lines.length; // Subtract 1 to exclude the empty line at the end
            // Set the text of the JTextArea with the list of tables and the count
            textArea.append("'" + selectedDatabase + "' Database selected..." + line + "'" + selectedDatabase + "' " + "Database Information...\nTotal '" + tableCount + "' Table in '" + selectedDatabase + "' " + "Database..." + line);

            if (!tablesInfo.isEmpty()) {

                int res = JOptionPane.showConfirmDialog(this, "Do you want view tables", "View Tables", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    textArea.append("Tables are...\n" + tablesInfo + line);
                    String[] tableNames = Database.showTables(selectedDatabase).toString().split("\n");

                    // Display a dialog to select a table for deletion
                    String selectedTable = (String) JOptionPane.showInputDialog(this, "Select a table to view column:", "Table Selection", JOptionPane.QUESTION_MESSAGE, null, tableNames, tableNames[0]);

                    if (selectedTable == null) {
                        textArea.append("cancel view column" + line);

                    } else {
                        // Get and display the columns of the selected table
                        String columnsInfo = Database.showTableColumn(selectedTable);

                        textArea.append("'" + selectedTable + "' Table selected from Database '" + selectedDatabase + "'." + line + "'" + selectedTable + "' " + "Table Information...\n" + columnsInfo + line);

                        int res1 = JOptionPane.showConfirmDialog(this, "Do you to view Data", "View Data", JOptionPane.YES_NO_OPTION);
                        if (res1 == JOptionPane.YES_OPTION) {
                            textArea.append("'" + selectedTable + "' Table selected for view data." + line);
                            // Get and display the columns and data of the selected table
                            String tableInfo = Database.showData(selectedTable);

                            if (tableInfo.isEmpty()) {
                                textArea.append(tableInfo + line + "There is no data in the selected table '" + selectedTable + "'." + line);
                            } else {

                                textArea.append(tableInfo + line);
                            }
                        }
                    }
                } else {
                    textArea.append("cancel view column" + line);
                }
            } else {
                textArea.append("There are no tables in " + selectedDatabase + " Database" + line);
            }
        }
        System.out.println("Run show table method admin-panel");

    }

    private void deleteTableCB() {
        // Call the showDatabase method to get the list of databases
        StringBuilder databasesInfo = Database.showDatabase();

        // Split the database names into an array
        String[] databaseNames = databasesInfo.toString().split("\n");
        String selectedDatabase;

        // Display a dialog to select a database
        selectedDatabase = (String) JOptionPane.showInputDialog(this, "Select a database:", "Database Selection", JOptionPane.QUESTION_MESSAGE, null, databaseNames, databaseNames[0]);


        textArea.append("'" + selectedDatabase + "' Database selected..." + line);
        if (!(selectedDatabase == null)) {
            // Call the showTables method to get the list of tables for the selected database
            String[] tableNames = Database.showTables(selectedDatabase).toString().split("\n");

            // Display a dialog to select a table for deletion
            String selectedTable = (String) JOptionPane.showInputDialog(this, "Select a table to delete:", "Table Selection", JOptionPane.QUESTION_MESSAGE, null, tableNames, tableNames[0]);

            textArea.append("'" + selectedTable + "' Table selected for deletion..." + line);

            // Ask for confirmation before deleting the selected table
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete\nTable '" + selectedTable + "' from '" + selectedDatabase + "' Database", "Confirm Table Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                Database.deleteTable(selectedTable);
                textArea.append("Table '" + selectedTable + "' deletion successfully.\nFrom the '" + selectedDatabase + "' " + "Database." + line);
            }
        } else {
            textArea.append("cancel deletion" + line);
        }

    }

    private void addTableData(String selectedTable) {

        if (selectedTable != null) {
            textArea.append("Inserting data in newly create table '" + selectedTable + "'" + line);

            // Call the showTableColumnAD method to get the list of columns for the selected table
            String columnsInfo = Database.showTableColumnAD(selectedTable);
            String[] columnNames = columnsInfo.split("\n");

            String[] inputData = new String[columnNames.length];

            int i;
            for (i = 0; i < columnNames.length; i++) {
                String columnName = columnNames[i];
                // Display an input dialog for data
                inputData[i] = JOptionPane.showInputDialog(this, "Enter data for column '" + columnName + "':");
                if (inputData[i] == null) {
                    textArea.append("Data insertion cancel..." + line);
                    break;
                }
            }

            if (inputData[0] != null) {
                // Add the data to the selected table
                boolean dataAdded = Database.addData(selectedTable, columnNames, inputData);


                if (dataAdded) {
                    textArea.append("Data successfully added in newly created table '" + selectedTable + "'." + line);

                    int res = JOptionPane.showConfirmDialog(this, "Do you want to view data", "View Data", JOptionPane.YES_NO_OPTION);
                    if (res == JOptionPane.YES_OPTION) {
                        // Get and display the columns and data of the selected table
                        String tableInfo = Database.showData(selectedTable);
                        textArea.append(tableInfo + line);
                    }
                } else {
                    textArea.append("Data Insertion Failed..." + line + "Data already exists in selected table '" + selectedTable + "'." + line);
                    int res = JOptionPane.showConfirmDialog(this, "Do you want to view data", "View Data", JOptionPane.YES_NO_OPTION);
                    if (res == JOptionPane.YES_OPTION) {
                        // Get and display the columns and data of the selected table
                        String tableInfo = Database.showData(selectedTable);
                        textArea.append(tableInfo + line);
                    }
                }
            }
        } else {
            textArea.append("Data insertion cancel..." + line);
        }
    }

    private void addData() {
        // Call the showDatabase method to get the list of databases
        StringBuilder databasesInfo = Database.showDatabase();

// Split the database names into an array
        String[] databaseNames = databasesInfo.toString().split("\n");
        String selectedDatabase;

// Display a dialog to select a database
        selectedDatabase = (String) JOptionPane.showInputDialog(this, "Select a database:", "Database Selection", JOptionPane.QUESTION_MESSAGE, null, databaseNames, databaseNames[0]);

        if (selectedDatabase != null && !selectedDatabase.isEmpty()) {
            textArea.append("'" + selectedDatabase + "' Database selected..." + line);

            // Call the showTables method to get the list of tables for the selected database
            String[] tableNames = Database.showTables(selectedDatabase).toString().split("\n");

            // Display a dialog to select a table for data insertion
            String selectedTable = (String) JOptionPane.showInputDialog(this, "Select a table to insert data into:", "Table Selection", JOptionPane.QUESTION_MESSAGE, null, tableNames, tableNames[0]);

            if (selectedTable != null) {
                textArea.append("'" + selectedTable + "' Table " + "selected for Inserting Data." + line);

                // Call the showTableColumnAD method to get the list of columns for the selected table
                String columnsInfo = Database.showTableColumnAD(selectedTable);
                String[] columnNames = columnsInfo.split("\n");

                int numRowsToInsert;
                String numRowsInput;

                do {
                    numRowsInput = JOptionPane.showInputDialog(this, "How many rows of data\nDo you want to insert?");
                    // Check if the user canceled or entered invalid input
                    if (numRowsInput != null) {
                        if (numRowsInput.isEmpty()) {
                            textArea.append("Please enter the number of rows to insert." + line);
                        } else {
                            try {
                                numRowsToInsert = Integer.parseInt(numRowsInput);
                                break; // Break the loop if valid input
                            } catch (NumberFormatException e) {
                                textArea.append("Invalid input for the number of rows.\nPlease enter a valid number." + line);
                            }
                        }
                    } else {
                        textArea.append("Data insertion canceled." + line);
                        return;
                    }
                } while (true);

                for (int rowNum = 0; rowNum < numRowsToInsert; rowNum++) {
                    String[] inputData = new String[columnNames.length];

                    while (true) {
                        for (int i = 0; i < columnNames.length; i++) {
                            String columnName = columnNames[i];
                            // Display an input dialog for data
                            inputData[i] = JOptionPane.showInputDialog(this, "Enter data for column '" + columnName + "':");
                            if (inputData[i] == null) {
                                textArea.append("Data insertion canceled..." + line);
                                return;
                            }
                        }


                        // Add the data to the selected table
                        boolean dataAdded = Database.addData(selectedTable, columnNames, inputData);

                        if (dataAdded) {
                            textArea.append("Data successfully added in selected table '" + selectedTable + "'." + line);
                            break;
                        } else {
                            textArea.append("Data Insertion Failed...\nData already exists in selected table '" + selectedTable + "'." + line);
                        }
                    }
                }

                int res = JOptionPane.showConfirmDialog(this, "Do you want to view data", "View Data", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    // Get and display the columns and data of the selected table
                    String tableInfo = Database.showData(selectedTable);
                    textArea.append(tableInfo + line);
                }
            } else {
                textArea.append("Data insertion canceled..." + line);
            }
        } else {
            textArea.append("Data insertion canceled..." + line);
        }

    }


    private void showData() {
        // Call the showDatabase method to get the list of databases
        StringBuilder databasesInfo = Database.showDatabase();

        // Split the database names into an array
        String[] databaseNames = databasesInfo.toString().split("\n");
        String selectedDatabase;

        // Display a dialog to select a database
        selectedDatabase = (String) JOptionPane.showInputDialog(this, "Select a database", "Database Selection for " + "view data", JOptionPane.QUESTION_MESSAGE, null, databaseNames, databaseNames[0]);


        if (!(selectedDatabase == null)) {
            textArea.append("'" + selectedDatabase + "' Database selected..." + line);
            // Call the showTables method to get the list of tables for the selected database
            String[] tableNames = Database.showTables(selectedDatabase).toString().split("\n");

            // Display a dialog to select a table for deletion
            String selectedTable = (String) JOptionPane.showInputDialog(this, "Select a table to view data", "Table " + "Selection for view data", JOptionPane.QUESTION_MESSAGE, null, tableNames, tableNames[0]);

            textArea.append("'" + selectedTable + "' Table selected for view data." + line);

            // Get and display the columns and data of the selected table
            String tableInfo = Database.showData(selectedTable);

            if (tableInfo.isEmpty()) {
                textArea.append(tableInfo + line + "There is no data in the selected table '" + selectedTable + "'." + line);
            } else {

                textArea.append("'" + selectedTable + "' Table selected for view data.\n" + tableInfo + line);
            }
        } else {
            textArea.append("Cancel..." + line);
        }
    }

    private void setQuestion() {
        // textArea.append("Select Database and Table..." + line);
        // Call the showDatabase method to get the list of databases
        StringBuilder databasesInfo = Database.showDatabase();

        // Split the database names into an array
        String[] databaseNames = databasesInfo.toString().split("\n");
        while (true) {
            // Display a dialog to select a database
            // Initialize with an empty string
            String selectedDatabase = (String) JOptionPane.showInputDialog(this, "Select a database:", "Database Selection for " + "Set Question", JOptionPane.QUESTION_MESSAGE, null, databaseNames, databaseNames[0]);


            if (!(selectedDatabase == null)) {
                textArea.append("'" + selectedDatabase + "' Database selected..." + line);
                // Call the showTables method to get the list of tables for the selected database
                String[] tableNames = Database.showTables(selectedDatabase).toString().split("\n");

                // Display a dialog to select a table for deletion
                selectedTable = (String) JOptionPane.showInputDialog(this, "Select a table:", "Table Selection for Set " + "Question", JOptionPane.QUESTION_MESSAGE, null, tableNames, tableNames[0]);

                if (selectedTable != null) {

                    if (selectedTable.isEmpty()) {
                        textArea.append("No Table in selected Database '" + selectedDatabase + "'.\nPlease try " + "again..." + line);
                    } else {
                        textArea.append("'" + selectedTable + "' " + "Table selected for add question..." + line);

                        remove(panel);
                        setQuestionPanel();
                        break;
                    }

                } else {
                    textArea.append("Cancel..." + line);
                    break;
                }

            } else {
                textArea.append("Cancel..." + line);
                break;
            }
        }
    }

    /*-------------------------------SET-QUESTION-PANEL-------------------------------*/
    private void setQuestionPanel() {
        repaint();
        revalidate();
        final JButton[] clickedButton = {null};
        //MAIN PANEL
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
        gbc.weighty = 1.0; // 100% height for both columns

        //RIGHT SIDE PANEL
        gbc.weightx = 0.9; // 90% width for the second column
        JPanel panel2 = new JPanel(new GridBagLayout());
        panel2.setBackground(Color.LIGHT_GRAY);

        // Create a panel to hold the headings
        JPanel headingPanel = new JPanel(new GridBagLayout());
        headingPanel.setBackground(Color.LIGHT_GRAY);

        // Create the first heading label
        JLabel headingLabel0 = new JLabel("Set Question Paper");
        headingLabel0.setForeground(textColor);
        headingLabel0.setFont(font14bold);
        GridBagConstraints headingGbc0 = new GridBagConstraints();
        headingGbc0.gridx = 0;
        headingGbc0.gridy = 0;
        headingGbc0.anchor = GridBagConstraints.NORTH;
        headingPanel.add(headingLabel0, headingGbc0);

        // Create the first heading label
        headingLabel1 = new JLabel(" Database '" + selectedDatabase1 + "'");
        headingLabel1.setForeground(textColor);
        headingLabel1.setFont(font14bold);
        GridBagConstraints headingGbc1 = new GridBagConstraints();
        headingGbc1.gridx = 0;
        headingGbc1.gridy = 0;
        headingGbc1.anchor = GridBagConstraints.WEST;
        headingPanel.add(headingLabel1, headingGbc1);

        headingLabel2 = new JLabel(" Table '" + selectedTable + "'");
        //headingLabel2.setForeground(textColor);
        headingLabel2.setFont(font14bold);
        GridBagConstraints headingGbc2 = new GridBagConstraints();
        headingGbc2.gridx = 0;
        headingGbc2.gridy = 0;
        headingGbc2.anchor = GridBagConstraints.SOUTHWEST;
        headingPanel.add(headingLabel2, headingGbc2);


        // Create the time label
        timeLabel = new JLabel();
        timeLabel.setForeground(textColor);
        timeLabel.setFont(font14bold);
        GridBagConstraints timeLabelGbc = new GridBagConstraints();
        timeLabelGbc.gridx = 0; // Adjust the column index as needed
        timeLabelGbc.gridy = 2;
        timeLabelGbc.anchor = GridBagConstraints.WEST;
        headingPanel.add(timeLabel, timeLabelGbc);


        headingLabelTQ = new JLabel(" Total Question '" + totalQS() + "'");
        headingLabelTQ.setFont(font14bold);
        GridBagConstraints headingGbcTQ = new GridBagConstraints();
        headingGbcTQ.gridx = 0;
        headingGbcTQ.gridy = 1;
        headingGbcTQ.anchor = GridBagConstraints.SOUTHWEST;
        headingPanel.add(headingLabelTQ, headingGbcTQ);

        // Create the second heading label
        JLabel headingLabel4 = new JLabel("Admin Panel");
        headingLabel4.setFont(font50bold);
        GridBagConstraints headingGbc4 = new GridBagConstraints();
        headingGbc4.gridx = 1;
        headingGbc4.gridy = 0;
        headingGbc4.weightx = 1;
        headingGbc4.anchor = GridBagConstraints.CENTER;
        headingPanel.add(headingLabel4, headingGbc4);

        // Create the second heading label
        JLabel headingLabel5 = new JLabel("Manage your Question Table");
        headingLabel5.setFont(font16bold);
        GridBagConstraints headingGbc5 = new GridBagConstraints();
        headingGbc5.gridx = 1;
        headingGbc5.gridy = 1;
        headingGbc5.weightx = 1;
        headingGbc5.anchor = GridBagConstraints.CENTER;
        headingPanel.add(headingLabel5, headingGbc5);


        // Add the headingPanel to panel2
        GridBagConstraints panel2Gbc = new GridBagConstraints();
        panel2Gbc.gridx = 0;
        panel2Gbc.gridy = 0;
        panel2Gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2Gbc.weightx = 1.0;
        panel2Gbc.weighty = 0.1; // Adjust the weight as needed
        panel2.add(headingPanel, panel2Gbc);

        // Create a text area
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createCompoundBorder(border2, border2));
        textArea.setFont(getFont16MTbold);

        GridBagConstraints textAreaGbc = new GridBagConstraints();
        textAreaGbc.gridx = 0;
        textAreaGbc.gridy = 1;
        textAreaGbc.fill = GridBagConstraints.BOTH;
        textAreaGbc.weightx = 1.0;
        textAreaGbc.weighty = 0.9; // Adjust the weight as needed
        panel2.add(new JScrollPane(textArea), textAreaGbc);

        // Add panel2 to the second column
        panel.add(panel2, gbc);

        JPanel panel1 = new JPanel(new GridBagLayout()); // Nested GridBagLayout
        panel1.setBackground(buttonColor);


        gbc.weightx = 0.1; // 20% width for the first column

        // Create a common constraint for buttons to fit width and set the height
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.gridx = 0;
        buttonGbc.fill = GridBagConstraints.BOTH;
        buttonGbc.weightx = 1;
        buttonGbc.weighty = 1; // Ensure all components share the same vertical space

        // Create an array of button names
        String[] buttonNames = {"New Table", "Change Table", "Add Question", "Delete Question", "View Questions", "Delete Table", "Set Database", " Results", "Back"};

        // Create and add 7 buttons to panel1 with action listeners
        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.setFont(font16bold);
            button.setBackground(buttonColor);
            button.setBorder(BorderFactory.createCompoundBorder(emptyBorder, border2));
            button.setPreferredSize(new Dimension(button.getPreferredSize().width, 70)); //Set the preferred button
            // height

            // Add action listener to the button
            final int buttonNumber = i; // Store the button number in a final variable
            button.addActionListener(e -> {

                if (clickedButton[0] != null) {
                    clickedButton[0].setBackground(buttonColor);
                }

                // Set the background color of the clicked button
                button.setBackground(buttonColor1);

                // Update the clickedButton variable
                clickedButton[0] = (JButton) e.getSource();
                // Perform a different action for each button
                // Perform a different action for each button
                switch (buttonNumber) {
                    case 0 -> {
                        System.out.println("clicked on change database button");
                        textArea.setText("Now, You Working on.......\nDatabase '" + selectedDatabase1 + "' and Table " + "'" + selectedTable + "'." + line);
                        newTable();
                    }
                    case 1 -> {
                        System.out.println("clicked on change table button");
                        textArea.setText("Changing a Table on a Working Database '" + selectedDatabase1 + "'." + line);
                        changeTable();
                    }
                    case 2 -> {
                        System.out.println("clicked on add question button");
                        textArea.setText("Question insert in Table '" + selectedTable + "'." + line);
                        addQuestion();
                    }
                    case 3 -> {
                        System.out.println("clicked on delete question button");
                        textArea.setText("Delete Question...." + line);
                        deleteQuestionQSP();
                    }
                    case 4 -> {
                        System.out.println("clicked on show question button");
                        textArea.setText("Show Question..." + line);
                        showQuestion();
                    }
                    case 5 -> {
                        System.out.println("clicked on delete table button");
                        textArea.setText("Deleting Table..." + line);
                        deleteTableQSP();
                    }
                    case 6 -> {
                        System.out.println("clicked on set database button");
                        //setDatabase();
                    }
                    case 7 -> {
                        System.out.println("clicked on results button");
                        showresult();
                    }
                    case 8 -> {
                        remove(panel);
                        adminPanel();
                        System.out.println("clicked on back button");
                    }

                    // Add cases for other buttons as needed
                    default -> System.out.println("default");
                    // Default action
                }
            });

            panel1.add(button, buttonGbc);
        }

        // Add panel1 to the first column
        panel.add(panel1, gbc);


        add(panel);
        setVisible(true);

    }

    private void setQuestionDirect() {
        textArea.append("Select Database and Table..." + line);
        while (true) {
            // Display a dialog to select a database


            String[] tableNames = Database.showTables(selectedDatabase1).toString().split("\n");

            // Display a dialog to select a table for deletion
            selectedTable = (String) JOptionPane.showInputDialog(this, "Select a table:", "Table Selection for Set " + "Question", JOptionPane.QUESTION_MESSAGE, null, tableNames, tableNames[0]);

            if (selectedTable != null) {

                if (selectedTable.isEmpty()) {
                    textArea.append("No Table in selected Database '" + selectedDatabase1 + "'.\nPlease try " + "again..." + line);
                } else {
                    textArea.append("'" + selectedTable + "' " + "Table selected for add question..." + line);

                    remove(panel);
                    setQuestionPanel();
                    headingLabel1.setText(" Database '" + selectedDatabase1 + "'");
                    headingLabel2.setText(" Table '" + selectedTable + "'");
                    break;
                }

            } else {
                textArea.append("Cancel..." + line);
                break;
            }

        }
    }


    private int totalQS() {
        //textArea.append("Total Question " + (TBC));
        return Database.rowCount(selectedTable);
    }

    private void newTable() {
        textArea.append("Creating new table, only use for add questions." + line);
        do {
            String tbName = JOptionPane.showInputDialog(this, "Enter Table Name");


            if (tbName != null) {
                // Check if the table already exists
                if (Database.tableExists(tbName)) {
                    textArea.append("Table '" + tbName + "' already exists." + line);

                } else {
                    if (!tbName.trim().isEmpty()) {
                        // Table does not exist, so create it
                        String query = "CREATE TABLE " + tbName + "(QuizNumber INT AUTO_INCREMENT PRIMARY KEY, " + "Question VARCHAR(255) NOT NULL, QuizOption1 VARCHAR(100) NOT NULL, QuizOption2 " + "VARCHAR(100) NOT NULL, QuizOption3 VARCHAR(100) NOT NULL, QuizOption4 VARCHAR(100) " + "NOT NULL, Answer VARCHAR(100) NOT NULL)";
                        System.out.println(query);
                        Database.createTable(selectedDatabase1, query);
                        textArea.append("Table '" + tbName + "' created successfully." + line);
                        break;
                    } else {
                        textArea.append("Please Enter Table Name" + line);
                    }
                }
            } else {
                textArea.append("Creation Cancel..." + line);
                break;
            }
        } while (true);
        headingLabelTQ.setText(" Total Question '" + totalQS() + "'");
    }

    private void changeTable() {
        // Call the showDatabase method to get the list of databases
        StringBuilder databasesInfo = Database.showDatabase();

        String tbName = selectedTable;

        if (selectedDatabase1 != null) {
            textArea.append("Your working Database '" + selectedDatabase1 + "'." + line);
            // Call the showTables method to get the list of tables for the selected database
            String[] tableNames = Database.showTables(selectedDatabase1).toString().split("\n");

            // Display a dialog to select a table for deletion
            String selectedTable1 = (String) JOptionPane.showInputDialog(this, "Select a table:", "Table Selection " + "for Change Table", JOptionPane.QUESTION_MESSAGE, null, tableNames, tableNames[0]);

            if (selectedTable1 != null) {
                selectedTable = selectedTable1; // Update the selected table name
                textArea.append("You are selecting Table '" + selectedTable + "'." + line);
                // Update the heading labels with the selected database and table names
                headingLabel1.setText(" Database '" + selectedDatabase1 + "'");
                headingLabel2.setText(" Table '" + selectedTable + "'");
                headingLabelTQ.setText(" Total Question '" + totalQS() + "'");

                textArea.append("Table '" + tbName + "' successfully change in Table '" + selectedTable + "'." + line);
                textArea.append("Now your working on...\nDatabase '" + selectedDatabase1 + "' and Table '" + selectedTable + "'." + line);
                int res = JOptionPane.showConfirmDialog(this, "You want to view about table '" + selectedTable + "'", "View About Table", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    tableInfo2(selectedTable);
                }
            } else {
                textArea.append("Cancel..." + line);
            }
        }
    }

    private void tableInfo2(String selectedDatabase) {
        if (!(selectedDatabase == null)) {
            String columnsInfo = Database.showTableColumn(selectedTable);
            textArea.append("Table Information...\n" + columnsInfo + line);
        } else {
            textArea.append("Cancel insertion..." + line);
        }

        int res = JOptionPane.showConfirmDialog(this, "Do you want view Table Data", "View Data", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {

            // Get and display the columns and data of the selected table
            String tableInfo = Database.showData(selectedTable);

            if (tableInfo.isEmpty()) {
                textArea.append("Table Information...\n" + tableInfo + line + "There is no data in the selected table" + " '" + selectedTable + "'." + line);
            } else {

                textArea.append("Table Information...\n" + tableInfo + line);
            }
        }
    }

    private void addQuestion() {

        while (true) {
            String title = JOptionPane.showInputDialog(this, "Title of Question Paper");


            if (title == null) {
                textArea.append("Cancel...");
                break;
            } else if (!title.isEmpty()) {

                textArea.append("'" + title + "' Question Paper\n");
// Table data count
                int TBC = Database.rowCount(selectedTable);
                textArea.append("Currently '" + TBC + "' Questions." + line);


                int num = 0; // Initialize num to 0
                boolean isValidInput1 = false;

                while (!isValidInput1) {
                    try {
                        String numInput = JOptionPane.showInputDialog(this, "Enter an integer:");
                        if (numInput == null) {
                            // User clicked Cancel, exit the loop
                            textArea.append("Cancel..." + line);
                            break;
                        }
                        num = Integer.parseInt(numInput);
                        isValidInput1 = true; // Valid integer input, exit the loop
                    } catch (NumberFormatException e) {
                        // Invalid input, display an error message and continue the loop
                        // JOptionPane.showMessageDialog(this, "Please enter a valid integer", "Error",JOptionPane.ERROR_MESSAGE);
                        textArea.append("Please enter a valid integer" + line);
                    }
                }

                // num = Integer.parseInt(JOptionPane.showInputDialog(this, "How many questions to add"));

                for (int n = 0; n < num; n++) {
                    TBC = TBC + 1;

                    boolean isValidInput = false;

                    while (!isValidInput) {
                        // Create an array of text fields for user input
                        JTextField[] inputFields = new JTextField[6];

                        // Create the panel to hold the input fields
                        JPanel inputPanel = new JPanel(new GridLayout(10, 2));

                        // Add labels and text fields to the panel
                        inputPanel.add(new JLabel("Question: " + TBC));
                        inputFields[0] = new JTextField(20);
                        inputPanel.add(inputFields[0]);

                        inputPanel.add(new JLabel("Option 1:"));
                        inputFields[1] = new JTextField(20);
                        inputPanel.add(inputFields[1]);

                        inputPanel.add(new JLabel("Option 2:"));
                        inputFields[2] = new JTextField(20);
                        inputPanel.add(inputFields[2]);

                        inputPanel.add(new JLabel("Option 3:"));
                        inputFields[3] = new JTextField(20);
                        inputPanel.add(inputFields[3]);

                        inputPanel.add(new JLabel("Option 4:"));
                        inputFields[4] = new JTextField(20);
                        inputPanel.add(inputFields[4]);

                        inputPanel.add(new JLabel("Answer:"));
                        inputFields[5] = new JTextField(20);
                        inputPanel.add(inputFields[5]);

                        // Create a custom JOptionPane with the input panel
                        int result = JOptionPane.showConfirmDialog(this, inputPanel, "Enter Data", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        // Check if the user clicked OK
                        if (result == JOptionPane.OK_OPTION) {
                            // Retrieve the values entered by the user
                            String[] userInput = new String[6];
                            for (int i = 0; i < 6; i++) {
                                userInput[i] = inputFields[i].getText();
                            }

                            // Check if any input field is empty
                            boolean anyEmpty = false;
                            for (String input : userInput) {
                                if (input.isEmpty()) {
                                    anyEmpty = true;
                                    break;
                                }
                            }

                            if (anyEmpty) {
                                // Display an error message and continue the loop
                                //JOptionPane.showMessageDialog(this, "Please enter data in all fields", "Error",JOptionPane.ERROR_MESSAGE);
                                textArea.append("Please enter data in all fields" + line);
                            } else {
                                // Display the entered values
                                StringBuilder message = new StringBuilder("Question: " + (TBC) + line);
                                String data = null;
                                for (int i = 0; i < 6; i++) {
                                    if (i == 0) {
                                        data = String.valueOf(message.append("Qs").append(n + 1).append(". ").append(userInput[i]).append("\n"));
                                    } else {
                                        data = String.valueOf(message.append("(").append(i + 1).append(")").append(".  ").append(userInput[i]).append("\n"));
                                    }
                                }
                                textArea.append(data + line);
                                // Extract options from userInput and add to the database
                                String[] options = {userInput[0], userInput[1], userInput[2], userInput[3], userInput[4], userInput[5]};
                                Database.addQuestion(selectedTable, options);
                                textArea.append("Question inserted successfully" + line);
                                headingLabelTQ.setText("Total Question '" + totalQS() + "'");
                                isValidInput = true; // Exit the loop

                            }

                        } else {
                            // The user clicked Cancel, so exit the loop without adding a question
                            textArea.append("Cancel..." + line);
                            break;
                        }

                    }
                }
                break;
            } else {
                System.out.println("enter");
                textArea.append("Enter Title of Question Paper" + line);
            }
        }
        textArea.append("Total Question '" + (totalQS()) + "'." + line);
    }

    private void deleteQuestionQSP() {
        //textArea.append("'" + selectedDatabase1 + "' Database selected..." + line);
        if (!(selectedDatabase1 == null)) {

            // Call the showTables method to get the list of tables for the selected database
            String[] tableNames = Database.showTables(selectedDatabase1).toString().split("\n");

            // Display a dialog to select a table for deletion
            String selectedTable = (String) JOptionPane.showInputDialog(this, "Which table's question will you delete?", "Table Selection", JOptionPane.QUESTION_MESSAGE, null, tableNames, tableNames[0]);
            if (!(selectedTable == null)) {
                textArea.append("Table Name '" + selectedTable + "'\n'" + selectedTable + "' selected for deletion.." + "." + line);

                if (totalQS() != 0) {

                    do {
                        // Get the question number to delete (you can replace this with your input logic)
                        String strId = JOptionPane.showInputDialog(this, "Enter Question Number to Delete");
                        if (strId != null) {
                            if (!strId.trim().isEmpty()) {
                                try {
                                    int id = Integer.parseInt(strId);
                                    int rowsDeleted = Database.deleteDataById(selectedTable, id);
                                    if (rowsDeleted > 0) {
                                        textArea.append("Question Number '" + id + "' deleted successfully." + line);
                                        headingLabelTQ.setText(" Total Question '" + totalQS() + "'");
                                    } else {
                                        textArea.append("Question Number '" + id + "' not found." + line);
                                    }
                                } catch (NumberFormatException e) {
                                    textArea.append("Please enter a valid integer." + line);
                                }
                            } else {
                                textArea.append("Enter Question Number." + line);
                            }
                        } else {
                            textArea.append("Deletion Cancel" + line);
                            break;
                        }
                    } while (true);
                } else {
                    textArea.append("No Question in selected Table '" + selectedTable + "'." + line);
                }
            } else {
                textArea.append("Deletion Cancel..." + line);
            }
        } else {
            textArea.append("Deletion Cancel..." + line);
        }
        headingLabelTQ.setText(" Total Question '" + totalQS() + "'");
    }

    private void showQuestion() {

        int res = JOptionPane.showConfirmDialog(this, "View Question", "View Questions", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            // Get and display the columns and data of the selected table
            String tableInfo = Database.showQuestionData(selectedTable);
            textArea.append(tableInfo + line);
        }
        headingLabelTQ.setText(" Total Question '" + totalQS() + "'");
    }

    private void showresult() {

        int res = JOptionPane.showConfirmDialog(this, "View Result", "View Questions", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            String tableInfo = Database.showResult(selectedTable);
            textArea.append(tableInfo + line);
        }
    }

    private void deleteTableQSP() {

        //textArea.append("'" + selectedDatabase1 + "' Database selected..." + line);
        if (!(selectedDatabase1 == null)) {
            // Call the showTables method to get the list of tables for the selected database
            String[] tableNames = Database.showTables(selectedDatabase1).toString().split("\n");

            // Display a dialog to select a table for deletion
            String selectedTable = (String) JOptionPane.showInputDialog(this, "Select a table to delete:", "Table Selection", JOptionPane.QUESTION_MESSAGE, null, tableNames, tableNames[0]);

            textArea.append("'" + selectedTable + "' Table selected for deletion..." + line);

            // Ask for confirmation before deleting the selected table
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete\nTable '" + selectedTable + "' from '" + selectedDatabase1 + "' Database", "Confirm Table Deletion", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                Database.deleteTable(selectedTable);
                textArea.append("Table '" + selectedTable + "' deletion successfully.\nFrom the '" + selectedDatabase1 + "' " + "Database." + line);
                headingLabelTQ.setText(" Total Question '" + totalQS() + "'");
            }
        } else {
            textArea.append("cancel deletion" + line);
        }
        headingLabelTQ.setText(" Total Question '" + totalQS() + "'");
        selectedTable = "";
    }

    private void setDatabase() {
        do {
            String dbNameC = JOptionPane.showInputDialog(this, "Enter Database Name", "Set Database");
            if (dbNameC != null) {
                if (!dbNameC.trim().isEmpty()) {
                    if (Database.createConnectionForExamPanel()) {
                        textArea.append("Set '" + dbNameC + "' Database." + line);
                        break;
                    } else {
                        textArea.append("Server not connected.\nCheck your connection and try again." + line);
                    }
                } else {
                    textArea.append("Enter Database Name" + line);
                }
            } else {
                textArea.append("Cancel..." + line);
                break;
            }
            System.out.println("SD " + dbNameC);
        } while (true);
    }


    // Method to start the timer
    private void startTimer() {
        Timer timer = new Timer(1, e -> updateTimeLabel());
        timer.start();
    }

    // Method to update the time label with the current time
    private void updateTimeLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); // Format for displaying time
        String currentTime = sdf.format(new Date()); // Get the current time
        timeLabel.setText(" Current Time " + currentTime); // Update the time label text
    }

}


