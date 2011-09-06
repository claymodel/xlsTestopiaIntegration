package pv.hasnat.xls;

/**
 *
 * @author hasnat
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.SQLException;

/**
 *
 * @author hasnat
 */
class DBase
{
    public DBase()
    {
    }

    public Connection connect(String db_connect_str, String db_userid, String db_password)
    {
        Connection conn;
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            conn = DriverManager.getConnection(db_connect_str,
    db_userid, db_password);

        }
        catch(Exception e)
        {
            conn = null;
        }

        return conn;
    }

    public void importData(Connection conn,String query)
    {
        Statement stmt;

        try
        {
           stmt = conn.createStatement();
           stmt.executeUpdate(query);
        }
        catch(Exception e)
        {
            stmt = null;
            System.err.println("Sorry: " + query);
        }
    }

    public int caseId(Connection conn,String query)
    {
        Statement stmt;
        ResultSet rs = null;
        int cur_case_id=0;

        try
        {
           stmt = conn.createStatement();
           rs = stmt.executeQuery(query);

           while (rs.next()) {
                cur_case_id = Integer.valueOf(rs.getString(1));
            }
        }
        catch(Exception e)
        {
            stmt = null;
            //System.err.println("Sorry: " + query);
        }

        return cur_case_id;
    }

    public int planId(Connection conn,String query)
    {
        Statement stmt;
        ResultSet rs = null;
        int plan_id=0;

        try
        {
           stmt = conn.createStatement();
           rs = stmt.executeQuery(query);

           while (rs.next()) {
                plan_id = Integer.valueOf(rs.getString(1));
            }
        }
        catch(Exception e)
        {
            stmt = null;
            //System.err.println("Sorry: " + query);
        }

        return plan_id;
    }

    public boolean fetchTestCasetexts(Connection conn, StringBuffer sb)
                        throws IOException,SQLException {
        boolean isScriptExecuted = false;
        Statement stmt;

        try {
            //sb = new StringBuffer();
            stmt = conn.createStatement();
            System.out.println(sb.toString());
            //stmt.executeUpdate(sb.toString());
            isScriptExecuted = true;
            } catch (Exception e) {
                System.err.println("Failed to Execute" + sb +". The error is"+ e.getMessage());
                 stmt = null;
            }
        return isScriptExecuted;
    }
}