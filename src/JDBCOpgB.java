import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCOpgB {

    public static void main(String[] args) {
        try {
            Connection minConnection;
            minConnection = DriverManager
                    .getConnection("jdbc:sqlserver://localhost;databaseName=TidsRegOpg;user=sa;password=reallyStrongPwd123;");
            Statement statement = minConnection.createStatement();

            ResultSet result = statement.executeQuery("select * from Medarbejder");
            while (result.next()) {
                System.out.println(result.getString(1) + "\t" + result.getString(2) + " \t " + result.getString(3) + " \t " + result.getString(4));
            }

            if (result != null)
                result.close();
            if (statement != null)
                statement.close();
            if (minConnection != null)
                minConnection.close();


        } catch (Exception e) {
            System.out.println("fejl:  " + e.getMessage());
        }
    }
}
