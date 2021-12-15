package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String database = "oscar";
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + database + "?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&characterEncoding=utf-8",
                    "root", "ouro1234");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
