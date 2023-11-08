package persistence;

import java.sql.*;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:oracle:thin:@db.ig.he-arc.ch:1521:ens";
    private static final String DB_USER = "ilan_springen";
    private static final String DB_PASSWORD = "ilan_springen";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors du chargement du pilote JDBC.");
        }
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLException("Erreur lors de la fermeture de la connexion.");
            }
        }
    }
}