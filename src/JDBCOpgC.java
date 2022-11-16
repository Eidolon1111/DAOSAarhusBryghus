import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class JDBCOpgC {

    public static void main(String[] args) {
        try {
            System.out.println("Opret Salgslinje ");
            BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Indtast antal: ");
            String antal = inLine.readLine();
            System.out.println("Intast aftalt pris: ");
            String aftaltPris = inLine.readLine();
            System.out.println("Indtast salgsid: ");
            String salgsId = inLine.readLine();
            System.out.println("Indtast prisid: ");
            String prisId = inLine.readLine();

            Connection minConnection;
            minConnection = DriverManager
                    .getConnection("jdbc:sqlserver://localhost\\SQLExpress;databaseName=Aarhus bryghus;user=sa;password=qbu65jvt;");
            Statement statement = minConnection.createStatement();

            String sql = "insert into Salgslinje values(?,?,?,?) ";
            PreparedStatement prestmt = minConnection.prepareStatement(sql);
            prestmt.clearParameters();

            prestmt.setInt(1, Integer.parseInt(antal.trim()));
            prestmt.setDouble(2, Double.parseDouble(aftaltPris.trim()));
            prestmt.setInt(3, Integer.parseInt(salgsId.trim()));
            prestmt.setInt(4, Integer.parseInt(prisId.trim()));

            prestmt.executeUpdate();
            System.out.println("Salgslinje indsat");

            String select =  "select p.minimumsAntalPaaLager, p.antalPaaLager \n" +
                    "from Salgslinje S\n" +
                    "inner join Pris\n" +
                    "on pris.prisid = s.prisId\n" +
                    "inner join Produkt p\n" +
                    "on p.produktId = pris.produktId\n" +
                    "where s.prisid = ?";
            PreparedStatement selectPreStmt = minConnection.prepareStatement(select);
            selectPreStmt.clearParameters();

            selectPreStmt.setInt(1, Integer.parseInt(prisId.trim()));

            ResultSet res = selectPreStmt.executeQuery();

            int minimumsAntalPaaLager = 0;
            int antalPaaLager = 0;
            while (res.next()) {
                minimumsAntalPaaLager = res.getInt(1);
                antalPaaLager = res.getInt(2);
            }
            if (antalPaaLager < minimumsAntalPaaLager) {
                System.out.println("Antal på lager er mindre end minimumsantallet");
            }
            System.out.println("Antal på lager: " + antalPaaLager);
            System.out.println("Minimum antal: " + minimumsAntalPaaLager);

            if (prestmt != null)
                prestmt.close();
            if (statement != null)
                statement.close();
            if (minConnection != null)
                minConnection.close();
        } catch (Exception e) {
            System.out.println("fejl:  " + e.getMessage());
        }
    }
}

