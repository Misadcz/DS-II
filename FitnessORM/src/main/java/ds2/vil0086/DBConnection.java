package ds2.vil0086;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SimpleTimeZone;

public class DBConnection
{
    static String url = "jdbc:oracle:thin:@dbsys.cs.vsb.cz:1521:oracle";
    static String user = "VIL0086";
    static String password = "z2BSU3b5UWmvRj3Z";


    public void newReport(ReportVysledku reportVysledku) throws SQLException
    {
        Connection conn = null;
        String errorMsg ;
        try
        {
            conn = DriverManager.getConnection(url, user, password);
            String counter = "SELECT COUNT(*) FROM REPORT_VYSLEDKU WHERE ZAKAZNIK_ID_ZAKAZNIKA = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(counter);
            preparedStatement.setInt(1, reportVysledku.getZakaznik_id_zakaznika());

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
             if(resultSet.getInt(1)<1)
             {
                 System.out.println("Zakaznik s ID " + reportVysledku.getZakaznik_id_zakaznika() + " Neexistuje!");
                 return;
             }

            String CREATE_REPORT = "INSERT INTO \"REPORT_VYSLEDKU\" (ID_REPORTU,DATUM,VAHA,ZAKAZNIK_ID_ZAKAZNIKA) values (?,?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(CREATE_REPORT);

            ps.setInt(1,new Random().nextInt(1000));
            ps.setDate(2,reportVysledku.getDatum());
            ps.setBigDecimal(3,reportVysledku.getVaha());
            ps.setInt(4,reportVysledku.getZakaznik_id_zakaznika());

            ps.execute();
            System.out.println("Zaznam ulozen!");


        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public List<ReportVysledku> getReports(int id_zakaznika)
    {
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(url, user, password);

            String list = "SELECT ID_REPORTU,DATUM,VAHA,ZAKAZNIK_ID_ZAKAZNIKA FROM \"REPORT_VYSLEDKU\" WHERE ZAKAZNIK_ID_ZAKAZNIKA = ?  ";
            PreparedStatement preparedStatement = conn.prepareStatement(list);
            preparedStatement.setInt(1,id_zakaznika);
            ResultSet resultSet = preparedStatement.executeQuery();


            List<ReportVysledku> vysledky = new ArrayList<>();
            int i = 0;
            while(resultSet.next())
            {
                ReportVysledku reportVysledku = new ReportVysledku();
                reportVysledku.setId_reportu(resultSet.getInt(1));
                reportVysledku.setDatum(resultSet.getDate(2));
                reportVysledku.setVaha(resultSet.getBigDecimal(3));
                reportVysledku.setZakaznik_id_zakaznika(resultSet.getInt(4));

                vysledky.add(reportVysledku);
            i++;
            }
            for(int j = 0 ; j < i ; j++)
            {
                System.out.print(vysledky.get(j).getId_reportu());
                System.out.print("   ");
                System.out.print(vysledky.get(j).getDatum());
                System.out.print("   ");
                System.out.print(vysledky.get(j).getVaha());
                System.out.print("   ");
                System.out.println(vysledky.get(j).getZakaznik_id_zakaznika());
            }
            if(vysledky.get(0) == null)
                return null;
            return vysledky;

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }


    }


    public ReportVysledku getReport(int id_reportu)
    {
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(url, user, password);

            String report = "SELECT ID_REPORTU,DATUM,VAHA,ZAKAZNIK_ID_ZAKAZNIKA FROM \"REPORT_VYSLEDKU\" WHERE ID_REPORTU = ?  ";
            PreparedStatement preparedStatement = conn.prepareStatement(report);
            preparedStatement.setInt(1,id_reportu);
            ResultSet resultSet = preparedStatement.executeQuery();

            ReportVysledku reportVysledku = new ReportVysledku();

            while(resultSet.next())
            {
                reportVysledku.setId_reportu(resultSet.getInt(1));
                reportVysledku.setDatum(resultSet.getDate(2));
                reportVysledku.setVaha(resultSet.getBigDecimal(3));
                reportVysledku.setZakaznik_id_zakaznika(resultSet.getInt(4));
            }

            System.out.print(reportVysledku.getId_reportu());
            System.out.print("   ");
            System.out.print(reportVysledku.getDatum());
            System.out.print("   ");
            System.out.print(reportVysledku.getVaha());
            System.out.print("   ");
            System.out.println(reportVysledku.getZakaznik_id_zakaznika());


            return reportVysledku;
        }
        catch (SQLException e)
        {
            System.out.println("Uzivatel neexistuje");
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }


    }

