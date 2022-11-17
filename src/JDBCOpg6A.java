import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class JDBCOpg6A {
    public static void main(String[] args) {
        opretProdukt();
    }

    public static void opretProdukt(){
        try {
            System.out.println("Opret Produkt");
            BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Indtast ProduktID: ");
            String ProduktId = inLine.readLine();
            System.out.println("Intast Produkt navn: ");
            String navn = inLine.readLine();
            System.out.println("Indtast antal på lager: ");
            String antalPaaLager = inLine.readLine();
            System.out.println("Indtast minimum antal på lager: ");
            String minimumsAntalPaaLager = inLine.readLine();
            System.out.println("Indtast antal enheder (f.eks '20' som i '20 liter'): ");
            String antalEnheder = inLine.readLine();
            System.out.println("Indtast navn enheder (f.eks 'liter'): ");
            String enhedNavn = inLine.readLine();
            System.out.println("Indtast Produktgruppe navn: ");
            String produktgruppeNavn = inLine.readLine();

            Connection minConnection;
            minConnection = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost;databaseName=AarhusBryghus;user=sa;password=reallyStrongPwd123;");

            String sql = "insert into Produkt values(?,?,?,?,?,?,?) ";// preparedStatement
            PreparedStatement prestmt = minConnection.prepareStatement(sql);
            prestmt.clearParameters();

            prestmt.setInt(1, Integer.parseInt(ProduktId.trim()));
            prestmt.setString(2, navn);
            prestmt.setInt(3, Integer.parseInt(antalPaaLager.trim()));
            prestmt.setInt(4, Integer.parseInt(minimumsAntalPaaLager.trim()));
            prestmt.setInt(5, Integer.parseInt(antalEnheder.trim()));
            prestmt.setString(6, enhedNavn);
            prestmt.setString(7, produktgruppeNavn);

            prestmt.executeUpdate();
            System.out.println("Produkt Oprettet!");

            if (prestmt != null)
                prestmt.close();
            if (minConnection != null)
                minConnection.close();
        } catch (SQLException sqlException){
            if(sqlException.getErrorCode() == 2627){
                System.out.println("ProduktID er allerede anvendt");
            } else if(sqlException.getErrorCode() == 2628 && sqlException.getMessage().contains("navn")) {
                System.out.println("Indtastet Produkt navn er for langt (max 30 karakterer)");
            } else if(sqlException.getErrorCode() == 2628 && sqlException.getMessage().contains("enhed")) {
                System.out.println("Indtastet enhed navn er for langt (max 30 karakterer)");
            } else if (sqlException.getErrorCode() == 2628 && sqlException.getMessage().contains("produktGruppeNavn")){
                System.out.println("Indtastet Produktgruppe navn er for langt (max 30 karakterer)");
            } else if(sqlException.getErrorCode() == 547 && sqlException.getMessage().contains("minimumsAntalPaaLager")){
                System.out.println("minimum antal på lager skal være over 0");
            } else if(sqlException.getErrorCode() == 547 && sqlException.getMessage().contains("FOREIGN KEY")){
                System.out.println("Produktgruppe navn er findes ikke!");
            } else {
                System.out.println("SQL fejl besked: " + sqlException.getMessage());
                System.out.println("SQL fejl Kode: " + sqlException.getErrorCode());
            }
        } catch (Exception e) {
            System.out.println("fejl:  " + e.getMessage());
        }
    }
}
