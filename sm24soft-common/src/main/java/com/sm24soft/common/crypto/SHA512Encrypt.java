package com.sm24soft.common.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * It will use SHA-512 hashing algorithm to generate a hash value for a password
 * plain text.
 * 
 * @author sondn
 *
 */
public final class SHA512Encrypt extends AbstractEncrypt {
	
	/**
	 * Get SHA-512 instance
	 * 
	 * @return
	 */
	private static MessageDigest getSHA512Instance() {
		try {
			return MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Encrypt password by using SHA-512 algorithm (64 bits). Reference
	 * http://java.sun.com/j2se/1.4.2/docs/api/java/security/MessageDigest.html
	 * 
	 * @param unencryptedText
	 * @return SHA-512 hash text of password
	 */
	@Override
	public String encrypt(String unencryptedText) {
		MessageDigest ctx = getSHA512Instance();
		ctx.update(unencryptedText.getBytes(UTF_8));
		
		byte[] byteOfHash = ctx.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteOfHash.length; i++) {
			sb.append(Integer.toString((byteOfHash[i] & 0xff) + 0x100, 16).substring(1));
		}
		/*
		 * Clear the buffer for the intermediate result so that people attaching
		 * to processes or reading core dumps cannot get any information.
		 */
		ctx.reset();
		return sb.toString();
	}
	
	/**
	 * @deprecated Failed to decrypt encrypted text because SHA-512 hashing
	 *             algorithm is not support decrypt method
	 */
	@Deprecated
	@Override
	public String decrypt(String encryptedText) {
		return encryptedText;
	}

}