    public void updateReport(ReportVysledku reportVysledku)
    {
        Connection conn = null;
        String errorMsg ;
        try
        {
            conn = DriverManager.getConnection(url, user, password);

            String UPDATE_REPORT = "UPDATE \"REPORT_VYSLEDKU\" SET DATUM = ? , VAHA = ? , ZAKAZNIK_ID_ZAKAZNIKA = ? WHERE ID_REPORTU = ?";



            /*           if(reportVysledku.getDatum() !=null)
                UPDATE_REPORT += " DATUM = ?";
            if(reportVysledku.getVaha() !=null)
            {
                if(UPDATE_REPORT.contains("DATUM"))
                    UPDATE_REPORT += ",";
                UPDATE_REPORT += " VAHA = ?";
            }
            if(Integer.toString(reportVysledku.getZakaznik_id_zakaznika()) != null)
            {
                if(UPDATE_REPORT.contains("DATUM"))
                    UPDATE_REPORT += ",";
                else if ( UPDATE_REPORT.contains("DATUM"))
                    UPDATE_REPORT += ",";
                UPDATE_REPORT += " ZAKAZNIK_ID_ZAKAZNIKA = ?";
            }

            UPDATE_REPORT += " WHERE ID_REPORTU = ?";
            */

            PreparedStatement ps = conn.prepareStatement(UPDATE_REPORT);




            ps.setDate(1,reportVysledku.getDatum());
            ps.setBigDecimal(2,reportVysledku.getVaha());
            ps.setInt(3,reportVysledku.getZakaznik_id_zakaznika());
            ps.setInt(4,reportVysledku.getId_reportu());

            ps.execute();

            System.out.println("Report Aktualizovan!");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void deleteReport(int id_reportu)
    {
        Connection conn = null;
        String errorMsg ;
        try
        {
            conn = DriverManager.getConnection(url, user, password);

            String DELETE_REPORT = "SELECT COUNT(*) FROM REPORT_VYSLEDKU WHERE ID_REPORTU = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_REPORT);
            preparedStatement.setInt(1,id_reportu);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int count = resultSet.getInt(1);
            if(resultSet.getInt(1)<1)
            {
                System.out.println("Report s ID " + id_reportu + " Neexistuje!");
                return;
            }


            DELETE_REPORT = "DELETE FROM REPORT_VYSLEDKU WHERE ID_REPORTU = ?";
            preparedStatement = conn.prepareStatement(DELETE_REPORT);
            preparedStatement.setInt(1,id_reportu);

            preparedStatement.execute();




            System.out.println("Zaznam uspesne odstranen!");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void newPlan(Trenink trenink, List<Cvik> cviky,int id_objednavky) throws SQLException {
        Connection conn = null;
        String errorMsg;
        try {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);



        String CHECK_SQL = "SELECT COUNT(*) FROM OBJEDNAVKA WHERE ID_OBJEDNAVKY = ?";


        PreparedStatement preparedStatement = conn.prepareStatement(CHECK_SQL);
        preparedStatement.setInt(1, id_objednavky);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        if(resultSet.getInt(1)<1)
        {
            System.out.println("Objednavka s ID " + id_objednavky + " Neexistuje!");
            return;
        }
//
        String CREATE_PLAN = "INSERT INTO \"TRENINKOVY_PLAN\" (ID_TRENINKOVEHO_PLANU) values (?)";

        PreparedStatement prepareStatement = conn.prepareStatement(CREATE_PLAN);

        int temp = new Random().nextInt(1000);
        prepareStatement.setInt(1,temp);
        prepareStatement.execute();

        conn.commit();

            String CREATE_PLAN_4 = "UPDATE \"OBJEDNAVKA\" SET TP_ID_TRENINKOVEHO_PLANU = ? WHERE ID_OBJEDNAVKY = ?";

            prepareStatement = conn.prepareStatement(CREATE_PLAN_4);
            prepareStatement.setInt(1,temp);
            prepareStatement.setInt(2,id_objednavky);
            prepareStatement.execute();




        String CREATE_PLAN_3 = "INSERT INTO \"TRENINK\" (ID_TRENINKU,NAZEV_TRENINKU,TP_ID_TRENINKOVEHO_PLANU) values (?,?,?)";

        prepareStatement = conn.prepareStatement(CREATE_PLAN_3);
        int temp_2 = new Random().nextInt(1000);
        prepareStatement.setInt(1,temp_2);
        prepareStatement.setString(2,trenink.getNazev_treninku());
        prepareStatement.setInt(3,temp);
        prepareStatement.execute();


        for(int i = 0; i <  cviky.size() ; i++)
        {
            String CREATE_PLAN_2 = "INSERT INTO \"CVIK\" (ID_CVIKU,TRENINK_ID_TRENINKU,NAZEV_CVIKU,DELKA_PAUZY,POCET_SERII) values (?,?,?,?,?)";
            prepareStatement = conn.prepareStatement(CREATE_PLAN_2);

            prepareStatement.setInt(1,new Random().nextInt(1000));
            prepareStatement.setInt(2,temp_2);
            prepareStatement.setString(3,cviky.get(i).getNazev_cviku());
            prepareStatement.setInt(4,cviky.get(i).getDelka_pauzy());
            prepareStatement.setInt(5,cviky.get(i).getPocet_serii());
            prepareStatement.execute();
        }

        conn.commit();
        System.out.println("Plan uspesne pridan!");

        } catch (SQLException e) {
            conn.rollback();
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

        public void getPlans(int id_zakaznika)
    {
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection(url, user, password);

            String list = "SELECT tp.id_treninkoveho_planu\n" +
                    "FROM TRENINKOVY_PLAN tp\n" +
                    " JOIN Objednavka o ON tp.id_treninkoveho_planu = o.TP_ID_TRENINKOVEHO_PLANU\n" +
                    " JOIN Zakaznik z ON o.ZAKAZNIK_ID_ZAKAZNIKA = z.id_zakaznika\n" +
                    "WHERE z.id_zakaznika = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(list);
            preparedStatement.setInt(1,id_zakaznika);
            ResultSet resultSet = preparedStatement.executeQuery();



            int i = 0;
            while(resultSet.next())
            {
                System.out.println(resultSet.getInt(1));
            }



        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }


    }
    public void getPlan(int id_treninku)
    {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String Reserver = "SELECT TP.ID_TRENINKOVEHO_PLANU,T.NAZEV_TRENINKU,C.NAZEV_CVIKU,C.POCET_SERII,C.DELKA_PAUZY FROM TRENINKOVY_PLAN TP JOIN TRENINK T ON T.TP_ID_TRENINKOVEHO_PLANU = TP.ID_TRENINKOVEHO_PLANU JOIN CVIK C ON C.TRENINK_ID_TRENINKU = T.ID_TRENINKU WHERE TP.ID_TRENINKOVEHO_PLANU = ?";


            PreparedStatement preparedStatement = conn.prepareStatement(Reserver);
            preparedStatement.setInt(1,id_treninku);
            ResultSet resultSet = preparedStatement.executeQuery();


            while(resultSet.next())
            {
                System.out.print(resultSet.getInt(1));
                System.out.print("   ");
                System.out.print(resultSet.getString(2));
                System.out.print("   ");
                System.out.print(resultSet.getString(3));
                System.out.print("   ");
                System.out.print(resultSet.getInt(4));
                System.out.print("   ");
                System.out.println(resultSet.getInt(5));
            }


        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }













}




