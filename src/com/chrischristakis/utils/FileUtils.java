package com.chrischristakis.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils
{
	
	private FileUtils() {}
	
	public static String readFileAsString(String path)
	{
		StringBuilder result = new StringBuilder();
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String toAppend = "";
			while((toAppend = reader.readLine()) != null)
				result.append(toAppend + "\n");
			reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return result.toString();
	}

}
