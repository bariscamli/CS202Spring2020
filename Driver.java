import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Driver {
    public static void main(String[] args) {


        StringTokenizer st = new StringTokenizer(args[0]," ");
        String[] array = new String[st.countTokens()];

        for (int i = 0; i < array.length ; i++) {
            array[i] = st.nextToken();
            System.out.println(array[i]);
        }


        DBHandler deneme = new DBHandler();
        deneme.check();
        if(array[0].equals("check")){
            if(deneme.check()){
                System.out.println("connection established");
            }
            else{
                System.out.println("connection failed");
            }
        }
        else if(array[0].equals("add")){
            boolean denemeBool = deneme.addDB(array[1],array[2]);
            if(denemeBool){
                System.out.println("Done!");
            }
            else {
                System.out.println("Problem!");
            }
        }
        else if(array[0].equals("delete")){
            boolean denemeBool = deneme.deleteDB(array[1],array[2]);
            if(denemeBool){
                System.out.println("Done!");
            }
            else {
                System.out.println("Problem!");
            }
        }
        else if(array[0].equals("query")){
            deneme.queryFoo(Integer.parseInt(array[1]));
        }
    }
}
