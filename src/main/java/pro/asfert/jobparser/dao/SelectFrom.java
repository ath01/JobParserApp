package pro.asfert.jobparser.dao;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import pro.asfert.jobparser.domain.Parser;

import java.sql.*;
import java.util.*;

/**
 * Created by darkwawe on 14.03.2015.
 */
public class SelectFrom {
    public static void main(String[] args) {
        selectFromTable("специалист продаж");
    }

    public static void printResults(ResultSet rs) throws SQLException {
        String id, vacancy, salary, experience, education, employer, details, hr;

        while (rs.next()) {
            id = rs.getString("id");
            vacancy = rs.getString("vacancy");
            salary = rs.getString("salary");
            experience = rs.getString("experience");
            education = rs.getString("education");
            employer = rs.getString("employer");
            details = rs.getString("details");
            hr = rs.getString("hr");
            System.out.println("******************************");
            System.out.println("id: " + id);
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

    public static void selectFromTable(String queries) {

        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/vacations" +
                    "?characterEncoding=utf8";
            connection = DriverManager.getConnection(url, "root", "123456");
            System.out.println("Connected.");
            statement = connection.createStatement();
            String[] arrayQueriesTmp = queries.trim().split(" ");
            ArrayList<String> arrayListQueries = new ArrayList<String>();
            StringBuilder query = new StringBuilder();
            for (int i = 0; i < arrayQueriesTmp.length; i++) {
                if (arrayQueriesTmp[i].matches("^[0-9a-zA-Zа-яА-Я]*$")) {
                    StringBuilder request = new StringBuilder();
                    request.append("\'%").append(arrayQueriesTmp[i]).append("%\'").append(" ");
                    arrayListQueries.add(request.toString());
                }
            }
            query.append("select * FROM vacations WHERE vacancy or details LIKE ");

            for (int i = 0; i < arrayListQueries.size(); i++) {
                query.append(arrayListQueries.get(i)).append("AND vacancy or details ").append("LIKE ").append(arrayListQueries.get(i));
                if (arrayListQueries.size() > 0 && i != arrayListQueries.size()-1) {
                    query.append("AND ");
                }
            }

            ResultSet rs = statement.executeQuery(query.toString());
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
}
