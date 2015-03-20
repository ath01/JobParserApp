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
        selectFromTable("346 59");
    }

    private static void printResults(ResultSet rs) throws SQLException {
        String id, vacancy, salary, experience, education, employer, details, hr;
        /*if (!rs.isBeforeFirst()) {
            System.out.println("По вашему запросу ничего не найдено");
        }*/
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


    private static void selectFromTable(String queries) {

        Connection connection = null;
        Statement statement = null;
        StringBuilder result = new StringBuilder();
        ArrayList<String> arrayListQueries = new ArrayList<String>();
        StringBuilder query = new StringBuilder();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/Vacancies" +
                    "?characterEncoding=utf8";
            connection = DriverManager.getConnection(url, "root", "Gthbvtnh95");
            System.out.println("Connected.");
            statement = connection.createStatement();
            String[] arrayQueriesTmp = queries.replaceAll("\\s+", " ").trim().split(" ");

            for (int i = 0; i < arrayQueriesTmp.length; i++) {
                if (arrayQueriesTmp[i].matches("^[0-9a-zA-Zа-яА-Я]*$")) {
                    StringBuilder request = new StringBuilder();
                    request.append("%").append(arrayQueriesTmp[i]).append("%");
                    arrayListQueries.add(request.toString());
                }
            }
            result.append("По вашему запросу \"");
            for (int i = 0; i < arrayQueriesTmp.length; i++) {
                result.append(arrayQueriesTmp[i]);
                if (i < arrayQueriesTmp.length - 1) {
                    result.append(" ");
                }
            }
            result.append("\" ничего не найдено");

            if (arrayListQueries.size() != 0) {
                query.append("SELECT * FROM Vacancies WHERE ");
                for (int i = 0; i < arrayListQueries.size(); i++) {
                    if (i > 0 && i < arrayListQueries.size()) {
                        query.append(" OR ");
                    }
                    query.append("vacancy LIKE ").append("\'").append(arrayListQueries.get(i)).append("\'").
                            append(" OR details LIKE ").append("\'").append(arrayListQueries.get(i)).append("\'");
                    if (i > 0 && i < arrayListQueries.size() - 1) {
                        query.append(" OR ");
                    }
                }
                if (arrayListQueries.size() > 1) {
                    query.append(" OR vacancy LIKE \'");
                    for (int i = 0; i < arrayListQueries.size(); i++) {
                        query.append(arrayListQueries.get(i));
                        if (i < arrayListQueries.size() - 1) {
                            query.append(" ");
                        }
                        if (i == arrayListQueries.size() - 1) {
                            query.append("\'");
                        }
                    }
                    query.append(" OR details LIKE \'");
                    for (int i = 0; i < arrayListQueries.size(); i++) {
                        query.append(arrayListQueries.get(i));
                        if (i < arrayListQueries.size() - 1) {
                            query.append(" ");
                        }
                        if (i == arrayListQueries.size() - 1) {
                            query.append("\'");
                        }
                    }
                }
                //вывод количества элементов для себя
                String count = "SELECT COUNT(*) FROM Vacancies";
                ResultSet resultSet = statement.executeQuery(count);
                while (resultSet.next()) {
                    System.out.println("Количество строк в базе: " + resultSet.getInt(1));
                }
                ResultSet rs = statement.executeQuery(query.toString());
                printResults(rs);
                if (!rs.isBeforeFirst()) {
                    System.out.println(result.toString());
                }
            } else {
                System.out.println(result.toString());
            }
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
