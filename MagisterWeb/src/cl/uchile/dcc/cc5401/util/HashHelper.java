package cl.uchile.dcc.cc5401.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashHelper {

	private static String toHexadecimal(byte[] digest){
		String hash = "";
		for(byte aux : digest) {
			int b = aux & 0xff;
			if (Integer.toHexString(b).length() == 1) hash += "0";
			hash += Integer.toHexString(b);
		}
		return hash;
	}

	private static String getStringMessageDigest(String message, String algorithm){
		byte[] digest = null;
		byte[] buffer = message.getBytes();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.reset();
			messageDigest.update(buffer);
			digest = messageDigest.digest();
		} catch (NoSuchAlgorithmException ex) {
			System.out.println("Error creando Digest");
		}
		return toHexadecimal(digest);
	} 
	
	public static String toHash(String mensaje , Algoritmo algoritmo){
		return getStringMessageDigest(mensaje,algoritmo.getValue());
	}
	
}
