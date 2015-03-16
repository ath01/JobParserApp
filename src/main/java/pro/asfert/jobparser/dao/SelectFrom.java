package pro.asfert.jobparser.dao;

import pro.asfert.jobparser.domain.Parser;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by darkwawe on 14.03.2015.
 */
public class SelectFrom {
    public static void main(String[] args) {

        Map<String, String> DataMap = Parser.getVacancyMap();
        Connection connection = null;
        Statement statement = null;
        Map<String, String> hashmap = new HashMap<String, String>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/vacations" +
                    "?characterEncoding=utf8";;
            connection = DriverManager.getConnection(url, "root", "123456");
            System.out.println("Connected.");
            statement = connection.createStatement();
            String query = "select * from vacations";
            ResultSet rs = statement.executeQuery(query);
            printResults(rs);
            System.out.println("Disconnected");


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

    private static void printResults(ResultSet rs) throws SQLException {
        String vacancy, salary, experience, education, employer, details, hr;

        while (rs.next()) {
            vacancy = rs.getString("vacancy");
            salary = rs.getString("salary");
            experience = rs.getString("experience");
            education = rs.getString("education");
            employer = rs.getString("employer");
            details = rs.getString("details");
            hr = rs.getString("details");
            System.out.println("******************************");
            System.out.println("vacancy: " + vacancy);
            System.out.println("salary: " + salary);
            System.out.println("experience: " + experience);
            System.out.println("education: " + education);
            System.out.println("employer: " + employer);
            System.out.println("details: " + details);
            System.out.println("hr: " + hr);
            System.out.println("******************************");
        }
    }
}
