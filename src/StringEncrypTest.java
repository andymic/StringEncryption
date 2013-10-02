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
public class StringEncrypTest {

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

			//passPhrase = "My Secret Password";
			System.out.println("String Encryptor\n");
			System.out.println("-----------------\n");

			// create a user-chosen password that can be used with password-based encryption (PBE)
			// provide password, salt, iteration count for generating PBEKey of fixed-key-size PBE ciphers
			KeySpec keySpec;
			SecretKey key = null;
			AlgorithmParameterSpec paramSpec = null;
			if(args.length==1)
			{
				String[] argsSplit=args[0].split("=");
				String passPhrase = argsSplit[1];
				
				System.out.println(passPhrase);
				
				keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);

			// create a secret (symmetric) key using PBE with MD5 and DES 
				key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
				// construct a parameter set for password-based encryption as defined in the PKCS #5 standard
				 paramSpec = new PBEParameterSpec(salt, iterationCount);
			}
			else if(args.length==0)
			{
				System.out.println("No password Detected-A system password will be generated for you!");
				key= KeyGenerator.getInstance("DES").generateKey();

			} 
			else
			{
				System.out.println("Invalid Parameter!\n");
				System.out.println("Usage:\n");
				System.out.println("For user password, pass in one argument: ex: Password=thisismypassword \n");
				System.out.println("If a password is not detected, the system will generate one for you.");
				System.exit(0);
			}

		

			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());

			// initialize the ciphers with the given key

			  ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			
			  dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
			  
			  Scanner input= new Scanner (System.in);
			  System.out.print("Please provide a string to be encrypted:\n");
			  String message= input.next();
			  System.out.println(message);
			
			  String encrypted = encrypt(message);
			  
			  System.out.println("Encrypted String: " + encrypted);
			
			  String decrypted = decrypt(encrypted);
			
			  System.out.println("Decrypted String: " + decrypted);

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