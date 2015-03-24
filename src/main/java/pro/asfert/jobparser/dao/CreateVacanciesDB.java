package pro.asfert.jobparser.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateVacanciesDB {
    //Так мы создаем базу данных:
    private final static String createDatabaseQuery =
            "CREATE DATABASE VacanciesApp CHARACTER SET utf8 COLLATE utf8_general_ci";

    public static void main(String[] args) {

        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/mysql";
            connection = DriverManager.getConnection(url, "root", "Gthbvtnh95");
            statement = connection.createStatement();
            statement.executeUpdate(createDatabaseQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}