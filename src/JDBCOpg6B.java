import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class JDBCOpg6B {

    public static void main(String[] args) {
        try {
            System.out.println("Find daglig omsætning af produkt på dato ");
            BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Indtast Dato (YYYY-MM-DD): ");
            String dato = inLine.readLine();
            System.out.print("Indtast Produktnavn: ");
            String produktNavn = inLine.readLine();


            Connection minConnection;
            minConnection = DriverManager
                    .getConnection("jdbc:sqlserver://localhost;databaseName=AarhusBryghus;user=sa;password=reallyStrongPwd123;");
            Statement statement = minConnection.createStatement();

            String sql = "Execute SamledeSalgProduktPaaDag ?,?";
            PreparedStatement prestmt = minConnection.prepareStatement(sql);
            prestmt.clearParameters();

            prestmt.setString(1, dato);
            prestmt.setString(2, produktNavn);

            ResultSet res = prestmt.executeQuery();
            res.next();
            System.out.println("Produkt: " + res.getString(1) + ", Beløb: " + res.getString(2) + " DKK, Dato: " + res.getString(3).substring(0, 10));


            if (prestmt != null)
                prestmt.close();
            if (statement != null)
                statement.close();
            if (minConnection != null)
                minConnection.close();

        }
        catch (SQLException sqlException) {
            System.out.println("SQL fejl besked: " + sqlException.getMessage());
            System.out.println("SQL fejl Kode: " + sqlException.getErrorCode());
            if (sqlException.getErrorCode() == 0) {
                System.out.println("Salg af dette produkt på valgte dato findes ikke");
            }
        } catch (Exception e) {
            System.out.println("fejl:  " + e.getMessage());
        }
    }
}