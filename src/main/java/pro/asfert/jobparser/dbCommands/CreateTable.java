package pro.asfert.jobparser.dbCommands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    private final static String createTableQuery = "CREATE TABLE `Vacancies` (" +
            "  `id` int(11) NOT NULL auto_increment," +
            "  `vacancy` varchar(500) default NULL," +
            "  `salary` varchar(100) default NULL," +
            "  `experience` varchar(100) default NULL," +
            "  `education` varchar(1000) default NULL," +
            "  `employer` varchar(50) default NULL," +
            "  `details` varchar(10000) default NULL," +
            "  `hr` varchar(1000) default NULL," +
            "  `url` varchar(1000) default NULL," +
            "  PRIMARY KEY  (`id`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/VacanciesApp" +
                    "?characterEncoding=utf8";
            connection = DriverManager.getConnection(url, "root", "Gthbvtnh95");
            statement = connection.createStatement();
            statement.executeUpdate(createTableQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //позакрываем теперь все
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