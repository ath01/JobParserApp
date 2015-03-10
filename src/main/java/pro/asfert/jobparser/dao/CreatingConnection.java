package pro.asfert.jobparser.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by darkwawe on 10.03.2015.
 */
public class CreatingConnection {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loading success!");
            //у MySQL обязательно есть системная база,
            //к ней и будем создавать соединение.
            String url = "jdbc:mysql://localhost/mysql";
            String name = "root";
            String password = "123456";
            try {
                Connection con = DriverManager.getConnection(url, name, password);
                System.out.println("Connected.");
                con.close();
                System.out.println("Disconnected.");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}