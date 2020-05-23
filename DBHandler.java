import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DBHandler {
    // todo change below line to fit to your configurations
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db";

    // todo change below lines to correspond to your credentials for the database
    private static final String USER = "root";
    private static final String PASS = "password";

    private static Connection conn = null;
    private static Statement stmt = null;
    private static PreparedStatement pstmt = null;

    public boolean check() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            return true;
        } catch (Exception e) {
            return false;
        }
    }






    public boolean addDB(String type,String s){
        boolean success = true;
        StringTokenizer bulk = new StringTokenizer(s,"\n");
        while(bulk.hasMoreTokens()){
            StringTokenizer st = new StringTokenizer(bulk.nextToken(),",");
            if (type.equals("productKeyword")) {
                try {
                    pstmt = conn.prepareStatement("INSERT INTO productKeyword VALUES(?,?)");
                    pstmt.setInt(1, Integer.parseInt(st.nextToken()));
                    pstmt.setString(2, st.nextToken());
                    pstmt.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                    success = false;
                }
            } else if (type.equals("websitephone")) {
                try {
                    pstmt = conn.prepareStatement("INSERT INTO websitephone VALUES(?,?)");
                    pstmt.setString(1, st.nextToken());
                    pstmt.setString(2, st.nextToken());
                    pstmt.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                    success = false;
                }
            }
            else if(type.equals("product")){
                try {
                    pstmt = conn.prepareStatement("INSERT INTO product VALUES(?,?,?,?)");
                    pstmt.setInt(1,Integer.parseInt(st.nextToken()));
                    pstmt.setString(2,st.nextToken());
                    pstmt.setString(3,st.nextToken());
                    pstmt.setString(4,st.nextToken());
                    pstmt.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                    success = false;
                }
            }
            else if(type.equals("externalsupplier")){
                try {
                    pstmt = conn.prepareStatement("INSERT INTO externalsupplier VALUES(?,?,?,?)");
                    pstmt.setString(1,st.nextToken());
                    pstmt.setString(2,st.nextToken());
                    pstmt.setString(3,st.nextToken());
                    pstmt.setString(4,st.nextToken());
                    pstmt.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                    success = false;
                }
            }
            else if(type.equals("sell")){
                try {
                    pstmt = conn.prepareStatement("INSERT INTO sell VALUES(?,?,?,?,?)");
                    pstmt.setInt(1,Integer.parseInt(st.nextToken()));
                    pstmt.setString(2,st.nextToken());
                    pstmt.setString(3,st.nextToken());
                    pstmt.setInt(4,Integer.parseInt(st.nextToken()));
                    pstmt.setInt(5,Integer.parseInt(st.nextToken()));
                    pstmt.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                    success = false;
                }
            }
            else if(type.equals("website")){
                try {
                    String website_url = st.nextToken();
                    pstmt = conn.prepareStatement("INSERT INTO zip VALUES(?,?,?)");
                    pstmt.setString(1,st.nextToken());
                    pstmt.setString(2,st.nextToken());
                    String zip_code = st.nextToken();
                    pstmt.setString(3,zip_code);

                    pstmt.executeUpdate();

                    pstmt = conn.prepareStatement("INSERT INTO website VALUES(?,?,?,?)");
                    pstmt.setString(1,website_url);
                    pstmt.setString(2,zip_code);
                    pstmt.setString(3,st.nextToken());
                    pstmt.setString(4,st.nextToken());
                    pstmt.executeUpdate();

                } catch (Exception e) {
                    e.printStackTrace();
                    success = false;
                }
            }
        }

        return success;
    }

    public boolean deleteDB(String type,String s){
        boolean success = true;
        if(type.equals("product")){
            try {
                pstmt = conn.prepareStatement("DELETE FROM product WHERE id = ?");
                pstmt.setInt(1,Integer.parseInt(s));
                pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                success = false;
            }
        }
        else if(type.equals("sell")) {
            StringTokenizer st = new StringTokenizer(s,",");
            try {
                pstmt = conn.prepareStatement("DELETE FROM sell WHERE id = ? AND url = ?");
                pstmt.setInt(1,Integer.parseInt(st.nextToken()));
                pstmt.setString(2,st.nextToken());
                pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                success = false;
            }
        }
        return success;
    }



    public void queryFoo(int number){
        if(number == 1){
            try{
                ResultSet result = stmt.executeQuery("SELECT *" +
                        "FROM product as p " +
                        "WHERE p.id =" +
                        "(SELECT id " +
                        "FROM (SELECT distinct id,url FROM sell) as mostproduct " +
                        "GROUP BY id " +
                        "ORDER BY COUNT(*) DESC " +
                        "LIMIT 1)");
                while(result.next()){
                    int id = result.getInt("id");
                    System.out.println(Integer.toString(id));
                }
            }
            catch (Exception e) {
            e.printStackTrace();
            }
        }
        else if(number == 2) {
            try{
                ResultSet result = stmt.executeQuery("SELECT s2.id,s2.url,s2.date " +
                        "FROM (SELECT id,url,MAX(initial-discounted) as bargain "+
                        "FROM sell "+"GROUP BY id,url) as s1, sell as s2 "+
                        "WHERE s1.id = s2.id AND s1.url = s2.url AND s1.bargain = (s2.initial-s2.discounted)");
                while(result.next()){
                    int id = result.getInt("id");
                    String url = result.getString("url");
                    String date = result.getString("date");
                    System.out.println(Integer.toString(id)+","+url+","+date);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(number == 3){
            try{
                ResultSet result = stmt.executeQuery("SELECT * FROM product as p " +
                        "WHERE NOT EXISTS(SELECT p.id FROM sell as s WHERE p.id=s.id)");
                while(result.next()){
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    String des = result.getString("des");
                    String bName = result.getString("bname");
                    System.out.println(Integer.toString(id)+","+name+","+des+","+bName);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
