package com.sm24soft.common.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * The following example implements a class for encrypting and decrypting
 * strings using several Cipher algorithms. The class is created with a key and
 * can be used repeatedly to encrypt and decrypt strings using that key.
 * Some of the more popular algorithms are:
 *      Blowfish
 *      DES
 *      DESede
 *      PBEWithMD5AndDES
 *      PBEWithMD5AndTripleDES
 *      TripleDES
 * 
 * Example: 
 * 			String secretString = "Hi, I'm Cipher";
 * 
 * 			// Generate a temporary key for this example. In practice, you would
 * 			// save this key somewhere. Keep in mind that you can also use a 
 * 			// Pass Phrase.
 * 			SecretKey desKey       = KeyGenerator.getInstance("DES").generateKey();
 * 			SecretKey blowfishKey  = KeyGenerator.getInstance("Blowfish").generateKey();
 * 			SecretKey desedeKey    = KeyGenerator.getInstance("DESede").generateKey();
 * 
 * 			// Create encrypter/decrypter class
 * 			StringEncrypter desEncrypter = new StringEncrypter(desKey, desKey.getAlgorithm());
 * 			StringEncrypter blowfishEncrypter = new StringEncrypter(blowfishKey, blowfishKey.getAlgorithm());
 * 			StringEncrypter desedeEncrypter = new StringEncrypter(desedeKey, desedeKey.getAlgorithm());
 * 
 * 			// Encrypt the string
 * 			String desEncrypted       = desEncrypter.encrypt(secretString);
 * 			String blowfishEncrypted  = blowfishEncrypter.encrypt(secretString);
 * 			String desedeEncrypted    = desedeEncrypter.encrypt(secretString);
 * 
 * 			// Decrypt the string
 * 			String desDecrypted       = desEncrypter.decrypt(desEncrypted);
 * 			String blowfishDecrypted  = blowfishEncrypter.decrypt(blowfishEncrypted);
 * 			String desedeDecrypted    = desedeEncrypter.decrypt(desedeEncrypted);
 * 
 * @author sondn
 *
 */
public class CipherEncrypt extends AbstractEncrypt {

	/** Encrypt Cipher */
	private Cipher encryptCipher = null;
	/** Decrypt Cipher */
    private Cipher decryptCipher = null;
    
	/**
	 * Constructor used to create this object. Responsible for setting and
	 * initializing this object's encrypter and decrypter Chipher instances
	 * given a Secret Key and algorithm.
	 * 
	 * @param key
	 *            Secret Key used to initialize both the encrypter and decrypter
	 *            instances.
	 * @param algorithm
	 *            Which algorithm to use for creating the encrypter and
	 *            decrypter instances.
	 */
    public CipherEncrypt(SecretKey key, String algorithm) {
        try {
        	encryptCipher = Cipher.getInstance(algorithm);
        	decryptCipher = Cipher.getInstance(algorithm);
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException ex) {
            throw new RuntimeException(ex);
        }
    }

	/**
	 * Constructor used to create this object. Responsible for setting and
	 * initializing this object's encrypter and decrypter Chipher instances
	 * given a Pass Phrase and algorithm.
	 * 
	 * @param passPhrase
	 *            Pass Phrase used to initialize both the encrypter and
	 *            decrypter instances.
	 */
    public CipherEncrypt(String passPhrase) {
        // 8-bytes Salt
		byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03 };

        // Iteration count
        int iterationCount = 19;

        try {
            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

            encryptCipher = Cipher.getInstance(key.getAlgorithm());
            decryptCipher = Cipher.getInstance(key.getAlgorithm());

            // Prepare the parameters to the cipthers
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

            encryptCipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            decryptCipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        } catch (InvalidAlgorithmParameterException | InvalidKeySpecException | NoSuchPaddingException 
        		| NoSuchAlgorithmException | InvalidKeyException ex) {
        	throw new RuntimeException(ex);
        }
    }
    
    /** {@inheritDoc} */
	@Override
	public String encrypt(String unencryptedText) {
        byte[] utf8 = unencryptedText.getBytes(UTF_8);
        byte[] enc;
        
		try {
			enc = encryptCipher.doFinal(utf8);
		} catch (IllegalBlockSizeException | BadPaddingException ex) {
			throw new RuntimeException(ex);
		}
        return Base64.encodeBase64String(enc);
	}

	/** {@inheritDoc} */
	@Override
	public String decrypt(String encryptedText) {
        byte[] dec = Base64.decodeBase64(encryptedText);
        byte[] utf8;
		try {
			utf8 = decryptCipher.doFinal(dec);
		} catch (IllegalBlockSizeException | BadPaddingException ex) {
			throw new RuntimeException(ex);
		}
        return bytes2String(utf8);
	}
	
	/**
	 * Returns String From An Array Of Bytes
	 * 
	 * @param bytes
	 * @return
	 */
	private static String bytes2String(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			stringBuffer.append((char) bytes[i]);
		}
		return stringBuffer.toString();
	}

}
