package pro.asfert.jobparser.dao;

import pro.asfert.jobparser.domain.Parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * Created by darkwawe on 10.03.2015.
 */
public class LoadingDataBase {
    public static void main(String[] args) {

        System.out.println();
        System.out.println("*****************************************************");
        System.out.println("Собираем вакансии и ссылки на них");
        System.out.println("*****************************************************");
        System.out.println();

        Map<String, String> mapOfSite1 = Parser.getVacancyMap();

        for (Map.Entry<String, String> pair : mapOfSite1.entrySet()) {
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }
        System.out.println(mapOfSite1.size());

    }

    private static void LoadData() {
        Map<String, String> DataMap = Parser.getSubCategoryMap();
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/mysql";
            connection = DriverManager.getConnection(url, "root", "123456");
            statement = connection.createStatement();

            /* Реализация логики загрузки данных в БД по каждой вакансии*/






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
