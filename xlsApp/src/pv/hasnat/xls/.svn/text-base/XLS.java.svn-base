package pv.hasnat.xls;


/**
 *
 * @author hasnat
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.sql.Connection;
import java.sql.SQLException;

public class XLS
{
    @SuppressWarnings("RedundantStringConstructorCall")
  public XLS(Workbook w, OutputStream out, 
            String host,int port,String dbname,String user, String pass)
    throws IOException, SQLException, InterruptedException
  {
    int flag = 1;
    String str = new String();
    String connectionStr = new String();
    DBase db = new DBase();
    connectionStr = new String("jdbc:mysql://"+host+":"+port+"/"+dbname);
    Connection conn = db.connect(connectionStr,user,pass);
    conn.setAutoCommit(false);
    StringBuffer sb = new StringBuffer();

    boolean hide = false;
    String encoding = "UTF8";

    try
    {
      OutputStreamWriter osw = new OutputStreamWriter(out, encoding);
      BufferedWriter bw = new BufferedWriter(osw);

      
      for (int sheet = 0; sheet < w.getNumberOfSheets(); sheet++)
      {
        Sheet s = w.getSheet(sheet);

        @SuppressWarnings("StringBufferMayBeStringBuilder")
        StringBuffer sql = new StringBuffer();

        if (!(hide && s.getSettings().isHidden()))
        {
          //bw.write("*** " + s.getName() + " ****");
          bw.newLine();

          Cell[] row0 = null;
          Cell[] row = null;

//==================================
            row0 = s.getRow(0);

            if (row0.length > 0)
            {
              if (!(hide && row0[0].isHidden()))
              {
                  str += row0[0].getContents();
              }

              for (int j = 1; j < row0.length; j++)
              {
                str += ',';
                if (!(hide && row0[j].isHidden()))
                {
                  str += row0[j].getContents();
                }
              }
            }
//==============================================


          for (int i = 1 ; i < s.getRows() ; i++)
          {
            row = s.getRow(i);
            int cur_case_id  = 0;
            int plan_id      = 0;
                String q1 = new String();
                String q2 = new String();
                String q3 = new String();
                String query_testopia    = new String();



            if (row.length > 0)
            {
              if (!(hide && row[0].isHidden()))
              {

              }

              for (int j = 1; j < row.length; j++)
              {
                if (!(hide && row[j].isHidden()))
                {
                  query_testopia += "\"" + row[j].getContents() + "\",";

                }
                query_testopia += "\"" + s.getName() + "";
              }


            String[] tokens = query_testopia.split("\",\"");

            q1 = "insert into test_cases(summary,category_id,priority_id,author_id,case_status_id,creation_date) values('"+tokens[0]+"',2,1,1,2,now())";

            q1 = new String(q1);

            cur_case_id = db.caseId(conn,"select max(case_id) from  test_cases");
            flag = cur_case_id + 1;
            //System.out.println("select plan_id from test_plans where upper(name) like upper('"+tokens[tokens.length - 1]+"%')");

            plan_id = db.caseId(conn,"select plan_id from test_plans where upper(name) like upper('"+tokens[tokens.length - 1]+"%')");

            q2 = "insert into test_case_plans(plan_id,case_id) values(" + plan_id + "," + flag +")";
            q2 = new String(q2);


            q3 = "insert into test_case_texts(case_id,who,creation_ts,action,effect) values("+ flag +",1,now(),'"+tokens[1].replaceAll(tokens[tokens.length - 1]+"\"", "")+"','"+tokens[2].replaceAll(tokens[tokens.length - 1]+"\"", "")+"')";

            q3 = new String(q3);

            db.importData(conn, q1);
            db.importData(conn, q2);
            conn.commit();

            sb.append(q3).append(";\n");
            }
            bw.newLine();
          }
        }

      }

      try {
          BufferedWriter outSql = new BufferedWriter(new FileWriter("outfilename"));
          outSql.write(sb.toString());
          outSql.close();
      } catch (IOException e) { }

     try {
          BufferedWriter scrScript = new BufferedWriter(new FileWriter("scrScript"));
          scrScript.write("#!/bin/bash\n mysql -h "+host+" -u "+user+" -p\""+pass+"\" "+dbname+" < outfilename;");
          scrScript.close();
      } catch (IOException e) { }


      Process proc = null;
      String cmd = "chmod 777 scrScript";

      Runtime run = Runtime.getRuntime();
      Process pr = run.exec(cmd);
      pr.waitFor();
      BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
      String line = "";

      while ((line=buf.readLine())!=null) {
        System.out.println(line);
      }
      
      Process proc2 = null;
      String cmd2 = "./scrScript";
      Runtime run2 = Runtime.getRuntime();
      Process pr2 = run.exec(cmd2);
      pr2.waitFor();
      BufferedReader buf2 = new BufferedReader(new InputStreamReader(pr2.getInputStream()));
      String line2 = "";

      while ((line2=buf2.readLine())!=null) {
        System.out.println(line2);
      }

      Process proc3 = null;
      String cmd3 = "rm -rf scrScript outfilename";
      Runtime run3 = Runtime.getRuntime();
      Process pr3 = run.exec(cmd3);
      pr3.waitFor();
      BufferedReader buf3 = new BufferedReader(new InputStreamReader(pr3.getInputStream()));
      String line3 = "";

      while ((line3=buf3.readLine())!=null) {
        System.out.println(line3);
      }


      bw.flush();
      bw.close();
      conn.close();
    }
    catch (UnsupportedEncodingException e)
    {
      System.err.println(e.toString());
    }
  }
}

