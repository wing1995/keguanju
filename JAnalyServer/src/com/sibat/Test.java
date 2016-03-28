package com.sibat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {

		String fileContent = readFileContent("./src/com/sibat/test.xml");
		
		//System.out.println(fileContent);
		DbInsert.Db(fileContent);
	}

	//参数string为你的文件名
	private static String readFileContent(String fileName) throws IOException {

		File file = new File(fileName);

		BufferedReader bf = new BufferedReader(new FileReader(file));

		String content = "";
		StringBuilder sb = new StringBuilder();

		while(content != null){
			content = bf.readLine();

			if(content == null){
				break;
			}

			sb.append(content.trim());
		}

		bf.close();
		return sb.toString();
	}
}