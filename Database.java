import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;

public class Database {
    private static final String line = "\n-----------------------------------------------------------------------------------------------\n";
    private static Connection con;

    Database() {
        createConnection();
    }

    /*----------------------------CHECK-EXISTENCE------------------------*/
    static boolean databaseExists(String dbName) {
        try {
            Statement stmt = con.createStatement();

            // Query to check if the database exists
            ResultSet resultSet = stmt.executeQuery("SHOW DATABASES LIKE '" + dbName + "'");

            // If a result is found, the database exists
            return resultSet.next();
        } catch (SQLException e) {
            // Handle any exceptions here
            e.printStackTrace();
            return false; // Return false in case of exceptions
        }

    }

    static boolean tableExists(String tableName) {
        try {
            DatabaseMetaData metaData = con.getMetaData();
            ResultSet tables = metaData.getTables(null, null, tableName, null);
            return tables.next();
        } catch (SQLException e) {
            // Handle any exceptions here
            e.printStackTrace();
            return false; // Return false in case of exceptions
        }
    }

    /*----------------------------ADMIN-PANEL-DATABASE-METHODS------------------------*/
    public static boolean adminLoginValidation(String userID, String userPassword) {

        try {
            String LVQuery = "select * from admin_info where binary AdminID = '" + userID + "' and binary " +
                    "AdminPassword" +
                    " = '" + userPassword + "'";
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(LVQuery);
            // Return false for unsuccessful login
            System.out.println("adminLoginValidation try-method run 'databasePanel'");
            return resultSet.next(); // Return true for successful login

        } catch (Exception e) {
            System.err.println(e);
            System.out.println("adminLoginValidation catch-method run 'databasePanel'");
            return false; // Return false for any exception
        }

    }

