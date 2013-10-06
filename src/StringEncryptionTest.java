import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.crypto.SecretKey;


public class StringEncryptionTest {
	public static Properties keysNstrings = new Properties();
	public static List<String> testStrings= new ArrayList<String>();
	
	public static List<String> automatedTest()
	{
		Random rnd = new Random();
		String alphaNumeric="ABCDEFGHI JKLMNOPQRSTUVWXYZ0123 456789 έήάίΐό ύϋΰώθω ρτψυοπ αφγηςκλζχξωβνμ";
		String letters="ABCDEFGHIJK LMNOPQRSTUVWXYZέήά ίΐόύϋΰ ώθωρτψυοπ αφγηςκλζχξωβνμ";
		String numbers="0 123 456789";
		int length=10;
		char[] text = new char[length]; 
		
		for(int j=0; j<2; j++)
		{
		    for (int i = 0; i < length; i++)
		    {
		        text[i] = letters.charAt(rnd.nextInt(letters.length()));
		    }
		    testStrings.add(new String(text));
		}
		
		for(int j=0; j<2; j++)
		{
		    for (int i = 0; i < length; i++)
		    {
		        text[i] = numbers.charAt(rnd.nextInt(numbers.length()));
		    }
		    testStrings.add(new String(text));
		}
		
		for(int j=0; j<2; j++)
		{
		    for (int i = 0; i < length; i++)
		    {
		        text[i] = alphaNumeric.charAt(rnd.nextInt(alphaNumeric.length()));
		    }
		    testStrings.add(new String(text));
		}
		
		
	    return testStrings;
	}
}
