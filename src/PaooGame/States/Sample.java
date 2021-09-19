package PaooGame.States;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sample
{
    public static void main(String[] args)
    {
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            //statement.executeUpdate("drop table if exists person");

            //statement.executeUpdate("create table person (id integer, name string, numar_nevastuci string)");

            statement.executeUpdate("insert into person values(1, 'leo', '42')");
            statement.executeUpdate("insert into person values(2, 'yui', 'ghjfjdjsg')");
            statement.executeUpdate("insert into person values(3, 'josh', '5884 ggg')");

            ResultSet rs = statement.executeQuery("select * from person");
            while(rs.next())
            {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
                System.out.println("nr nevastuici = " + rs.getInt("numar_nevastuci"));
            }

            connection.commit();
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
}