    public static StringBuilder showDatabase() {
        String databaseName;
        StringBuilder dbList = new StringBuilder();

        try {
            Statement stmt = Database.con.createStatement();
            //PRINT ALL DATABASE ON YOUR SERVER.
            String query = "show databases";
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                databaseName = resultSet.getString("Database");
                dbList.append(databaseName).append("\n");
            }
            resultSet.close();
            stmt.close();

        } catch (Exception i) {
            System.err.println(i);
            System.err.println("Problem on this code\n" + i);
        }
        System.out.println("showDatabase method run 'databasePanel'");
        return dbList;
    }

    static void createDatabase(String dbName) throws IOException {

        try {
            String createDatabaseQuery = "create database " + dbName;
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createDatabaseQuery);
            stmt.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Database not exists");
        }
        System.out.println("createDatabase method run 'databasePanel'");
    }

    static void deleteDatabase(String dbName) throws IOException {

        try {
            String createDatabaseQuery = "drop database " + dbName;
            Statement stmt = con.createStatement();
            stmt.executeUpdate(createDatabaseQuery);
            stmt.close();
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("Database already exists");
        }
        System.out.println("deleteDatabase method run 'databasePanel'");
    }

    static void useDatabase(String dbName) {


        try {
            String useDatabaseQuery = "use " + dbName;
            Statement stmt = con.createStatement();
            stmt.executeUpdate(useDatabaseQuery);
            stmt.close();
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("please enter valid database name");
        }
        System.out.println("useDatabase method run 'databasePanel'");

    }

    static void createTable(String dbName, String query) {
        try {
            useDatabase(dbName);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        System.out.println("createTable method run 'databasePanel'");
    }

    public static StringBuilder showTables(String dbName) {
        StringBuilder tbList = new StringBuilder();

        try {
            useDatabase(dbName);
            Statement statement = con.createStatement();
            // Query to fetch all tables in the current database
            String query = "SHOW TABLES";
            ResultSet resultSet = statement.executeQuery(query);
            int n = 1;

            while (resultSet.next()) {
                // Get the table name from the result set
                String tableName = resultSet.getString(1);
                tbList.append(tableName).append("\n");
                n++;
            }

            // System.out.println("Total " + (n - 1) + " Tables in this Database");

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.err.println("Problem in this code\n" + e);
        }

        System.out.println("showTables method run 'databasePanel'");
        return tbList;
    }

    static void deleteTable(String tbName) {

        try {
            String deleteTableQuery = "drop table " + tbName;
            // System.out.println("Your Query --> '" + deleteTableQuery);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(deleteTableQuery);
            //  System.out.println(tbName + " table delete successfully");
        } catch (Exception i) {
            System.err.println(i);
        }
        System.out.println("deleteTables method run 'databasePanel'");
    }

    public static String showTableColumn(String tableName) {
        StringBuilder columnsInfo = new StringBuilder();

        try {
            String getColumnQuery = "DESC " + tableName; // Use the appropriate query for your database system
            // System.out.println("Your Query: " + getColumnQuery);
            // Execute the query to get column information
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(getColumnQuery);

            int n = 0;
            // Iterate through the result set to build the list of column names
            while (resultSet.next()) {
                String columnName = resultSet.getString("Field");
                columnsInfo.append(columnName).append(" | ");
                n++;
            }
            String str = "Total '" + n + "' column in table '" + tableName + "'" + line;
            columnsInfo.insert(0, str + "Column Name\n| ");

            // Close the result set and statement
            resultSet.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("showTableColumn method run 'databasePanel'");
        return columnsInfo.toString();
    }

    public static String showTableColumnAD(String tableName) {
        StringBuilder columnsInfo1 = new StringBuilder();

        try {
            String getColumnQuery = "DESC " + tableName; // Use the appropriate query for your database system
            // System.out.println("Your Query: " + getColumnQuery);
            // Execute the query to get column information
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(getColumnQuery);

            int n = 0;
            // Iterate through the result set to build the list of column names
            while (resultSet.next()) {
                String columnName = resultSet.getString("Field");
                columnsInfo1.append(columnName).append("\n");
                n++;
            }
            //  System.out.println("Total " + n + " columns");
            //  System.out.println("column name\n" + columnsInfo1);
            // Close the result set and statement
            resultSet.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("showTableColumnAD method run 'databasePanel'");
        return columnsInfo1.toString();
    }

    public static boolean addData(String tableName, String[] columnNames, String[] inputData) {
        PreparedStatement preparedStatement = null;

        try {
            // Check if the data already exists
            StringBuilder checkQuery = new StringBuilder("SELECT COUNT(*) FROM " + tableName + " WHERE ");
            for (int i = 0; i < columnNames.length; i++) {
                checkQuery.append(columnNames[i]).append(" = ?");
                if (i < columnNames.length - 1) {
                    checkQuery.append(" AND ");
                }
            }

            PreparedStatement checkStatement = con.prepareStatement(checkQuery.toString());
            for (int i = 0; i < inputData.length; i++) {
                checkStatement.setString(i + 1, inputData[i]);
            }

            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int rowCount = resultSet.getInt(1);

            // If rowCount is greater than 0, the data already exists
            if (rowCount > 0) {
                //  System.out.println("Duplicate data found in table '" + tableName + "'.");
                return false;
            }

            // Construct the INSERT query
            StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName + " (");
            for (String columnName : columnNames) {
                insertQuery.append(columnName).append(", ");
            }
            insertQuery.setLength(insertQuery.length() - 2); // Remove the trailing comma and space
            insertQuery.append(") VALUES (");
            insertQuery.append("?, ".repeat(inputData.length));
            insertQuery.setLength(insertQuery.length() - 2); // Remove the trailing comma and space
            insertQuery.append(")");

            // Prepare a statement with parameters for the data
            preparedStatement = con.prepareStatement(insertQuery.toString());

            // Set values for each parameter
            for (int i = 0; i < inputData.length; i++) {
                preparedStatement.setString(i + 1, inputData[i]);
            }

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                System.out.println("Data added to table '" + tableName + "'.");
                return true;
            } else {
                System.out.println("Failed to add data to table '" + tableName + "'.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error adding data: " + e.getMessage());
            return false;
        } finally {
            // Close resources in the reverse order of their creation
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("addData method run 'databasePanel'");
        }
    }

    public static String showData(String tableName) {
        StringBuilder tableData = new StringBuilder();
        int rowCount = 0; // Initialize row count
        int columnCount = 0; // Initialize column count

        try {
            String getTableDataQuery = "SELECT * FROM " + tableName;
            //System.out.println("Your Query: " + getTableDataQuery);

            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(getTableDataQuery);

            ResultSetMetaData metaData = resultSet.getMetaData();
            columnCount = metaData.getColumnCount();

            // Append column headers to the table data
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                tableData.append(columnName).append(" | ");
            }
            tableData.append("\n");

            // Append table data rows to the table data
            while (resultSet.next()) {
                rowCount++; // Increment row count
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = resultSet.getString(i);
                    tableData.append(columnValue).append(" | ");
                }
                tableData.append("\n");
            }

            // Close the result set and statement
            resultSet.close();
            stmt.close();

            if (rowCount == 0) {
                return "No data found in table '" + tableName + "'.";
            } else {
                return "Table '" + tableName + "' contains " + rowCount + " rows and " + columnCount + " columns" + ".\nColumn Names...\n" + tableData;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        System.out.println("showData method run databasePanel");
        return tableData.toString();
    }

    public static int rowCount(String tableName) {

        StringBuilder tableData = new StringBuilder();
        int rowCount = 0; // Initialize row count
        int columnCount = 0; // Initialize column count

        try {
            String getTableDataQuery = "SELECT * FROM " + tableName;
            // System.out.println("Your Query: " + getTableDataQuery);

            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(getTableDataQuery);

            ResultSetMetaData metaData = resultSet.getMetaData();
            columnCount = metaData.getColumnCount();

            // Append column headers to the table data
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                tableData.append(columnName).append(" | ");
            }
            tableData.append("\n");

            // Append table data rows to the table data
            while (resultSet.next()) {
                rowCount++; // Increment row count
                for (int i = 1; i <= columnCount; i++) {
                    String columnValue = resultSet.getString(i);
                    tableData.append(columnValue).append(" | ");
                }
                tableData.append("\n");
            }

            // Close the result set and statement
            resultSet.close();
            stmt.close();

            return rowCount;

        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("showData method run databasePanel");

        //System.out.println(rowCount);
        return rowCount;
    }


    /*-----------------------------SET-QUESTION-DATABASE-METHODS------------------------------*/
    public static void addQuestion(String tbName, String[] options) {

        System.out.println("option " + options[0] + ", " + options[1] + ", " + options[2] + ", " + options[3] + ", " + options[4] + ", " + options[5]);
        try {
            String sql = "INSERT INTO " + tbName + " (Question, QuizOption1, QuizOption2, QuizOption3, QuizOption4, " + "Answer) " + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);

            // Set the values for the placeholders
            statement.setString(1, options[0]);  //Question
            statement.setString(2, options[1]);  //Option1
            statement.setString(3, options[2]);  //Option2
            statement.setString(4, options[3]);  //Option3
            statement.setString(5, options[4]);  //Option1
            statement.setString(6, options[5]);  //Answer

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Question added successfully.");
            } else {
                System.out.println("Failed to add question.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e);
        }
        System.out.println("addQuestion method run 'databasePanel'");
    }

    public static int deleteDataById(String tableName, int quizNumber) {
        int rowsDeleted = 0;
        try {
            String deleteQuery = "DELETE FROM " + tableName + " WHERE QuizNumber = ?";
            PreparedStatement pstmt = con.prepareStatement(deleteQuery);
            pstmt.setInt(1, quizNumber);
            rowsDeleted = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("deleteDataByID method run 'databasePanel'");
        return rowsDeleted;
    }

    public static String showQuestionData(String tableName) {
        StringBuilder tableData = new StringBuilder();
        int rowCount = 0; // Initialize row count
        int columnCount = 0; // Initialize column count

        try {
            String getTableDataQuery = "SELECT * FROM " + tableName;
            // System.out.println("Your Query: " + getTableDataQuery);

            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(getTableDataQuery);

            ResultSetMetaData metaData = resultSet.getMetaData();
            columnCount = metaData.getColumnCount();

            // Append table data rows to the table data
            while (resultSet.next()) {
                rowCount++; // Increment row count

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String columnValue = resultSet.getString(i);
                    tableData.append(columnName).append(": ").append(columnValue).append("\n");
                }

                // Add a separator between rows
                tableData.append("\n");
            }

            // Close the result set and statement
            resultSet.close();
            stmt.close();

            if (rowCount == 0) {
                return "No data found in table '" + tableName + "'.";
            } else {
                return "Total '" + rowCount + "' Data" + line + tableData;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("showData method run databasePanel");
        return tableData.toString();
    }

    public static String showResult(String tableName) {
        StringBuilder tableData = new StringBuilder();
        int rowCount = 0; // Initialize row count
        try {
            String getTableDataQuery = "SELECT * FROM " + tableName;
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(getTableDataQuery);

            ResultSetMetaData metaData = resultSet.getMetaData();

            // Append table data rows to the table data
            while (resultSet.next()) {
                rowCount++; // Increment row count

                for (int i = 1; i <= 2; i++) {
                    String columnValue = resultSet.getString(i);
                    tableData.append(columnValue).append("\t");
                }

                for (int j = 5; j <= 5; j++) {
                    String columnValue1 = resultSet.getString(j);
                    tableData.append(columnValue1).append("\t");
                }
                tableData.append("\n");
            }

            // Close the result set and statement
            resultSet.close();
            stmt.close();

            if (rowCount == 0) {
                return "No data found in table '" + tableName + "'.";
            } else {
                return "Total '" + rowCount + "' Data" + line + tableData;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("showData method run databasePanel");
        return tableData.toString();
    }


    /*---------------------------------------------------------------------------------*/
    public static void fetchData(String tbName) {

        try {
            if (con != null) {
                System.out.println("Connected to the database!");

                // Query execute karke data fetch karein
                Statement statement = con.createStatement();
                String sqlQuery = "SELECT * FROM " + tbName;
                ResultSet resultSet = statement.executeQuery(sqlQuery);

                while (resultSet.next()) {
                    // ResultSet se data fetch karen
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String pass = resultSet.getString("pass");

                    // ... (aur baki columns ke liye fetch karein)
                    System.out.println("ID: " + id + ", Name: " + name + ", Name: " + pass);
                }

                resultSet.close();
                statement.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    /*----------------------------EXAM-PANEL-DATABASE-METHODS------------------------*/
    public static void insertDataInTable(String c1value, String c2value, String c3value, String c4value) throws IOException {

        try {
            String sql = "INSERT INTO candidate_info(UserID, UserName, UserMobile, UserPassword) VALUES" + " (?, ?, ?, ?)";
            System.out.println(sql);
            PreparedStatement statement = con.prepareStatement(sql);

            // Set the values for the placeholders
            statement.setString(1, c1value);
            statement.setString(2, c2value);
            statement.setString(3, c3value);
            statement.setString(4, c4value);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Registration successfully.");
            } else {
                System.out.println("Registration Failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e);
        }
        System.out.println("insertDataInTable method run 'databasePanel'");
    }

    public static boolean loginValidation(String userID, String userPassword) {

        try {
            String LVQuery = "select * from candidate_info where binary UserID = '" + userID + "' and binary " + "UserPassword = '" + userPassword + "'";
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(LVQuery);
            // Return false for unsuccessful login
            System.out.println("loginValidation try-method run 'databasePanel'");
            return resultSet.next(); // Return true for successful login

        } catch (Exception e) {
            System.err.println(e);
            System.out.println("loginValidation catch-method run 'databasePanel'");
            return false; // Return false for any exception
        }

    }

    public static String welcome(String userID) {
        String name = null;
        try {
            String LVQuery1 = "select UserName from candidate_info where binary UserID ='" + userID + "'";
            System.out.println("Enter query " + LVQuery1);
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(LVQuery1);
            while (resultSet.next()) {
                name = resultSet.getString("UserName");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        // System.out.println(name);
        System.out.println("welcome method run 'databasePanel'");
        return name;
    }

    //QUESTION
    public static void welcomeScreenQuestion(String[] questions, String[][] options) {
        try {
            // Define your SQL query to fetch questions
            String query = "SELECT Question, QuizOption1, QuizOption2, QuizOption3, QuizOption4 FROM java_quiz";
            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Execute the query and obtain the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the result set and populate the arrays
            int index = 0;
            while (resultSet.next()) {
                questions[index] = resultSet.getString("Question");
                options[index][0] = resultSet.getString("QuizOption1");
                options[index][1] = resultSet.getString("QuizOption2");
                options[index][2] = resultSet.getString("QuizOption3");
                options[index][3] = resultSet.getString("QuizOption4");

                // Log the retrieved data for debugging
               /* System.out.println("Question: " + questions[index]);
                System.out.println("Options: " + Arrays.toString(options[index]));
*/
                index++;
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection or query errors here
        }
    }

    public static String getAnswerFromDatabase(int QuizNumber) {
        String answer = ""; // Initialize with an empty string or default value

        try {
            // Create and execute a SQL query to fetch the answer based on questionId
            String query = "SELECT Answer FROM java_quiz WHERE QuizNumber = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setInt(1, QuizNumber);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        answer = resultSet.getString("Answer");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database error
        }
        System.out.println(answer);
        return answer;
    }

    static int NumberOfQuestion() {
        int numberOfQuestions = 0;
        try {
            // Define your SQL query to count the number of questions
            String query = "SELECT COUNT(*) AS total FROM java_quiz";
            // Create a PreparedStatement to execute the query
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Execute the query and obtain the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // Get the count from the result set
            if (resultSet.next()) {
                numberOfQuestions = resultSet.getInt("total");
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection or query errors here
        }

        return numberOfQuestions;
    }

    static void result(int score, String UserID) {
        try {
            String sql = "UPDATE candidate_info SET UserResult = " + score + " WHERE UserID = '" + UserID + "'";
            System.out.println(sql);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e);
        }
        System.out.println("result method run 'databasePanel'");
    }

    /*----------------------------Connection of server------------------------*/
    static void createConnection() {
        try {

            //Lode the Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Create a connection
            String url = "jdbc:mysql://localhost:3306/admin";
            String userName = "root";
            String password = "Kumar@111";

            con = DriverManager.getConnection(url, userName, password);

            if (con.isClosed()) {
                System.out.println("Connection Closed");
            } else {
                System.out.println("Connect");
            }

        } catch (NullPointerException | SQLException | ClassNotFoundException e) {
            System.err.println(e);
            System.err.println("Server not connected\nCheck Connection");
            JOptionPane.showMessageDialog(null, "Server not connected\nCheck your connection\nAnd please try again", "Server Connection", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("createConnection method run 'databasePanel'");
    }

    static boolean createConnectionForExamPanel() {

        try {
            // Load the Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create a connection
            String url = "jdbc:mysql://localhost:3306/quiz_exam";
            String userName = "root";
            String password = "Kumar@111";

            con = DriverManager.getConnection(url, userName, password);

            if (con.isClosed()) {
                System.out.println("createConnectionForExamPanel method run 'databasePanel'");
                System.out.println("Connection Closed");
                return false;
            } else {
                System.out.println("createConnectionForExamPanel method run 'databasePanel'");
                System.out.println("quiz_exam Connected");

                return true;
            }
        } catch (NullPointerException | SQLException | ClassNotFoundException e) {
            //  e.printStackTrace(); // Print the exception stack trace for debugging
            System.err.println("Server not connected\nCheck Connection");
            System.out.println("createConnectionForExamPanel method run 'databasePanel'");
            System.err.println(e);
            return false;
        }

    }


}



