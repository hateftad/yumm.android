package se.yumm.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IO
{

	public static String ConvertStreamToString(InputStream is) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = br.readLine()) != null)
		{
			sb.append(line);
		}

		is.close();

		return sb.toString();
	}

	public static String[] SeperateAndRemove(String input, String seperate,
			String remove)
	{
		String[] tempString = null;

		tempString = input.split(seperate);
		String[] outString = new String[tempString.length];

		String newStr = tempString[0].replaceAll("\\[", "");
		for (int i = 0; i < tempString.length; i++)
		{
			outString[i] = tempString[i].replaceAll("[", "");
		}

		return outString;
	}
}
