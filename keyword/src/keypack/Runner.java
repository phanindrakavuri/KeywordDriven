package keypack;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Runner {
				
		public static void main(String[] args) throws Exception 
		{
			// connect to excel sheet
				File f=new File("w2smstests1.xls");
			//open excel sheet for reading
				Workbook rwb=Workbook.getWorkbook(f);
				Sheet rsh1=rwb.getSheet(0) ; //sheet1 for  test ids 
				int nour1=rsh1.getRows();
				int  nouc1=rsh1.getColumns();
				Sheet rsh2=rwb.getSheet(1) ; //sheet1 for  step ids
				int nour2=rsh2.getRows();
				int  nouc2=rsh2.getColumns();
		// open same excel  for  writing test results
				WritableWorkbook wwb=Workbook.createWorkbook(f,rwb);
				WritableSheet wsh1=wwb.getSheet(0);
				WritableSheet wsh2=wwb.getSheet(1);
				WritableCellFormat cf=new WritableCellFormat();
				cf.setAlignment(Alignment.JUSTIFY);
				cf.setWrap(true);
			// set name to results column in sheet2 date as column  take results heading as date and time stamp
				Date da=new Date();
			    SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
			    String cname=sf.format(da);
			// set name results to sheet1
			    Label l1=new Label(nouc1,0,cname,cf);
			    wsh1.addCell(l1);
			 // set name results to sheet2
			    Label l2=new Label(nouc2,0,cname,cf);
			    wsh2.addCell(l2);   
		// create object to method class		
			    Methods ms=new Methods();
				
		// Collect methods into using 	methods class object
				Method[] m=ms.getClass().getMethods();
				try
				{
					// calling methods one after other // 1st row(index is 0) have name of class in sheet
					for (int i=1;i<nour1; i++)
					{
						int flag=0;
						
					// Get test id  and mode from sheet
						String tid=rsh1.getCell(0,i).getContents();
						String mode=rsh1.getCell(2,i).getContents();
						if(mode.equalsIgnoreCase("yes"))
						{
							// 1st row index=0 have name of cols in sheet
						for (int j=1; j<nour2; j++)
					{
							
						String sid=rsh2.getCell(0, j).getContents();
						  if(sid.equalsIgnoreCase(tid))	
					 {
							  //take step details from sheet2
						  System.out.println("hi");  
						  String mn=rsh2.getCell(2,j).getContents();
						  String e=rsh2.getCell(3,j).getContents();
						  String d=rsh2.getCell(4,j).getContents();
						  String c=rsh2.getCell(5,j).getContents();
							System.out.println(mn+e+d+c); 
						  // methods
						 for (int k=0; k<m.length;k++)
						 {
							 System.out.println("hi"); 
							 if (m[k].getName().equals(mn))
						 {
							String r=(String)m[k].invoke(ms,e,d,c );
							Label lb=new Label(nouc2,j,r,cf);
							wsh2.addCell(lb);
						
						if(r.equalsIgnoreCase("unknown browser"))	
						{
							wwb.write();
							wwb.close();
							rwb.close();
							System.exit(0);
						}
					if(r.contains("Failed")  || r.contains("failed") || r.contains("interrupted"))	
					{
						flag=1;
					}
							
				 } // if closed
				 } //  for k closed
			  } // if closed
			 } // j closed
			if (flag==0)		
			{
				Label lb=new Label(nouc2,i,"passed",cf);
				wsh2.addCell(lb);
			}
			else
			{
				Label la=new Label(nouc1,i,"Failed",cf);
				wsh1.addCell(la);		
			}
				} // if mode class
				} // i closing
				} // try block
				catch (Exception ex)
				{
					System.out.println(ex.getMessage());;
				}
				
		// keyword driven		
		// save and close excel 
				wwb.write();
				wwb.close();
				rwb.close();
				

			}  // main method

		} // class closed


