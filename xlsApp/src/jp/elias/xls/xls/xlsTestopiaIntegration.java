package jp.elias.xls.xls;

import java.io.File;
import jxl.Workbook;

public class xlsTestopiaIntegration {
	public xlsTestopiaIntegration(String[] Str){
		   try
		    {
                        Workbook wbook = Workbook.getWorkbook(new File(Str[0]));
		        XLS xLS = new XLS(wbook, System.out, Str[1], new Integer(Str[2]), Str[3], Str[4], Str[5]);
                        wbook.close();
		     }
		    catch (Throwable t)
		    {
		      System.out.println(t.toString());
		      t.printStackTrace();
		    }
	}
}
