package com.sibat;

import java.io.*;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/************************************************************
 * 
 * @author wuying
 * @version1.0.0
 * @since JDK1.8
 * @Date 2016.03.15
 * 
 *************************************************************/

public class ExtractXml {
	public static class Extract {
		
		public static Element condition(BufferedWriter writer, Iterator m) throws IOException {
			
			Element node = (Element) m.next();
	    		//获取节点内容并去除空字符串
	    		String text = node.getTextTrim();
			if (!text.equals("")){
				int word = 0;
	    		writer.write(new String(text.getBytes()));
	    		if (m.hasNext()){
	                		writer.write(",");
	    		}
	    		else{
	    			writer.newLine(); //换行
	    		}
	    	}
			return node;
		}

		public static void parseXml(String xmlString, String outputName){
	        
			try {
				//将文件缓存写入
            	OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(new File(outputName), true));
	        	BufferedWriter writer = new BufferedWriter(write);
	            Document doc = DocumentHelper.parseText(xmlString);
	            //读取根节点
	            Element root = doc.getRootElement(); 
	            System.out.println("I am reading the table to file: " + root.getName());
	   
	            //开始迭代子节点
	            for (Iterator i = root.elementIterator(); i.hasNext();){
	            	condition(writer, i);
	            	
	        } writer.close();
	        
	        }catch (DocumentException e) {
	            System.out.println(e.getMessage());
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
		}
	}
}
