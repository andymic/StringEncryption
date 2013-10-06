 /**
 * 
 */


import java.io.Console;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;
/**
 * @author master
 *
 */
public class StringEncryption {

	/**
	 * @param args
	 */
	
	private static Cipher ecipher;
	private static Cipher dcipher;

	private static final int iterationCount = 10;

	// 8-byte Salt
	private static byte[] salt = {

  (byte)0xB2, (byte)0x12, (byte)0xD5, (byte)0xB2,

  (byte)0x44, (byte)0x21, (byte)0xC3, (byte)0xC3
    };
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			// create a user-chosen password that can be used with password-based encryption (PBE)
			// provide password, salt, iteration count for generating PBEKey of fixed-key-size PBE ciphers
			KeySpec keySpec;
			SecretKey key = null;
			AlgorithmParameterSpec paramSpec = null;
			Scanner input= new Scanner (System.in);
			List<String> tests= new ArrayList<String>();
			List<SecretKey> keys = new ArrayList<SecretKey>();
			  
			
			
			int choice=usage();
			
			if(choice==1)
			{
				
				
				System.out.println("Automated Mode started...\n");
				for(int i=0; i<6; i++)
				{
				key= KeyGenerator.getInstance("DES").generateKey();
				keys.add(key);
				}
				
				tests= StringEncryptionTest.automatedTest();
			}
			else
			{
		    	System.out.print("Manual mode started, please provide a key. Ex: <thisismykey>\n");
		    	String userKey=input.nextLine();
				keySpec = new PBEKeySpec(userKey.toCharArray(), salt, iterationCount);

				// create key using PBE with MD5 and DES 
				key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
				//  set  password-based encryption 
				 paramSpec = new PBEParameterSpec(salt, iterationCount);
				
				System.out.println("Key generated successfully. Please provide a string to be encrypted. Ex: <this is my string>");
				String message=input.nextLine();
				keys.add(key);
				tests.add(message);
			}

			
			
			

			for (int i=0; i<keys.size(); i++)
			{
				
				//System.out.println(keys.get(i)+"==>"+tests.get(i));
			
			
			ecipher = Cipher.getInstance(keys.get(i).getAlgorithm());
			dcipher = Cipher.getInstance(keys.get(i).getAlgorithm());

			// initialize the ciphers with the given keys

			  ecipher.init(Cipher.ENCRYPT_MODE, keys.get(i), paramSpec);
			
			  dcipher.init(Cipher.DECRYPT_MODE, keys.get(i), paramSpec);
			  

			
			  String encrypted = encrypt(tests.get(i));
			  System.out.println((i+1)+".Plain String:"+tests.get(i));
			  System.out.println("Encrypted String: " + encrypted);
			
			  String decrypted = decrypt(encrypted);
			
			  System.out.println("Decrypted String: " + decrypted);
			  System.out.println();
			}

		}
		catch (InvalidAlgorithmParameterException e) {
			System.out.println("Invalid Alogorithm Parameter:" + e.getMessage());
			return;
		}
		catch (InvalidKeySpecException e) {
			System.out.println("Invalid Key Spec:" + e.getMessage());
			return;
		}
		catch (NoSuchAlgorithmException e) {
			System.out.println("No Such Algorithm:" + e.getMessage());
			return;
		}
		catch (NoSuchPaddingException e) {
			System.out.println("No Such Padding:" + e.getMessage());
			return;
		}
		catch (InvalidKeyException e) {
			System.out.println("Invalid Key:" + e.getMessage());
			return;
		}

	}

	private static int usage() {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Scanner input= new Scanner (System.in);
		int mode=0;
		System.out.println("             **String Encryptor** "+dateFormat.format(date));
		System.out.println("==========================================================\n");
		System.out.println("[Usage]--Mode:");
		System.out.println("1)Scripted (Automated)");
		System.out.println("\ta) A system key will be generated for you.");
		System.out.println("\tb) A series of automated random tests will be executed");
		System.out.println("2)Manual");
		System.out.println("\ta) You will be prompted for a key: ex: thisismykey");
		System.out.println("\tb) Provide  a path for scripted test cases (either relative or absolute)\n");
		System.out.println("Which mode would you like to run? Select mode 1 or 2.");
		mode=input.nextInt();
		
		if(mode==1)
		{
			return mode;
		}
		else if(mode==2)
		{
			return mode;
		}
		else
		{
			System.out.println("Invalid input,review Usage.\n");
			usage();
		}
		return -1;
	}

	public static String encrypt(String str) {

  try {

  	// encode the string into a sequence of bytes using the named charset

  	// storing the result into a new byte array. 

  	byte[] utf8 = str.getBytes("UTF8");

	byte[] enc = ecipher.doFinal(utf8);
	
	// encode to base64
	
	enc = BASE64EncoderStream.encode(enc);
	
	return new String(enc);

  }

  catch (Exception e) {

  	e.printStackTrace();

  }

  return null;

    }

	public static String decrypt(String str) {

  try {

  	// decode with base64 to get bytes
	
	byte[] dec = BASE64DecoderStream.decode(str.getBytes());
	
	byte[] utf8 = dcipher.doFinal(dec);
	
	// create new string based on the specified charset
	
	return new String(utf8, "UTF8");

  }

  catch (Exception e) {

  	e.printStackTrace();

  }

  return null;

    }

}