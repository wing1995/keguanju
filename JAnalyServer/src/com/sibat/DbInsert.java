package com.sibat;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/************************************************************
 * 
 * @author wing
 * @version1.0.0
 * @since JDK1.8
 * @Date 2016.03.25
 * 
 *************************************************************/

public class DbInsert {
	public static void Db(String pParam) throws UnsupportedEncodingException{
		String xmlString = new String(pParam.getBytes());
		String tableName = "";
		try{Document doc = DocumentHelper.parseText(xmlString);
		
        Element root = doc.getRootElement(); 
        tableName = root.getName();
        
        System.out.println("I am reading the table: " + tableName);
        
        
        HashMap records = getData(root);
        Insert(tableName, records);
        
		}catch (DocumentException e) {
            System.out.println(e.getMessage());
		}
		
	}
	
	public static HashMap getData(Element root){
		HashMap<String, String> records = new HashMap();
		
		for (Iterator i = root.elementIterator(); i.hasNext();){
			
			Element node = (Element) i.next();
			String name = node.getName();
    		String text = node.getTextTrim();
    		records.put(name, text);
		}
		return records;
    }
		
	public static void Insert(String tableName, HashMap records){
		Connection c = null;
		Statement stmt = null;
		try{
			Class.forName("org.postgresql.Driver");
			String URL = "jdbc:postgresql://58.251.160.178:35432/kgj";
			String USER = "kgj";
			String PASS = "kgj123";
			c = DriverManager.getConnection(URL,USER, PASS);
			
			System.out.println("Opended database successful");
			stmt = c.createStatement();
			
			Iterator iter = records.entrySet().iterator();  
			String field = "";
	     	String data = "";
	     	
	        while (iter.hasNext()) {  
	        	Map.Entry<String, String> record = (Entry<String, String>) iter.next();
	        	
	        	field += record.getKey();
	        	data += "'" + record.getValue() + "'";
	        	
	            if(iter.hasNext()){
	            	field += ',';
	            	data += ',';
	            }
	        }
	      
	        String sql="";
	        sql = "INSERT INTO " + tableName + "(" + field + ")" + "VALUES("+ data + ")";
			stmt.executeUpdate(sql);
			
			System.out.println("Stored datas into table " + tableName + " successfuly!");
			
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      	}finally{
	          //finally block used to close resources
	          try{
	             if(stmt!=null)
	                c.close();
	          }catch(SQLException se){
	          	}// do nothing
	          try{
	             if(c!=null)
	                c.close();
	          	}catch(SQLException se){
	             se.printStackTrace();
	          }//end finally try
	       }//end try
	   System.out.println("Goodbye!");
	}
}
