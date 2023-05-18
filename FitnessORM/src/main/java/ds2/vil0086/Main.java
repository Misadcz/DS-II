package ds2.vil0086;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static DBConnection database = new DBConnection();


    public static void main(String[] args) throws SQLException
    {
       Date date = new Date(System.currentTimeMillis());


        ReportVysledku reportVysledku = new ReportVysledku();

        while(true)
        {
            System.out.println("1 - Nový report");
            System.out.println("2 - Seznam reportu");
            System.out.println("3 - Detail reportu");
            System.out.println("4 - Aktualizace reportu");
            System.out.println("5 - Smazání reportu");
            System.out.println("6 - Nový plán");
            System.out.println("7 - Seznam plánů");
            System.out.println("8 - Detail plánu");
            System.out.println("jiné - Exit");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Zadejte výběr: ");
            int cislo = scanner.nextInt();


            switch (cislo)
            {
                case 1 :
                    reportVysledku = new ReportVysledku();
                    reportVysledku.setVaha(BigDecimal.valueOf(66));
                    reportVysledku.setDatum( date);
                    reportVysledku.setZakaznik_id_zakaznika(1);
                    database.newReport(reportVysledku);
                    break;
                case 2 :
                    database.getReports(1);
                    break;
                case 3 :
                    database.getReport(1);
                    break;
                case 4 :
                    reportVysledku.setId_reportu(1);
                    reportVysledku.setVaha(BigDecimal.valueOf(999));
                    reportVysledku.setDatum(date.valueOf("2002-02-02"));
                    reportVysledku.setZakaznik_id_zakaznika(2);
                    database.updateReport(reportVysledku);
                    break;
                case 5 :
                    database.deleteReport(1);
                    break;
                case 6 :
                    List<Cvik> cviky = new ArrayList<>();
                    cviky.add(new Cvik(1,"Dipy",30,30,1));
                    cviky.add(new Cvik(2,"Bench Press",45,50,1));
                    database.newPlan(new Trenink(1,"Push",2),cviky,2);
                    break;
                case 7 :
                    database.getPlans(1);
                    break;
                case 8 :
                    database.getPlan(614);
                    break;
                default:
                    System.exit(1);
                    break;

            }
        }




    }
}