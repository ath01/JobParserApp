package pro.asfert.jobparser.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    private final static String createTableQuery = "CREATE TABLE `vacations` (" +
            "  `id` int(11) NOT NULL auto_increment," +
            "  `vacancy` varchar(50) default NULL," +
            "  `salary` int(100) default NULL," +
            "  `experience` int(100) default NULL," +
            "  `education` varchar(20) default NULL," +
            "  `employer` varchar(50) default NULL," +
            "  `responsibilities` varchar(150) default NULL," +
            "  PRIMARY KEY  (`id`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Подключаемся к новосозданной базе. Значение параметров после "?"
            //ясно из их имен.
            String url = "jdbc:mysql://localhost/vacations" +
                    "?characterEncoding=utf8";
            connection = DriverManager.getConnection(url, "root", "123456");
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