package com.sm24soft.common.crypto;

import java.nio.charset.Charset;

/**
 * Abstract encrypt class
 * 
 * @author sondn
 *
 */
public abstract class AbstractEncrypt {
	
	/** DEFAULT_ENCODE */
	private static final String DEFAULT_ENCODE = "UTF8";
	/** UTF_8 */
	protected static final Charset UTF_8 = Charset.forName(DEFAULT_ENCODE);
	    
	/**
	 * Takes a single String as an argument and returns an Encrypted version of
	 * that String
	 * 
	 * @param unencryptedText
	 *            String to be encrypted
	 * @return An encrypted text
	 */
	public abstract String encrypt(String unencryptedText);
	
	/**
	 * Takes a encrypted String as an argument, decrypts and returns the
	 * decrypted String
	 * 
	 * @param encryptedText
	 *            Encrypted String to be decrypted
	 * @return A decrypted text
	 */
	public abstract String decrypt(String encryptedText);
	
}
